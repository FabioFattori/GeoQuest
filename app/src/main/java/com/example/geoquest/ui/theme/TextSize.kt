package com.example.geoquest.ui.theme

import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

enum class TextType {
    BigTitle,
    Title,
    Normal,
    ButtonText
}

fun getSize(type: TextType): TextUnit {
    return when (type) {
        TextType.BigTitle -> 65.sp
        TextType.Title -> 35.sp
        TextType.Normal -> 24.sp
        TextType.ButtonText -> 20.sp
    }
}
