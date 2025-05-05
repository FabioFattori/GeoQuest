package com.example.geoquest.ui.components.dialogs

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.rounded.WarningAmber
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.example.geoquest.R
import com.example.geoquest.ui.components.baseComponents.ButtonProps
import com.example.geoquest.ui.components.baseComponents.ButtonShapes
import com.example.geoquest.ui.components.baseComponents.CustomButton
import com.example.geoquest.ui.theme.TextType
import com.example.geoquest.ui.theme.getSize


enum class DialogMode(val icon: ImageVector, val color: Color) {
    Warning(icon = Icons.Rounded.WarningAmber, color = Color(0xFFFF5F15)),
    Info(icon = Icons.Outlined.Info, color = Color(0xFF2196F3))
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleDialog(
    text: String,
    onOk: () -> Unit,
    onNo: () -> Unit,
    dialogMode: DialogMode = DialogMode.Warning
) {
    val okText = stringResource(R.string.yes)
    val backText = stringResource(R.string.back)

    AlertDialog(
        onDismissRequest = onNo,
        icon = {
            Icon(
                imageVector = dialogMode.icon,
                contentDescription = null,
                tint = dialogMode.color,
                modifier = Modifier.size(60.dp, 60.dp)
            )
        },
        text = {
            Text(text, fontSize = getSize(TextType.ButtonText))
        },
        confirmButton = {
            CustomButton(
                props = ButtonProps(
                    label = okText,
                    onClick = onOk
                ),
                modifier = Modifier.width(100.dp),
                buttonShape = ButtonShapes.RoundedRect
            )
        },
        dismissButton = {
            CustomButton(
                props = ButtonProps(
                    label = backText,
                    onClick = onNo
                ),
                modifier = Modifier.width(130.dp),
                buttonShape = ButtonShapes.RoundedRect
            )
        },
        properties = DialogProperties(
            true,
            true,
            true
        )

    )
}
