package com.vitaz.sinkcalculator.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Rune (
    val statName: String,
    val runeName: String,
    val bonus: Int,
    val sinkValue: Double,
    val minusSinkValue: Double,
    val maxOvermage: Int,
    val image: String
): Parcelable {
    override fun toString(): String {
        return runeName
    }
}