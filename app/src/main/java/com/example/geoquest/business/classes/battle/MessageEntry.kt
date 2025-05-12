package com.example.geoquest.business.classes.battle

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.geoquest.business.classes.battle.enums.MessageTypes
import com.example.geoquest.R
import com.example.geoquest.ui.theme.TextType
import com.example.geoquest.ui.theme.getSize

data class MessageEntry(
    val messageType: MessageTypes
) : SingleEntry {
    @Composable
    override fun GetText() {
        val message: String = stringResource(
            when (messageType) {
                MessageTypes.BattleStart -> R.string.battleStart
                MessageTypes.BattleEnd -> R.string.battleEnd
            }
        )

        Box(
            modifier = Modifier.Companion.padding(vertical = 20.dp, horizontal = 5.dp)
        ) {
            Text(
                text = message,
                fontWeight = FontWeight.Companion.Bold,
                fontSize = getSize(TextType.Normal),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }

}
