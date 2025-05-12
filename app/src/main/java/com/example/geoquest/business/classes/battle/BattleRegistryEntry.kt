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
import com.example.geoquest.R
import com.example.geoquest.ui.theme.TextType
import com.example.geoquest.ui.theme.getSize

data class BattleRegistryEntry(
    val attackerName: String,
    val damageDealt: Int,
    val attackedName: String,
    val isPlayer: Boolean
) : SingleEntry {

    @Composable
    override fun GetText() {
        val firstString = stringResource(R.string.attack)
        val secondString = stringResource(R.string.dealingDamage, damageDealt)

        Box(
            modifier = Modifier.Companion.padding(vertical = 20.dp, horizontal = 5.dp)
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = if (isPlayer) MaterialTheme.colorScheme.surfaceVariant else Color.Red,
                            fontWeight = FontWeight.Companion.Bold,
                            fontSize = getSize(TextType.Normal)
                        )
                    ) {
                        append(attackerName)
                    }
                    append(" $firstString ")
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Companion.Bold,
                            fontSize = getSize(TextType.Normal)
                        )
                    ) {
                        append(attackedName)
                    }
                    append(" $secondString")
                },
                fontWeight = FontWeight.Companion.Bold,
                fontSize = getSize(TextType.Normal),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
