package com.github.damipen.steamapi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class DebugActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_fragment)

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, SteamCommunityFragment())
            .commit()

    }

}