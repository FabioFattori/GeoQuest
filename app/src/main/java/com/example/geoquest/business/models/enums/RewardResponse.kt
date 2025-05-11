package com.example.geoquest.business.models.enums

enum class RewardResponse(statusCode: Int) {
    AlreadyGotReward(413),
    PlayerHasNotDoneEnoughBattles(412),
    PlayerCanGetReward(200);

    companion object{
        fun findEnumGivenStatusCode(statusCode: Int): RewardResponse {
            return when (statusCode) {
                413 -> AlreadyGotReward
                412 -> PlayerHasNotDoneEnoughBattles
                200 -> PlayerCanGetReward
                else -> throw Exception("invalid status code")
            }
        }
    }
}