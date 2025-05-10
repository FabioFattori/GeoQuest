package com.example.geoquest.ui.components.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.geoquest.business.models.EquippableItem
import com.example.geoquest.business.models.UsableItem
import com.example.geoquest.R
import com.example.geoquest.business.models.Player
import com.example.geoquest.ui.components.baseComponents.SingleItem
import com.example.geoquest.ui.theme.TextType
import com.example.geoquest.ui.theme.getSize
import com.example.geoquest.ui.viewModels.factories.GlobalViewModels
import com.example.geoquest.utilities.ImagesResolver
import com.example.geoquest.utilities.PreferenceManager

@Composable
fun ChooseItemDialog(
    firstChoice: EquippableItem,
    secondChoice: UsableItem,
    onDismissRequest: () -> Unit,
    onAccept : () -> Unit
) {
    val string = stringResource(R.string.chooseItem)

    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        // Draw a rectangle shape with rounded corners inside the dialog
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(15.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Text(
                string,
                fontSize = getSize(TextType.Title),
            )
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                SingleItem(
                    modifier = Modifier,
                    clickable = true,
                    image = {
                        val res =
                            ImagesResolver
                                .associateDbImagesToPossibleImages()[firstChoice.blueprint.imagePath]
                        if (res == null) {
                            Unit
                        } else {
                            ImagesResolver.GetImageComponent(res)
                        }
                    },
                    rarity = firstChoice.rarity.getColor(),
                    onClick = {
                        GlobalViewModels.inventoryHandler.ownEquippableItem(
                            firstChoice
                        )
                        onAccept()
                    }
                )
                SingleItem(
                    modifier = Modifier,
                    clickable = true,
                    image = {
                        val res =
                            ImagesResolver
                                .associateDbImagesToPossibleImages()[secondChoice.imageIndex]
                        if (res == null) {
                            Unit
                        } else {
                            ImagesResolver.GetImageComponent(res)
                        }
                    },
                    rarity = secondChoice.rarity.getColor(),
                    onClick = {
                        GlobalViewModels.inventoryHandler.ownUsableItem(
                            secondChoice
                        )
                        onAccept()
                    }
                )
            }
        }
    }
}