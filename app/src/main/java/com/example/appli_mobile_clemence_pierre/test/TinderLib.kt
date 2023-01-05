package com.example.appli_mobile_clemence_pierre.test

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.math.abs

enum class Direction {
    Left, Right, Up, Down
}

/**
 * Enables Tinder like swiping gestures.
 *
 * @param state The current state of the swipeable card. Use [rememberSwipeableCardState] to create.
 * @param onSwiped will be called once a swipe gesture is completed. The given [Direction] will indicate which side the gesture was performed on.
 * @param onSwipeCancel will be called when the gesture is stopped before reaching the minimum threshold to be treated as a full swipe
 * @param blockedDirections the directions which will not trigger a swipe. By default only horizontal swipes are allowed.
 */
@OptIn(ExperimentalComposeUiApi::class)
fun Modifier.swipeableCard(
    state: SwipeableCardState,
    onDrag: (Offset) -> Unit,
    onSwiped: (Direction) -> Unit,
    onSwipeCancel: () -> Unit = {},
    blockedDirections: List<Direction> = listOf(Direction.Up, Direction.Down),
): Modifier {
    return (if (state.swipedDirection != null) this
    else pointerInput(Unit) {
        coroutineScope {
            detectDragGestures(
                onDragCancel = {
                    launch {
                        state.reset()
                        onSwipeCancel()
                    }
                },
                onDrag = { change, dragAmount ->
                    launch {
                        val original = state.offset.targetValue
                        val summed = original + dragAmount
                        val newValue = Offset(
                            x = summed.x.coerceIn(-state.maxWidth, state.maxWidth),
                            y = summed.y.coerceIn(-state.maxHeight, state.maxHeight)
                        )

                        if (change.positionChange() != Offset.Zero) {
                            change.consume()
                        }

                        onDrag(newValue)
                        state.drag(newValue.x, newValue.y)
                    }
                },
                onDragEnd = {
                    launch {
                        val coercedOffset = state.offset.targetValue.coerceIn(
                            blockedDirections,
                            maxHeight = state.maxHeight,
                            maxWidth = state.maxWidth
                        )

                        if (hasNotTravelledEnough(state, coercedOffset)) {
                            state.reset()
                            onSwipeCancel()
                            return@launch
                        }

                        if (abs(coercedOffset.x) > abs(coercedOffset.y)) {
                            if (state.offset.targetValue.x > 0) {
                                state.swipe(Direction.Right)
                                onSwiped(Direction.Right)
                            } else {
                                state.swipe(Direction.Left)
                                onSwiped(Direction.Left)
                            }
                        } else {
                            if (state.offset.targetValue.y < 0) {
                                state.swipe(Direction.Up)
                                onSwiped(Direction.Up)
                            } else {
                                state.swipe(Direction.Down)
                                onSwiped(Direction.Down)
                            }
                        }
                    }
                }
            )
        }
    }).graphicsLayer(
        translationX = state.offset.value.x,
        translationY = state.offset.value.y,
        rotationZ = (state.offset.value.x / 60).coerceIn(-40f, 40f),
    )
}

private fun Offset.coerceIn(
    blockedDirections: List<Direction>,
    maxHeight: Float,
    maxWidth: Float,
): Offset {
    return copy(
        x = x.coerceIn(
            if (blockedDirections.contains(Direction.Left)) 0f else -maxWidth,
            if (blockedDirections.contains(Direction.Right)) 0f else maxWidth
        ),
        y = y.coerceIn(
            if (blockedDirections.contains(Direction.Up)) 0f else -maxHeight,
            if (blockedDirections.contains(Direction.Down)) 0f else maxHeight
        )
    )
}

private fun hasNotTravelledEnough(
    state: SwipeableCardState,
    offset: Offset,
): Boolean {
    return abs(offset.x) < state.maxWidth / 4 && abs(offset.y) < state.maxHeight / 4
}

class SwipeableCardState(
    internal val maxWidth: Float,
    internal val maxHeight: Float,
) {
    private val animationDuration = 200
    val offset = Animatable(offset(0f, 0f), Offset.VectorConverter)

    /**
     * The [Direction] the card was swiped at.
     *
     * Null value means the card has not been swiped fully yet.
     */
    var swipedDirection: Direction? by mutableStateOf(null)
        private set

    internal suspend fun reset() {
        offset.animateTo(
            offset(0f, 0f),
            tween(animationDuration / 2)
        )
        swipedDirection = null
    }

    suspend fun swipe(
        direction: Direction,
        animationSpec: AnimationSpec<Offset> = tween(animationDuration)
    ) {
        swipedDirection = direction
        val endX = maxWidth * 1.2f
        val endY = maxHeight
        when (direction) {
            Direction.Left -> offset.animateTo(offset(x = -endX), animationSpec)
            Direction.Right -> offset.animateTo(offset(x = endX), animationSpec)
            Direction.Up -> offset.animateTo(offset(y = -endY), animationSpec)
            Direction.Down -> offset.animateTo(offset(y = endY), animationSpec)
        }
    }

    private fun offset(x: Float = offset.value.x, y: Float = offset.value.y): Offset {
        return Offset(x, y)
    }

    internal suspend fun drag(x: Float, y: Float) {
        offset.animateTo(offset(x, y))
    }
}

@Composable
fun rememberSwipeableCardState(): SwipeableCardState {
    val screenWidth = with(LocalDensity.current) {
        LocalConfiguration.current.screenWidthDp.dp.toPx()
    }
    val screenHeight = with(LocalDensity.current) {
        LocalConfiguration.current.screenHeightDp.dp.toPx()
    }
    return remember {
        SwipeableCardState(screenWidth, screenHeight)
    }
}
