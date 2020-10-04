package com.vitaz.sinkcalculator.Services

import com.vitaz.sinkcalculator.Model.Rune
import com.vitaz.sinkcalculator.Model.SinkModifier
import com.vitaz.sinkcalculator.Model.Stat

object RunesService {

    val runes = listOf<Rune>(
        Rune("Initiative", "Ini", 10, 0.1, 0.05,  1010, "rune_ini"),
        Rune("Initiative", "Pa Ini", 30, 0.1, 0.05,  1010, "rune_ini_pa"),
        Rune("Initiative", "Ra Ini", 100, 0.1, 0.05,  1010, "rune_ini_ra"),
        Rune("Vitality", "Vit", 5, 0.2, 0.1, 505, "rune_vit"),
        Rune("Vitality", "Pa Vit", 15, 0.2, 0.1,  505, "rune_vit_pa"),
        Rune("Vitality", "Ra Vit", 50, 0.2, 0.1, 505, "rune_vit_ra"),
        Rune("Pods", "Pod", 10, 0.25, 0.125, 404, "rune_pod"),
        Rune("Pods", "Pa Pod", 30, 0.25, 0.125,  404, "rune_pod_pa"),
        Rune("Pods", "Ra Pod", 100, 0.25, 0.125, 404, "rune_pod_ra")

        )

    var statList = listOf<Stat>(
        Stat("Selected:",null,null),
        Stat("Unselected:", null,null),
        Stat("Initiative", false, "stat_initiative"),
        Stat("Vitality", false, "stat_vitality"),
        Stat("Pods", false, "stat_pods")
        )

    fun createNewStatSet() : MutableList<Stat> {
        val newList = mutableListOf<Stat>()
        statList.forEach(){
            newList.add(Stat(it.statName, it.isSelected, it.statImage))
        }

        return newList
    }

    fun createNewRuneSet(statList: MutableList<Stat>): MutableList<Rune> {
        val runeList = mutableListOf<Rune>()

        statList.forEach() {stat ->
            runes.forEach() {rune ->
                if (stat.isSelected == true && stat.statName.equals(rune.statName)) {
                    runeList.add(rune)
                }
            }
        }

        return runeList
    }

    fun createNewListOfSinkModifiers(statList: MutableList<Stat>): MutableList<SinkModifier> {
        var newSinkModifierList = mutableListOf<SinkModifier>()
        statList.forEach() statArray@ {Stat ->
            runes.forEach() {Rune ->
                if (Stat.isSelected == true && Stat.statName.equals(Rune.statName)){
                    newSinkModifierList.add(SinkModifier(Stat.statName, Stat.statImage!!, Rune.sinkValue, Rune.minusSinkValue, 0, 0))
                    return@statArray
                }
            }
        }
        return newSinkModifierList
    }



//    fun getAllStats(runes: ArrayList<Rune>) : ArrayList<String> {
//        val stats = ArrayList<String>()
//        stats.add(runes.get(0).stat)
//
//        if (runes.size >= 1) {
//            for (x in 1 until runes.size-1) {
//                if (runes.get(x).stat != stats.last()){
//                    stats.add(runes.get(x).stat)
//                }
//            }
//        }
//        return stats
//    }

}