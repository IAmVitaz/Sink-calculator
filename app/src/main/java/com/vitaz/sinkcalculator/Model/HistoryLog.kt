package com.vitaz.sinkcalculator.Model

import java.util.*


class HistoryLog (
    val timeStamp: Date,
    val message: String,
    val sink: Double
) {
    override fun toString(): String {
        return timeStamp.toString()
    }
}