package com.example.geoquest.business.models

import kotlinx.serialization.SerialName
import java.util.Date

data class TimeStamps(
    @SerialName("created_at")
    val createAt: Date,
    @SerialName("updated_at")
    val updatedAt: Date
)
