package com.example.geoquest.ui.components.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.geoquest.business.models.Rarity
import com.example.geoquest.ui.components.baseComponents.ButtonProps
import com.example.geoquest.ui.components.baseComponents.CustomButton
import com.example.geoquest.ui.components.baseComponents.SingleItem
import com.example.geoquest.ui.theme.TextType
import com.example.geoquest.ui.theme.getSize

@Composable
fun RewardDialog(
    onConfirmation: () -> Unit,
    image: @Composable () -> Unit,
    title: String,
    rarity: Rarity?
) {

    val okString = stringResource(R.string.get)

    Dialog(onDismissRequest = { onConfirmation() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = title,
                    fontSize = getSize(TextType.Title),
                    modifier = Modifier.padding(16.dp),
                    fontWeight = FontWeight.Bold
                )

                SingleItem(
                    image = image,
                    rarity = rarity?.getColor(),
                    modifier = Modifier,
                    clickable = false,
                )

                CustomButton(
                    props = ButtonProps(
                        label = okString,
                        onClick = onConfirmation,
                    ),
                    modifier = Modifier.padding(8.dp),
                )

            }
        }
    }
}
