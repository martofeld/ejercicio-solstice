package com.mfeldsztejn.solstice

import android.app.Application
import androidx.emoji.text.EmojiCompat
import androidx.emoji.bundled.BundledEmojiCompatConfig

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        EmojiCompat.init(BundledEmojiCompatConfig(this))
    }
}