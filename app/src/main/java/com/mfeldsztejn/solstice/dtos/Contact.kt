package com.mfeldsztejn.solstice.dtos

import java.io.Serializable
import java.util.*

data class Contact(val id: String,
                   val name: String,
                   var isFavorite: Boolean,
                   val companyName: String?,
                   val smallImageURL: String?,
                   val largeImageURL: String?,
                   val emailAddress: String,
                   val birthdate: Date,
                   val phone: Map<String, String>,
                   val address: Address) : Serializable {
    override fun equals(other: Any?): Boolean {
        return other is Contact && other.id == id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}