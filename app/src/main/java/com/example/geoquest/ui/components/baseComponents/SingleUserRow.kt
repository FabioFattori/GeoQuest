package com.example.geoquest.ui.components.baseComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CardGiftcard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.geoquest.business.models.Player
import com.example.geoquest.ui.theme.TextType
import com.example.geoquest.ui.theme.getGradient
import com.example.geoquest.ui.theme.getSize

@Composable
fun SingleUserRow(player: Player, isHighlighted: Boolean, isDialogOpen: MutableState<Boolean>) {
    var modifierForRow = Modifier
        .fillMaxWidth()
        .height(85.dp)
        .padding(10.dp)

    if (isHighlighted)
        modifierForRow = modifierForRow.background(
            brush = getGradient()
        )



    Row(
        modifier = modifierForRow,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            player.name,
            fontSize = getSize(TextType.Normal),
            modifier = Modifier.padding(start = 7.dp),
            color = if (isHighlighted)
                MaterialTheme.colorScheme.background
            else
                MaterialTheme.colorScheme.primary
        )

        if (isHighlighted) {
            Icon(
                imageVector = Icons.Rounded.CardGiftcard,
                tint = MaterialTheme.colorScheme.background,
                contentDescription = "",
                modifier = Modifier
                    .size(50.dp)
                    .padding(end = 7.dp)
                    .clickable(
                        true,
                        onClick = {
                            isDialogOpen.value = true
                        }
                    )
            )
        }
    }
}
