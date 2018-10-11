package com.mfeldsztejn.solstice.networking

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mfeldsztejn.solstice.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.greenrobot.eventbus.EventBus
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class RestApiBuilder {


    companion object {
        private val bus = EventBus.builder().build();

        fun build(): Retrofit {

            val client = OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build()

            return Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()
        }

        fun register(subscriber: Any) {
            bus.register(subscriber)
        }

        fun unregister(subscriber: Any) {
            bus.unregister(subscriber)
        }

        fun post(event: Any){
            bus.post(event)
        }
    }
}