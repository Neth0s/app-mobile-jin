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
    val name: String = names.random(),
    val description: String,
    val modifier: EventModifier,
    @DrawableRes var image: Bitmap? = null,
)

// Parce que l'API pour générer des noms aléatoires ne marche plus
val names = listOf(
    "Erlich Bachman",
    "Richard Hendricks",
    "Laurie Bream",
    "Burkett Rivard",
    "Rémy Grivois",
    "Frédéric Caron",
    "Joseph Caisse",
    "Agramant Lachapelle",
    "Prewitt Lajeunesse",
    "Merle Robillard",
    "Octave Chartré",
    "Didier Mercier",
    "Brice Doyon",
    "Audric de Brisay",
    "Vernon Bazinet",
    "Curtis Bergeron",
    "Delmar Jacques",
    "Curtis Paquet",
    "Hugh Pitre",
    "Léon Roussel"
)

val baseProfiles = listOf(
    Event(
        description = "Vendre des calendriers associatifs",
        modifier = EventModifier(0.1f, 0f, 0.2f, -0.1f, 0.05f, -0.05f),
        image = null
    ),
    Event(
        description = "Faire un stand au forum",
        modifier = EventModifier(-0.1f, 0.1f, 0f, 0.1f, 0.05f, -0.05f),
        image = null
    ),
    Event(
        description = "Faire un prank aux listeux",
        modifier = EventModifier(0f, -0.2f, 0.2f, -0.2f, -0.1f, 0f),
        image = null
    ),
    Event(
        description = "Faire la soirée de Noël",
        modifier = EventModifier(
            -0.35f, 0.35f, -0.10f,
            0.25f, -0.20f, -0.10f
        ),
    ),
    Event(
        description = "Faire une animation absinthe",
        modifier = EventModifier(
            -0.05f, 0.10f, -0.05f,
            -0.05f, -0.10f, -0.05f
        ),
    ),
    Event(
        description = "Retarder la passation",
        modifier = EventModifier(
            0.00f, -0.30f, 0.25f,
            0.00f, 0.00f, -0.10f
        ),
    ),
    Event(
        description = "Organiser un sondage pour savoir qui liste",
        modifier = EventModifier(
            0.00f, -0.10f, 0.05f,
            0.00f, 0.00f, 0.05f
        ),
    ),
    Event(
        description = "Suivre RESET",
        modifier = EventModifier(
            -0.05f, 0.10f, -0.15f,
            0.05f, 0.00f, 0.15f
        ),
    ),
    Event(
        description = "Créer un jeu pour faire la promo de ton asso",
        modifier = EventModifier(
            -0.05f, 0.15f, -0.20f,
            0.00f, -0.10f, 0.05f
        ),
    ),
    Event(
        description = "Faire un partenariat Discord",
        modifier = EventModifier(
            0.30f, 0.10f, -0.10f,
            0.00f, 0.00f, 0.10f
        ),
    ),
    Event(
        description = "Installer un chateau gonflable dans le local",
        modifier = EventModifier(
            -0.15f, 0.15f, 0.15f,
            0.00f, -0.05f, -0.05f
        ),
    ),
    Event(
        description = "Augmenter la cotisation",
        modifier = EventModifier(
            0.25f, -0.30f, 0.00f,
            0.00f, 0.10f, -0.10f
        ),
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
