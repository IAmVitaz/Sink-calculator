package com.vitaz.sinkcalculator

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.vitaz.sinkcalculator.ViewModel.MagusViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val mMagusViewModel: MagusViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set tutorial step shared prefs
        val preferenceManager = getSharedPreferences("tutorial", Context.MODE_PRIVATE)
        val currentTutorialValue = preferenceManager.getInt("tutorialCurrentStep", 99)
        if (currentTutorialValue == 99) {
            preferenceManager.edit().putInt("tutorialCurrentStep", 1).apply()
        }

        runTutorial.setOnClickListener {
            preferenceManager.edit().putInt("tutorialCurrentStep", 1).apply()
            val startNewJob = Intent(this, CreateNewItemActivity::class.java)
            startActivity(startNewJob)
            this.finish();
        }

        newJob.setOnClickListener {
            val startNewJob = Intent(this, CreateNewItemActivity::class.java)
            startActivity(startNewJob)
            this.finish();
        }

    }




}
