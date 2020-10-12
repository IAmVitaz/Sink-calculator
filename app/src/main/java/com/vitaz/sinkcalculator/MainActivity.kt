package com.vitaz.sinkcalculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.vitaz.sinkcalculator.Services.RunesService
import com.vitaz.sinkcalculator.ViewModel.MagusViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mMagusViewModel: MagusViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        newJob.setOnClickListener {
            val startNewJob = Intent(this, CreateNewItemActivity::class.java)
            startActivity(startNewJob)
            this.finish();
        }


    }




}