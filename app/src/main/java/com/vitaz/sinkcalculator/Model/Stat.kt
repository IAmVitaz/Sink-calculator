package com.vitaz.sinkcalculator.Model

class Stat(
    val stat: String,
    var isSelected: Boolean?,
    val statImage: String?
) {

    override fun toString(): String {
        return stat
    }

}