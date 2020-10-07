package com.vitaz.sinkcalculator.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.vitaz.sinkcalculator.Model.HistoryLog
import com.vitaz.sinkcalculator.Model.Stat
import com.vitaz.sinkcalculator.Services.RunesService

class MagusViewModel: ViewModel() {

    var activeListOfStats = RunesService.createNewStatSet()

    var activeListOfRunes = RunesService.createNewRuneSet(activeListOfStats)

    var listOfSinkModifiers = RunesService.createNewListOfSinkModifiers(activeListOfStats)

    var previousSink: Double = 0.0

    var currentSink: Double = 0.0

    var historyLogList = mutableListOf<HistoryLog>(HistoryLog("Gobbal boots smithmagus", 0.0))

}