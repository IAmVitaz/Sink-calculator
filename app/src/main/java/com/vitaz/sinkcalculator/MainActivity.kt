package com.vitaz.sinkcalculator

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.Intent.*
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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

        titleContactLayout.setOnClickListener {
            val emailIntent = Intent(ACTION_SENDTO)
            emailIntent.data = Uri.parse("mailto:vitaz.development@gmail.com") // only email apps should handle this
            emailIntent.addFlags(FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
            emailIntent.putExtra(EXTRA_EMAIL, arrayOf("vitaz.development@gmail.com"))
            emailIntent.putExtra(EXTRA_SUBJECT, "Sink calculator feedback")

            try {
                val chooser = createChooser(emailIntent, "Send email with..")
                this.startActivity(chooser)
                Log.i("INFO", "Finished sending email...")
            } catch (ex: ActivityNotFoundException) {
                Toast.makeText(
                    this@MainActivity,
                    "There is no email client installed.",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }

    }




}
