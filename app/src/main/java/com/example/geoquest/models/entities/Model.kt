package com.example.geoquest.models.entities

abstract class Model(id: Int) {
    var id: Int = id
        private set

    fun setId(id: Int) {
        this.id = id
    }

    fun getId(): Int {
        return id
    }
}
