package com.example.geoquest.ui.theme

import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

enum class TextType{
    BigTitle,
    Title,
    Normal,
}

fun getSize(type: TextType) : TextUnit{
    when(type){
        TextType.BigTitle -> return 70.sp
        TextType.Title -> return 35.sp
        TextType.Normal -> return 24.sp
    }
}