package com.example.appli_mobile_clemence_pierre.test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appli_mobile_clemence_pierre.R

class HalfSizeShape(val widthPart: Float) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline = Outline.Generic(Path().apply {
        addRect(Rect(Offset.Zero, Size(size.width * widthPart, size.height)))
    })
}

@Composable
fun Filler(
    name: String,
    fillValue: Float, // Between 0 - 1
    hintValue: Float, // Between 0 - 1
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box() {
            Image(
                modifier = Modifier.size(100.dp),
                painter = painterResource(R.drawable.church),
                contentDescription = "Church",
                colorFilter = ColorFilter.tint(Color.Yellow),
                alpha = 0.2f
            )
            Image(
                modifier = Modifier
                    .size(100.dp)
                    .clip(HalfSizeShape(hintValue)),
                painter = painterResource(R.drawable.church),
                contentDescription = "Church",
                colorFilter = ColorFilter.tint(Color.Gray),
                alpha = 0.6f
            )
            Image(
                modifier = Modifier
                    .size(100.dp)
                    .clip(HalfSizeShape(fillValue)),
                painter = painterResource(R.drawable.church),
                contentDescription = "Church",
                colorFilter = ColorFilter.tint(Color.Red),
                alpha = 1f
            )
        }
        Text(text = name, color = Color.Red)
    }
}

// From : https://github.com/alexstyl/compose-tinder-card
class TestActivity : ComponentActivity() {

    val profiles = mutableStateListOf(
        MatchProfile(name = "Erlich Bachman", image = R.drawable.ic_launcher_background),
        MatchProfile(name = "Richard Hendricks", image = R.drawable.ic_launcher_background),
        MatchProfile(name = "Laurie Bream", image = R.drawable.ic_launcher_background),
    )

    var religion1 = 0.4f
    var religion2 = 0.6f

    var hint by mutableStateOf("Swipe a card or press a button below")

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme() {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black)
                ) {
                    Column(
                        Modifier
                            .padding(10.dp)
                            .align(Alignment.TopCenter)
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = hint,
                            color = MaterialTheme.colors.onPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                            textAlign = TextAlign.Center
                        )

                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Filler(name = "Test1", fillValue = 0.6f, hintValue = 0.7f)

                            Filler(name = "Test2", fillValue = 0.1f, hintValue = 0.2f)

                            Filler(name = "Test3", fillValue = 0.1f, hintValue = 0.2f)

                            Filler(name = "Test4", fillValue = 0.1f, hintValue = 0.2f)
                        }
                    }

                    profiles
                        .filter { !it.swiped }
                        .forEach { profile ->
                            key(profile) {
                                ProfileCard(
                                    modifier = Modifier
                                        .padding(24.dp)
                                        .aspectRatio(1f)
                                        .align(Alignment.Center)
                                        .swipeableCard(
                                            state = rememberSwipeableCardState(),
                                            onDrag = { hint = "Offsetx = ${it.x}" },
                                            onSwiped = {
                                                profiles.add(0, profile.copy(swiped = false))
                                                hint =
                                                    "$profile  ➡" + if (it == Direction.Right) "YAY" else "NAY"
                                                profile.swiped = true
                                            },
                                            onSwipeCancel = { hint = "You canceled the swipe" }
                                        ),
                                    matchProfile = profile
                                )
                            }
                        }
                }
            }
        }
    }
}
