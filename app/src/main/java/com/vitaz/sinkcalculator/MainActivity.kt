package com.vitaz.sinkcalculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.vitaz.sinkcalculator.Services.RunesService
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        newJob.setOnClickListener {
            val startNewJob = Intent(this, MagusActivity::class.java)
            startActivity(startNewJob)
        }

        Log.d("StatList:", RunesService.statList.toString())


    }




}