package com.example.geoquest.ui.components.baseComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.geoquest.ui.theme.TextType
import com.example.geoquest.ui.theme.getSize

@Composable
fun BattleUser(
    playerName: String,
    isAlignedToLeft: Boolean,
    currentHealth: Int,
    maxHealth: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        horizontalArrangement = if (isAlignedToLeft) Arrangement.Start else Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .width(300.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = if (isAlignedToLeft) Alignment.Start else Alignment.End
        ) {
            Text(
                text = playerName,
                modifier = Modifier
                    .fillMaxWidth(),
                fontSize = getSize(TextType.Normal),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                textAlign = if (isAlignedToLeft) TextAlign.Start else TextAlign.End
            )

            LinearProgressIndicator(
                progress = { currentHealth * 1f / maxHealth },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(35.dp)
                    .padding(vertical = 10.dp)
                    .clip(CircleShape)
                    .graphicsLayer(scaleX = if (isAlignedToLeft) 1f else -1f),
                color = MaterialTheme.colorScheme.surfaceVariant,
                trackColor = MaterialTheme.colorScheme.secondary,
                strokeCap = StrokeCap.Square,
            )

            Text(
                text = "$currentHealth/$maxHealth",
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                fontSize = getSize(TextType.Normal),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )

        }
    }
}
