package com.example.appli_mobile_clemence_pierre.test

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.example.appli_mobile_clemence_pierre.R
import com.example.appli_mobile_clemence_pierre.avatar_api.Api
import com.example.appli_mobile_clemence_pierre.test.ui.theme.ApplimobileclemencepierreTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedInputStream
import java.io.InputStream
import kotlin.math.abs

class HalfSizeShape(private val widthPart: Float, private val offset: Float) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline = Outline.Generic(Path().apply {
        if (widthPart < 0) {
            addRect(
                Rect(
                    Offset(size.width * (offset + widthPart), 0f),
                    Size(size.width * abs(widthPart), size.height)
                )
            )
        } else {
            addRect(
                Rect(
                    Offset(size.width * offset, 0f),
                    Size(size.width * abs(widthPart), size.height)
                )
            )
        }
    })
}

@Composable
fun Filler(
    name: String,
    fillValue: Float, // Between 0 - 1
    hintValue: Float, // Between 0 - 1
    image: Int
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box {
            Image(
                modifier = Modifier.size(100.dp),
                painter = painterResource(image),
                contentDescription = "Church",
                colorFilter = ColorFilter.tint(Color.Cyan),
                alpha = 0.2f
            )
            Image(
                modifier = Modifier
                    .size(100.dp)
                    .clip(HalfSizeShape(fillValue, 0f)),
                painter = painterResource(image),
                contentDescription = "Church",
                colorFilter = ColorFilter.tint(Color.Blue),
                alpha = 1f
            )
            Image(
                modifier = Modifier
                    .size(100.dp)
                    .clip(HalfSizeShape(hintValue, fillValue)),
                painter = painterResource(image),
                contentDescription = "Church",
                colorFilter = ColorFilter.tint(if (hintValue < 0) Color.Red else Color.Green),
                alpha = 1f
            )
        }
        Text(text = name, color = Color.Blue)
    }
}

// From : https://github.com/alexstyl/compose-tinder-card
class TestActivity : ComponentActivity() {

    private var money by mutableStateOf(0.5f)
    private var moneyHint by mutableStateOf(0f)
    private var popularity by mutableStateOf(0.5f)
    private var popularityHint by mutableStateOf(0f)
    private var mental by mutableStateOf(0.5f)
    private var mentalHint by mutableStateOf(0f)

    private var hint by mutableStateOf("Swipe a card or press a button below")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            profiles.forEach {
                val bm: Bitmap? = withContext(Dispatchers.IO) {
                    val a = Api.imageWebService.get_image(it.name) // in avatar_api.Api
                    val inStream: InputStream = BufferedInputStream(a.byteStream())
                    return@withContext BitmapFactory.decodeStream(inStream)
                }
                it.image = bm
            }
        }

        setContent {
            ApplimobileclemencepierreTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black)
                ) {
                    val mContext = LocalContext.current
                    FloatingActionButton(
                        onClick = {
                            mContext.startActivity(
                                Intent(
                                    mContext,
                                    ListActivity::class.java
                                )
                            )
                        },
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(20.dp),
                        shape = CircleShape,
                        backgroundColor = Color.Red
                    ) {
                        Icon(Icons.Filled.List, "See list")
                    }
                    Column(
                        Modifier
                            .padding(10.dp)
                            .align(Alignment.TopCenter)
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = hint,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                            textAlign = TextAlign.Center
                        )

                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Filler(
                                name = "Money",
                                fillValue = money,
                                hintValue = moneyHint,
                                image = R.drawable.dollar
                            )

                            Filler(
                                name = "Popularity",
                                fillValue = popularity,
                                hintValue = popularityHint,
                                image = R.drawable.people
                            )

                            Filler(
                                name = "Mental",
                                fillValue = mental,
                                hintValue = mentalHint,
                                image = R.drawable.mental
                            )
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
                                            onDrag = {
//                                                hint = "Offsetx = ${it.x}"
                                                if (it.x > 0) {
                                                    moneyHint = profile.modifier.moneyYes
                                                    popularityHint =
                                                        profile.modifier.popularityYes
                                                    mentalHint = profile.modifier.mentalYes
                                                } else {
                                                    moneyHint = profile.modifier.moneyNo
                                                    popularityHint =
                                                        profile.modifier.popularityNo
                                                    mentalHint = profile.modifier.mentalNo
                                                }
                                            },
                                            onSwiped = {
                                                profiles.add(
                                                    0,
                                                    baseProfiles
                                                        .random()
                                                        .copy(swiped = false, name = names.random())
                                                )
                                                lifecycleScope.launch {
                                                    val bm: Bitmap? = withContext(Dispatchers.IO) {
                                                        val a =
                                                            Api.imageWebService.get_image(profiles[0].name) // in avatar_api.Api
                                                        val inStream: InputStream =
                                                            BufferedInputStream(a.byteStream())
                                                        return@withContext BitmapFactory.decodeStream(
                                                            inStream
                                                        )
                                                    }
                                                    profiles[0].image = bm
                                                }
//                                                hint =
//                                                    if (it == Direction.Right) "YES" else "NO"
                                                profile.swiped = true
                                                money += moneyHint
                                                if (money <= 0) hint = "PERDU"
                                                money = money.coerceIn(0f, 1f)
                                                popularity += popularityHint
                                                if (popularity <= 0) hint = "PERDU"
                                                popularity = popularity.coerceIn(0f, 1f)
                                                mental += mentalHint
                                                if (mental <= 0) hint = "PERDU"
                                                mental = mental.coerceIn(0f, 1f)
                                                moneyHint = 0f
                                                popularityHint = 0f
                                                mentalHint = 0f
                                            },
                                            onSwipeCancel = {
//                                                hint = "You canceled the swipe"
                                                moneyHint = 0f
                                                popularityHint = 0f
                                                mentalHint = 0f
                                            }
                                        ),
                                    event = profile
                                )
                            }
                        }
                }
            }
        }
    }
}
