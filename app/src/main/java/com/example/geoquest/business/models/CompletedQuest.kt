package com.example.geoquest.business.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

//@JsonAdapter(CompletedQuestDeserializer::class)
@Parcelize
data class CompletedQuest(
    val id:Int,
    val playerId:Int,
    val timeStamps:@RawValue TimeStamps,
    val name: String
) : Parcelable
