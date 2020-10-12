package com.vitaz.sinkcalculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.iterator
import androidx.core.widget.addTextChangedListener
import kotlinx.android.synthetic.main.activity_create_new_item.*

class CreateNewItemActivity : AppCompatActivity() {

    var itemCategory = ""
    var itemName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_new_item)



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


    fun unselectEverything(constraintLayout: ConstraintLayout) {
        for (image in constraintLayout) {
            image.setBackgroundResource(R.drawable.item_category_background_unchecked)
        }
    }

//    fun pickCategory (imageView: ImageView): String {
//        var imageName = ""
//
//
//
//
//        return imageName
//    }
}