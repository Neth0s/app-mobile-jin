package com.example.appli_mobile_clemence_pierre.test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexstyl.swipeablecard.*
import kotlinx.coroutines.launch


// From : https://github.com/alexstyl/compose-tinder-card
class TestActivity : ComponentActivity() {
    @OptIn(ExperimentalSwipeableCardApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val displayMetrics = DisplayMetrics()
//        windowManager.defaultDisplay.getMetrics(displayMetrics)
//        val height = displayMetrics.heightPixels
//        val width = displayMetrics.widthPixels

//        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val screenWidth =
                with(LocalDensity.current) {
                    LocalConfiguration.current.screenWidthDp.dp.toPx()
                }
            val screenHeight =
                with(LocalDensity.current) {
                    LocalConfiguration.current.screenHeightDp.dp.toPx()
                }
            MaterialTheme() {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    Color(0xfff68084),
                                    Color(0xffa6c0fe),
                                )
                            )
                        )
                        .systemBarsPadding()
                ) {
                    Box {
                        var states by remember {
                            mutableStateOf(profiles.map {
                                it to SwipeableCardState(
                                    screenWidth,
                                    screenHeight
                                )
                            })
                        }

                        var hint by remember {
                            mutableStateOf("Swipe a card or press a button below")
                        }

                        Hint(hint)

                        val scope = rememberCoroutineScope()
                        Box(
                            Modifier
                                .padding(24.dp)
                                .fillMaxSize()
                                .aspectRatio(1f)
                                .align(Alignment.Center)
                        ) {
                            states.forEach { (matchProfile, state) ->
//                                if (state.swipedDirection == null) {
//                                    if (abs(state.offset.value.x) > 10) {
//                                        if (state.offset.value.x > 0) {
//                                            // Card is right
////                                            Log.d("TEST", "Swiping right: ${matchProfile.name}")
//                                        } else {
//                                            // Card is left
////                                            Log.d("TEST", "Swiping left: ${matchProfile.name}")
//                                        }
//                                    }
                                ProfileCard(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .swipableCard(
                                            state = state,
                                            blockedDirections = listOf(
                                                com.alexstyl.swipeablecard.Direction.Up,
                                                com.alexstyl.swipeablecard.Direction.Down
                                            ),
                                            onSwiped = {
                                                val a = profiles[0] to SwipeableCardState(
                                                    screenWidth,
                                                    screenHeight
                                                )
                                                states = states - (matchProfile to state)
                                                states = listOf(a) + states;
                                                hint =
                                                    "You swiped ${stringFrom(state.swipedDirection!!)}"
                                            },
                                            onSwipeCancel = {
                                                hint = "You canceled the swipe"
                                            }
                                        ),
                                    matchProfile = matchProfile
                                )
//                                }
//                                LaunchedEffect(matchProfile, state.swipedDirection) {
//                                    if (state.swipedDirection != null) {
//                                        hint = "You swiped ${stringFrom(state.swipedDirection!!)}"
//                                    }
//                                }
                            }
                        }
                        Row(
                            Modifier
                                .align(Alignment.BottomCenter)
                                .padding(horizontal = 24.dp, vertical = 32.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            CircleButton(
                                onClick = {
                                    scope.launch {
                                        val pair = states.last()

                                        pair.second.swipe(com.alexstyl.swipeablecard.Direction.Left)
                                        val a = profiles[0] to SwipeableCardState(
                                            screenWidth,
                                            screenHeight
                                        )
                                        states = states - pair
                                        states = listOf(a) + states
                                    }
                                },
                                icon = Icons.Rounded.Close
                            )
                            CircleButton(
                                onClick = {
                                    scope.launch {
                                        val pair = states.last()

                                        pair.second.swipe(com.alexstyl.swipeablecard.Direction.Right)
                                        val a = profiles[0] to SwipeableCardState(
                                            screenWidth,
                                            screenHeight
                                        )
                                        states = states - pair
                                        states = listOf(a) + states
                                    }
                                },
                                icon = Icons.Rounded.Favorite
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun CircleButton(
        onClick: () -> Unit,
        icon: ImageVector,
    ) {
        IconButton(
            modifier = Modifier
                .clip(CircleShape)
                .background(MaterialTheme.colors.primary)
                .size(56.dp)
                .border(2.dp, MaterialTheme.colors.primary, CircleShape),
            onClick = onClick
        ) {
            Icon(
                icon, null,
                tint = MaterialTheme.colors.onPrimary
            )
        }
    }

    @Composable
    private fun ProfileCard(
        modifier: Modifier,
        matchProfile: MatchProfile,
    ) {
        Card(modifier) {
            Box {
                Image(
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(matchProfile.drawableResId),
                    contentDescription = null
                )
                Scrim(Modifier.align(Alignment.BottomCenter))
                Column(Modifier.align(Alignment.BottomStart)) {
                    Text(
                        text = matchProfile.name,
                        color = MaterialTheme.colors.onPrimary,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }
        }
    }

    @Composable
    private fun Hint(text: String) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 32.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = text,
                color = MaterialTheme.colors.onPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                textAlign = TextAlign.Center
            )
        }
    }

    private fun stringFrom(direction: com.alexstyl.swipeablecard.Direction): String {
        return when (direction) {
            com.alexstyl.swipeablecard.Direction.Left -> "Left 👈"
            com.alexstyl.swipeablecard.Direction.Right -> "Right 👉"
            com.alexstyl.swipeablecard.Direction.Up -> "Up 👆"
            com.alexstyl.swipeablecard.Direction.Down -> "Down 👇"
        }
    }
}

@Composable
fun Scrim(modifier: Modifier = Modifier) {
    Box(
        modifier
            .background(Brush.verticalGradient(listOf(Color.Transparent, Color.Black)))
            .height(180.dp)
            .fillMaxWidth()
    )
}


