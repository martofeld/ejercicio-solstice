package com.mfeldsztejn.solstice.dtos

import java.io.Serializable

data class Address(val street: String,
                   val city: String,
                   val state: String,
                   val country: String,
                   val zipCode: String) : Serializable {
    override fun toString(): String {
        return "$street \n$city, $state $zipCode, $country"
    }
}