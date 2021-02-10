package com.sixt.demoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.sixt.car.CarActivity
import com.sixt.utils.extensions.withDelay

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        withDelay(3000){
            startActivity(Intent(this@MainActivity,CarActivity::class.java))
            finish()
        }
    }
}