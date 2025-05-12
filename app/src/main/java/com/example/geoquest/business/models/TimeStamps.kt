package com.example.geoquest.business.models

import android.os.Parcelable
import kotlinx.serialization.SerialName
import java.util.Date
import kotlinx.parcelize.Parcelize

@Parcelize
data class TimeStamps(
    @SerialName("created_at")
    val createAt: Date,
    @SerialName("updated_at")
    val updatedAt: Date
) : Parcelable
