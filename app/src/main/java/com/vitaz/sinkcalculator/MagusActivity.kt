package com.vitaz.sinkcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.vitaz.sinkcalculator.Model.HistoryLog
import com.vitaz.sinkcalculator.ViewModel.MagusViewModel
import kotlinx.android.synthetic.main.fragment_edit_magus_list_item.view.*
import java.util.*

class MagusActivity : AppCompatActivity() {

    lateinit var mMagusViewModel: MagusViewModel

    var itemCategory = ""
    var itemName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_magus)

        mMagusViewModel = ViewModelProvider(this).get(MagusViewModel::class.java)

        itemCategory = intent!!.getStringExtra("itemCategory")

        itemName = intent!!.getStringExtra("itemName")

        if (mMagusViewModel.historyLogList.size == 0)  {
            mMagusViewModel.historyLogList.add(HistoryLog(Date(),"$itemName smithmagus", 0.0, null))
        }

        setupActionBarWithNavController(findNavController(R.id.navHostFragment))

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.navHostFragment)
        return navController.navigateUp() || return super.onSupportNavigateUp()
    }
}