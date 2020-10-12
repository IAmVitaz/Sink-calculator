package com.vitaz.sinkcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import kotlinx.android.synthetic.main.fragment_edit_magus_list_item.view.*

class MagusActivity : AppCompatActivity() {

    var itemCategory = ""
    var itemName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_magus)

        itemCategory = intent!!.getStringExtra("itemCategory")

        itemName = intent!!.getStringExtra("itemName")

        setupActionBarWithNavController(findNavController(R.id.navHostFragment))

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.navHostFragment)
        return navController.navigateUp() || return super.onSupportNavigateUp()
    }
}