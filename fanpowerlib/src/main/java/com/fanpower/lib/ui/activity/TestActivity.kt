package com.fanpower.lib.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fanpower.lib.R
import com.fanpower.lib.databinding.ActivityTestBinding


class TestActivity : AppCompatActivity() {

    private  val TAG = "TestActivity"

    private lateinit var binding: ActivityTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        binding = ActivityTestBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


    }
}