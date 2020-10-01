package com.vitaz.sinkcalculator.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.vitaz.sinkcalculator.Services.RunesService

class MagusViewModel(application: Application): AndroidViewModel(application) {

    var activeListOfStats = RunesService.statList.toMutableList()

    var activeRuneList = RunesService.runes.toMutableList()


}