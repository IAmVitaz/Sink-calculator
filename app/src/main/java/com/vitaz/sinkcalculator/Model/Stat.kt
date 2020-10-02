package com.vitaz.sinkcalculator.Model

class Stat(
    val statName: String,
    val sink: Double,
    val minusSink: Double,
    var isSelected: Boolean?,
    val statImage: String?
) {

    override fun toString(): String {
        return statName
    }

}