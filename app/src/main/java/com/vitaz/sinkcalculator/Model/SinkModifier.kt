package com.vitaz.sinkcalculator.Model

class SinkModifier(
    val statName: String,
    val statImage: String,
    val sinkPositiveValue: Double,
    val sinkNegativeValue: Double,
    var statPositive: Int,
    var statNegative: Int
) {

    override fun toString(): String {
        return "$statName: $sinkPositiveValue"
    }

}