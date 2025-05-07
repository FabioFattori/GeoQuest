package com.example.geoquest.ui.components.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Card
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.geoquest.business.models.EquippableItem
import com.example.geoquest.ui.components.baseComponents.ButtonProps
import com.example.geoquest.ui.components.baseComponents.CustomButton
import com.example.geoquest.ui.theme.TextType
import com.example.geoquest.ui.theme.getSize
import com.example.geoquest.R
import com.example.geoquest.ui.components.StatTable
import com.example.geoquest.ui.components.baseComponents.IconGradient
import com.example.geoquest.ui.components.baseComponents.SingleItem
import com.example.geoquest.ui.components.baseComponents.SingleItemConfiguration
import com.example.geoquest.utilities.ImagesResolver

@Composable
fun GetTextForDialog(txt: String) {
    Text(
        txt,
        fontSize = getSize(TextType.Normal),
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(bottom = 10.dp)
    )
}

@Composable
fun GetSmallTextDialog(txt: String) {
    Text(
        txt,
        fontSize = getSize(TextType.ButtonText),
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(bottom = 10.dp)
    )
}

@Composable
fun EquippableItemDialog(
    toShow: EquippableItem,
    playerItem: EquippableItem?,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
) {
    val typeText = stringResource(R.string.type)
    val rarityString = stringResource(R.string.rarity)
    val statString = stringResource(R.string.stats)
    val descString = stringResource(R.string.descriptio)
    val equipString = stringResource(R.string.equip)
    val damageString = stringResource(R.string.damage)
    val healthString = stringResource(R.string.life)
    val typeOfToShow = toShow.blueprint.getRuneString()

    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        // Draw a rectangle shape with rounded corners inside the dialog
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .then(Modifier.widthIn(max = 1000.dp))
                .padding(15.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp, vertical = 15.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start,
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    IconButton(
                        onClick = onDismissRequest
                    ) {
                        IconGradient(
                            modifier = Modifier.size(100.dp),
                            contentDescription = "",
                            icon = Icons.Rounded.Close
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(
                            SingleItemConfiguration.size + 80.dp
                        )
                        .padding(bottom = 10.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SingleItem(
                        modifier = Modifier.padding(4.dp),
                        rarity = toShow.rarity.getColor(),
                        clickable = false,
                        image = {
                            val image =
                                ImagesResolver
                                    .associateDbImagesToPossibleImages()[toShow.blueprint.imagePath]
                            ImagesResolver.GetImageComponent(image!!)
                        }
                    )
                    Column(
                        modifier = Modifier.fillMaxSize(),

                        ) {
                        GetTextForDialog(
                            toShow.blueprint.name
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 5.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            GetSmallTextDialog(typeText)
                            GetSmallTextDialog(typeOfToShow)
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            GetSmallTextDialog(rarityString)
                            GetSmallTextDialog(toShow.rarity.name)
                        }
                    }
                }

                GetTextForDialog(
                    statString
                )
                StatTable(
                    toCompare = toShow,
                    playerItem = playerItem,
                    damageString = damageString,
                    healthString = healthString
                )
                GetTextForDialog(
                    descString
                )
                GetTextForDialog(toShow.blueprint.description)
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CustomButton(
                        props = ButtonProps(
                            label = equipString,
                            onClick = onConfirmation,
                        ),
                        modifier = Modifier.padding(8.dp),
                    )
                }

            }
        }
    }
}