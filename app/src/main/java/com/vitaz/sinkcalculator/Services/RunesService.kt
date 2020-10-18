package com.vitaz.sinkcalculator.Services

import android.util.Log
import com.vitaz.sinkcalculator.Model.Rune
import com.vitaz.sinkcalculator.Model.SinkModifier
import com.vitaz.sinkcalculator.Model.Stat
import kotlin.math.round

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
        Rune("Pods", "Ra Pod", 100, 0.25, 0.125, 404, "rune_pod_ra"),
        Rune("Strength", "Stre", 1, 1.0, 1.0, 101, "rune_stre"),
        Rune("Strength", "Pa Stre", 3, 1.0, 1.0,  101, "rune_stre_pa"),
        Rune("Strength", "Ra Stre", 10, 1.0, 1.0, 101, "rune_stre_ra"),
        Rune("Intelligence", "Int", 1, 1.0, 1.0, 101, "rune_int"),
        Rune("Intelligence", "Pa Int", 3, 1.0, 1.0,  101, "rune_int_pa"),
        Rune("Intelligence", "Ra Int", 10, 1.0, 1.0, 101, "rune_int_ra"),
        Rune("Agility", "Agi", 1, 1.0, 1.0, 101, "rune_agi"),
        Rune("Agility", "Pa Agi", 3, 1.0, 1.0,  101, "rune_agi_pa"),
        Rune("Agility", "Ra Agi", 10, 1.0, 1.0, 101, "rune_agi_ra"),
        Rune("Chance", "Cha", 1, 1.0, 1.0, 101, "rune_cha"),
        Rune("Chance", "Pa Cha", 3, 1.0, 1.0,  101, "rune_cha_pa"),
        Rune("Chance", "Ra Cha", 10, 1.0, 1.0, 101, "rune_cha_ra"),
        Rune("Critical Resistance", "Cri Res", 1, 2.0, 1.0, 50, "rune_cri_res"),
        Rune("Critical Resistance", "Pa Cri Res", 3, 2.0, 1.0,  50, "rune_cri_res_pa"),
        Rune("Pushback Resistance", "Psh Res", 1, 2.0, 1.0, 50, "rune_psh_res"),
        Rune("Pushback Resistance", "Pa Psh Res", 3, 2.0, 1.0,  50, "rune_psh_res_pa"),
        Rune("Power", "Pow", 1, 2.0, 0.0, 50, "rune_pow"),
        Rune("Power", "Pa Pow", 3, 2.0, 0.0,  50, "rune_pow_pa"),
        Rune("Power", "Ra Pow", 10, 2.0, 0.0, 50, "rune_pow_ra"),
        Rune("Trap Power", "Tra Per", 1, 2.0, 0.0, 50, "rune_tra_per"),
        Rune("Trap Power", "Pa Tra Per", 3, 2.0, 0.0,  50, "rune_tra_per"),
        Rune("Trap Power", "Ra Tra Per", 10, 2.0, 0.0, 50, "rune_tra_per")


        )

    var statList = listOf<Stat>(
        Stat("Selected:",null,null),
        Stat("Unselected:", null,null),
        Stat("Initiative", false, "stat_initiative"),
        Stat("Vitality", false, "stat_vitality"),
        Stat("Pods", false, "stat_pods"),
        Stat("Strength", false, "stat_strength"),
        Stat("Intelligence", false, "stat_intelligence"),
        Stat("Agility", false, "stat_agility"),
        Stat("Chance", false, "stat_chance"),
        Stat("Critical Resistance", false, "stat_critical_resistance"),
        Stat("Pushback Resistance", false, "stat_pushback_resistance"),
        Stat("Power", false, "stat_power"),
        Stat("Trap Power", false, "stat_trap_power"),
        Stat("Neutral Resistance Fix", false, "stat_resistance_neutral"),
        Stat("Earth Resistance Fix", false, "stat_resistance_earth"),
        Stat("Fire Resistance Fix", false, "stat_resistance_fire"),
        Stat("Water Resistance Fix", false, "stat_resistance_water"),
        Stat("Air Resistance Fix", false, "stat_resistance_air"),
        Stat("Wisdom", false, "stat_wisdom"),
        Stat("Prospecting", false, "stat_prospecting"),
        Stat("Lock", false, "stat_lock"),
        Stat("Dodge", false, "stat_dodge"),
        Stat("Neutral Damage", false, "stat_damage_neutral"),
        Stat("Earth Damage", false, "stat_strength"),
        Stat("Fire Damage", false, "stat_intelligence"),
        Stat("Water Damage", false, "stat_chance"),
        Stat("Air Damage", false, "stat_agility"),
        Stat("Critical Damage", false, "stat_critical_damage"),
        Stat("Pushback Damage", false, "stat_pushback_damage"),
        Stat("Trap Damage", false, "stat_trap_damage"),
        Stat("Hunting", false, "stat_hunter"),
        Stat("% Neutral Resistance", false, "stat_resistance_neutral"),
        Stat("% Earth Resistance", false, "stat_resistance_earth"),
        Stat("% Fire Resistance", false, "stat_resistance_fire"),
        Stat("% Water Resistance", false, "stat_resistance_water"),
        Stat("% Air Resistance", false, "stat_resistance_air"),
        Stat("MP Reduction", false, "stat_mp_reduction"),
        Stat("AP Reduction", false, "stat_ap_reduction"),
        Stat("MP Parry", false, "stat_mp_parry"),
        Stat("AP Parry", false, "stat_ap_parry"),
        Stat("Heals", false, "stat_heals"),
        Stat("Critical Hit", false, "stat_critical"),
        Stat("Reflect", false, "stat_reflect"),
        Stat("% Spell Damage", false, "stat_spell_damage"),
        Stat("% Ranged Damage", false, "stat_ranged_damage"),
        Stat("% Ranged Resistance", false, "stat_ranged_resistance"),
        Stat("% Weapon Damage", false, "stat_weapon_damage"),
        Stat("% Melee Damage", false, "stat_melee_damage"),
        Stat("% Melee Resistance", false, "stat_melee_resistance"),
        Stat("Damage", false, "stat_damage"),
        Stat("Summons", false, "stat_summons"),
        Stat("Range", false, "stat_range"),
        Stat("MP", false, "stat_mp"),
        Stat("AP", false, "stat_ap")
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