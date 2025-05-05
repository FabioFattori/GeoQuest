package com.example.geoquest.ui.components.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.geoquest.R
import com.example.geoquest.business.classes.DayPointOfInterest
import com.example.geoquest.business.classes.Position
import com.example.geoquest.ui.components.baseComponents.ButtonProps
import com.example.geoquest.ui.components.baseComponents.CustomButton
import com.example.geoquest.ui.theme.TextType
import com.example.geoquest.ui.theme.getSize

@Composable
fun POIDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    poi: DayPointOfInterest,
    playerPosition: Position,
    isAlreadyCollected: Boolean
) {
    val isNear = poi.isNearPlayer(playerPosition = playerPosition)
    val contentString = if(isAlreadyCollected){
        stringResource(R.string.alreadyCollected)
    }else{
        if (isNear) stringResource(R.string.playerNextPOI) else stringResource(
            R.string.playerNotNextPOI
        )
    }
    val backString = stringResource(R.string.back)
    val getString = stringResource(R.string.get)

    Dialog(
        onDismissRequest = { onDismissRequest() }) {
        // Draw a rectangle shape with rounded corners inside the dialog
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(410.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = poi.getDisplayName(),
                    fontSize = getSize(TextType.Title),
                    modifier = Modifier.padding(16.dp),
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = contentString,
                    modifier = Modifier.padding(16.dp),
                    fontSize = getSize(TextType.Normal),
                )
                CustomButton(
                    props = ButtonProps(
                        label = backString,
                        onClick = onDismissRequest,
                    ),
                    modifier = Modifier.padding(8.dp),
                )

                CustomButton(
                    props = ButtonProps(
                        label = getString, onClick = onConfirmation, isEnabled = isNear && !isAlreadyCollected
                    ),
                    modifier = Modifier.padding(8.dp),
                )

            }
        }
    }
}
