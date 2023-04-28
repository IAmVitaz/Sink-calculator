package com.vitaz.sinkcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.vitaz.sinkcalculator.Model.HistoryLog
import com.vitaz.sinkcalculator.ViewModel.MagusViewModel
import com.vitaz.sinkcalculator.databinding.ActivityMagusBinding
import java.util.*

class MagusActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMagusBinding
    lateinit var mMagusViewModel: MagusViewModel

    var itemCategory = ""
    var itemName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        mMagusViewModel = ViewModelProvider(this).get(MagusViewModel::class.java)
        itemCategory = intent!!.getStringExtra("itemCategory").toString()
        itemName = intent!!.getStringExtra("itemName").toString()
        mMagusViewModel.itemName = itemName

        if (mMagusViewModel.historyLogList.size == 0)  {
            mMagusViewModel.historyLogList.add(HistoryLog(Date(),"$itemName smithmagus", 0.0, null))
        }

        super.onCreate(savedInstanceState)
        binding = ActivityMagusBinding.inflate(layoutInflater)

        val view = binding.root
        setContentView(view)

        setupActionBarWithNavController(findNavController(R.id.navHostFragment))
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.navHostFragment)
        return navController.navigateUp() || return super.onSupportNavigateUp()
    }
}