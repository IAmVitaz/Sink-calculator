package com.vitaz.sinkcalculator.Model

import java.util.*


class HistoryLog (
    val timeStamp: Date,
    val message: String,
    val sink: Double,
    val isPositiveOutcome: Boolean?
) {
    override fun toString(): String {
        return timeStamp.toString()
    }
}