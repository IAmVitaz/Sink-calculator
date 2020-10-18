package com.vitaz.sinkcalculator

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.iterator
import androidx.core.widget.addTextChangedListener
import kotlinx.android.synthetic.main.activity_create_new_item.*
import me.toptas.fancyshowcase.FancyShowCaseQueue
import me.toptas.fancyshowcase.FancyShowCaseView
import me.toptas.fancyshowcase.FocusShape

class CreateNewItemActivity : AppCompatActivity() {

    lateinit var preferenceManager: SharedPreferences

    var itemCategory = ""
    var itemName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_new_item)

        preferenceManager = getSharedPreferences("tutorial", Context.MODE_PRIVATE)

        // Run item creation intro if we are on the 1st step of tutorial
        runFirstStepOfTutorial()

        for (image in categoryHolder) {
            image.setOnClickListener {
                unselectEverything(categoryHolder)
                image.setBackgroundResource(R.drawable.item_category_background_checked)
                itemCategory = image.getTag().toString()
            }
        }

        itemNameEditText.addTextChangedListener() {
            itemName = it.toString()
        }

        createItem.setOnClickListener {

            if (itemName.isNotEmpty()) {
                if (itemCategory.isNotEmpty()) {
                    val startNewJob = Intent(this, MagusActivity::class.java)
                    startNewJob.putExtra("itemCategory", itemCategory)
                    startNewJob.putExtra("itemName", itemName)
                    startActivity(startNewJob)
                    this.finish();
                } else Toast.makeText(this, "Please choose the item category", Toast.LENGTH_LONG).show()
            } else Toast.makeText(this, "Please enter the item name", Toast.LENGTH_LONG).show()
        }
    }

    private fun unselectEverything(constraintLayout: ConstraintLayout) {
        for (image in constraintLayout) {
            image.setBackgroundResource(R.drawable.item_category_background_unchecked)
        }
    }

    private fun runFirstStepOfTutorial() {
        if (preferenceManager.getInt("tutorialCurrentStep", 0) == 1) {

            val fancyShowCaseView1 =
                FancyShowCaseView.Builder(this)
                    .title(getString(R.string.tutorial_1_1))
                    .focusOn(nameConstraintLayout)
                    .titleStyle(R.style.MyTitleStyle, Gravity.CENTER)
                    .focusShape(FocusShape.ROUNDED_RECTANGLE)
                    .roundRectRadius(90)
                    .enableAutoTextPosition()
                    .build()

            val fancyShowCaseView2 =
                FancyShowCaseView.Builder(this)
                    .title(getString(R.string.tutorial_1_2))
                    .focusOn(categoryHolder)
                    .titleStyle(R.style.MyTitleStyle, Gravity.CENTER)
                    .focusShape(FocusShape.ROUNDED_RECTANGLE)
                    .roundRectRadius(90)
                    .enableAutoTextPosition()
                    .build()

            val mQueue = FancyShowCaseQueue()
                .add(fancyShowCaseView1)
                .add(fancyShowCaseView2)
            mQueue.show()

            //Move to the second step
            preferenceManager.edit().putInt("tutorialCurrentStep", 2).apply()
        }
    }

}