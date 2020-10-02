package com.vitaz.sinkcalculator.Model

class Stat(
    val statName: String,
    var isSelected: Boolean?,
    val statImage: String?
) {

    override fun toString(): String {
        return statName
    }

}