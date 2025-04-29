package com.example.geoquest.utilities

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.geoquest.R

enum class PossibleImages {
    Axe,
    Sword,
    Helmet1,
    Helmet2,
    Rune1,
    Rune2,
    LifePotion,
}

abstract class ImagesResolver {
    companion object {
        fun associateDbImagesToPossibleImages(): Map<String, PossibleImages> {
            return mapOf(
                "axe" to PossibleImages.Axe,
                "sword" to PossibleImages.Sword,
                "helmet" to PossibleImages.Helmet1,
                "helmet2" to PossibleImages.Helmet2,
                "rune__1_" to PossibleImages.Rune1,
                "rune__2_" to PossibleImages.Rune2,
                "lifePotion" to PossibleImages.LifePotion,
            )
        }

        @Composable
        fun getImage(images: PossibleImages): Painter {
            return when (images) {
                PossibleImages.Axe -> painterResource(id = R.drawable.axe)
                PossibleImages.Sword -> painterResource(id = R.drawable.spada)
                PossibleImages.Helmet1 -> painterResource(id = R.drawable.helmet)
                PossibleImages.Helmet2 -> painterResource(id = R.drawable.helmet2)
                PossibleImages.Rune1 -> painterResource(id = R.drawable.rune__1_)
                PossibleImages.Rune2 -> painterResource(id = R.drawable.rune__2_)
                PossibleImages.LifePotion -> painterResource(id = R.drawable.life_potion)
            }
        }

        @Composable
        fun GetImageComponent(images: PossibleImages) {
            val painter = getImage(images)
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier.size(150.dp)
            )
        }
    }
}
