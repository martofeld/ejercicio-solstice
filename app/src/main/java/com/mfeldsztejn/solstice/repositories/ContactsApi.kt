package com.mfeldsztejn.solstice.repositories

import com.mfeldsztejn.solstice.dtos.Contact
import com.mfeldsztejn.solstice.networking.RestApiBuilder
import retrofit2.Call
import retrofit2.http.GET

interface ContactsApi {
    @GET("contacts.json")
    fun contacts() : Call<List<Contact>>

    companion object {
        fun build() : ContactsApi {
            return RestApiBuilder
                    .build()
                    .create(ContactsApi::class.java)
        }
    }
}