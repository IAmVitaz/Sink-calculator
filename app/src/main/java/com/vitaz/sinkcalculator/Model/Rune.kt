package com.vitaz.sinkcalculator.Model

class Rune (
    val stat: String,
    val runeName: String,
    val bonus: Int,
    val sinkValue: Double,
    val minusSinkValue: Double,
    val maxOvermage: Int,
    val image: String
) {
    override fun toString(): String {
        return runeName
    }
}