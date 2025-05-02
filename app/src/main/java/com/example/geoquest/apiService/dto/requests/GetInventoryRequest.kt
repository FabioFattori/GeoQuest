package com.example.geoquest.apiService.dto.requests

import com.example.geoquest.business.models.enums.EquippableItemTypes


data class GetInventoryRequest(
    val ownerId: Int,
    val type: Int
){
    companion object{
        fun create(ownerId: Int,type: EquippableItemTypes): GetInventoryRequest{
            val toSendType : Int = when(type){
                EquippableItemTypes.Weapon -> 1
                EquippableItemTypes.Rune -> 3
                EquippableItemTypes.Armor -> 2
            }

            return GetInventoryRequest(
                ownerId = ownerId,
                type = toSendType
            )
        }
    }
}