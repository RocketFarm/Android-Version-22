package com.fanpower.power

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView
import com.fan.power.R
import com.fanpower.lib.databinding.ActivityWebViewBinding
import com.fanpower.lib.ui.activity.WebViewActivity

class HomeActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        var inlineView : CardView = findViewById(R.id.inlineView)
        var individualCarousel : CardView = findViewById(R.id.individualCarousel)

        inlineView.setOnClickListener(){
            var intent = Intent(this, WebActivityFanPower::class.java)
            startActivity(intent)
        }

        individualCarousel.setOnClickListener(){
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}