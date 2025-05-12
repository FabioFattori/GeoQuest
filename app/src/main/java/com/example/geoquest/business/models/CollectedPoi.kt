package com.example.geoquest.business.models

import android.os.Parcelable
import com.example.geoquest.business.classes.Position
import com.example.geoquest.business.classes.deserializers.CollectedPoiDeserializer
import com.google.gson.annotations.JsonAdapter
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
@JsonAdapter(CollectedPoiDeserializer::class)
data class CollectedPoi(
    val id: Int,
    val timeStamps:@RawValue TimeStamps,
    val position :@RawValue Position
) : Parcelable
