package com.example.appli_mobile_clemence_pierre.test

import android.graphics.Bitmap
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appli_mobile_clemence_pierre.R
import kotlinx.serialization.Serializable

// All values are between 0 - 1
@Serializable
data class EventModifier(
    val moneyYes: Float,
    val popularityYes: Float,
    val mentalYes: Float,
    val moneyNo: Float,
    val popularityNo: Float,
    val mentalNo: Float,
)

data class Event(
    var swiped: Boolean = false,
    val name: String,
    val description: String,
    val modifier: EventModifier,
    @DrawableRes var image: Bitmap?,
)

val baseProfiles = listOf(
    Event(
        name = "Erlich Bachman",
        description = "Vendre des calendriers associatifs",
        modifier = EventModifier(0.1f, 0f, 0.2f, -0.1f, 0.05f, -0.05f),
        image = null
    ),
    Event(
        name = "Richard Hendricks",
        description = "Faire un stand au forum",
        modifier = EventModifier(-0.1f, 0.1f, 0f, 0.1f, 0.05f, -0.05f),
        image = null
    ),
    Event(
        name = "Laurie Bream",
        description = "Faire un prank aux listeux",
        modifier = EventModifier(0f, -0.2f, 0.2f, -0.2f, -0.1f, 0f),
        image = null
    ),
)

val profiles = (0..5).map { baseProfiles.random().copy(swiped = false) }.toMutableList()

@Composable
public fun ProfileCard(
    modifier: Modifier,
    event: Event,
) {
    Card(modifier) {
        Box {
            if (event.image != null) {
                Image(
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                    bitmap = event.image!!.asImageBitmap(),
                    contentDescription = "Person Avatar",
                )

            } else {
                Image(
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(R.drawable.ic_launcher_background),
                    contentDescription = null
                )
            }
            Column(Modifier.align(Alignment.BottomStart)) {
                Text(
                    text = event.name,
                    color = MaterialTheme.colors.onPrimary,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Medium,
                    style = TextStyle(
                        shadow = Shadow(
                            color = Color.White,
                            offset = Offset.Zero,
                            blurRadius = 5f
                        )
                    ),
                    modifier = Modifier
                        .padding(10.dp)
                )
                Text(
                    text = event.description,
                    color = MaterialTheme.colors.onPrimary,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    style = TextStyle(
                        shadow = Shadow(
                            color = Color.White,
                            offset = Offset.Zero,
                            blurRadius = 5f
                        )
                    ),
                    modifier = Modifier
                        .padding(10.dp)
                )
            }
        }
    }
}
