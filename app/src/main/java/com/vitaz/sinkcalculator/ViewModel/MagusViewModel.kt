package com.vitaz.sinkcalculator.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.vitaz.sinkcalculator.Model.Stat
import com.vitaz.sinkcalculator.Services.RunesService

class MagusViewModel: ViewModel() {

    var activeListOfStats = RunesService.createNewStatSet()

    var activeRuneList = RunesService.runes.toMutableList()

    var statLowerHeaderPosition = 1


}