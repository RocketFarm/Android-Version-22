package com.fanpower.power

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fan.power.R

import com.fanpower.lib.ui.views.FanPowerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        var fanPowerView = findViewById<FanPowerView>(R.id.fanPowerView)

//        fanPowerView.initView("your-tokenForJwtRequest",
//            0, // your-publisherId
//            "your-publisherToken",
//            "your-shareUrl",
//            supportFragmentManager)




    }
}