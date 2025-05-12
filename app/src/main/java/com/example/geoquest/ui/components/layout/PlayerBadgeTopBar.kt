package com.example.geoquest.ui.components.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.geoquest.R
import com.example.geoquest.business.models.Player
import com.example.geoquest.ui.components.baseComponents.IconGradient
import com.example.geoquest.ui.theme.TextType
import com.example.geoquest.ui.theme.getSize
import com.example.geoquest.utilities.PreferenceManager
import kotlin.math.roundToInt

@Composable
fun PlayerBadgeForTopBar(modifier: Modifier) {
    // get the user, if it is null throw error
    val player = PreferenceManager.getObject("player", Player::class.java)


    if (player == null) {
        Text("No player")
        return
    }

    val percMissingExp: Float =
        ((player.experienceCollected.toFloat()) / player.experienceNeeded).coerceIn(0f, 1f)
    val percText = (percMissingExp * 100).roundToInt()

    Column(
        modifier = modifier.width(200.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = player.name, fontSize = getSize(TextType.ButtonText))
        LinearProgressIndicator(
            progress = { percMissingExp },
            modifier = Modifier
                .width(180.dp)
                .height(37.dp)
                .padding(vertical = 10.dp)
                .clip(CircleShape),
            color = MaterialTheme.colorScheme.surfaceVariant,
            trackColor = MaterialTheme.colorScheme.secondary,
            strokeCap = StrokeCap.Square,
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "${percText}%",
                fontSize = getSize(TextType.ButtonText),
                fontWeight = FontWeight.Bold
            )

            IconGradient(
                icon = ImageVector.vectorResource(id = R.drawable.award_star_24px),
                contentDescription = "",
                modifier = Modifier.size(30.dp)
            )

            Text(
                text = "${stringResource(R.string.level)} ${player.level}",
                fontSize = getSize(TextType.ButtonText),
                fontWeight = FontWeight.Bold
            )
        }
    }
}
