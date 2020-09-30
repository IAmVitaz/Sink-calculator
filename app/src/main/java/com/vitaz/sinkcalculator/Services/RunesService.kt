package com.vitaz.sinkcalculator.Services

import com.vitaz.sinkcalculator.Model.Rune

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

    val statList = ArrayList<String>(getAllStats(ArrayList(runes)))


    fun getAllStats(runes: ArrayList<Rune>) : ArrayList<String> {
        val stats = ArrayList<String>()
        stats.add(runes.get(0).stat)

        if (runes.size >= 1) {
            for (x in 1 until runes.size-1) {
                if (runes.get(x).stat != stats.last()){
                    stats.add(runes.get(x).stat)
                }
            }
        }
        return stats
    }

}