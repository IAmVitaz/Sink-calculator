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
        Rune("Trap Power", "Ra Tra Per", 10, 2.0, 0.0, 50, "rune_tra_per"),
        Rune("Neutral Resistance Fix", "Neutral Res", 1, 2.0, 0.0, 50, "rune_res_neutral"),
        Rune("Neutral Resistance Fix", "Pa Re Neutral", 3, 2.0, 0.0,  50, "rune_res_neutral_pa"),
        Rune("Earth Resistance Fix", "Earth Res", 1, 2.0, 0.0, 50, "rune_res_earth"),
        Rune("Earth Resistance Fix", "Pa Re Earth", 3, 2.0, 0.0,  50, "rune_res_earth_pa"),
        Rune("Fire Resistance Fix", "Fire Res", 1, 2.0, 0.0, 50, "rune_res_fire"),
        Rune("Fire Resistance Fix", "Pa Re Fire", 3, 2.0, 0.0,  50, "rune_res_fire_pa"),
        Rune("Water Resistance Fix", "Water Res", 1, 2.0, 0.0, 50, "rune_res_water"),
        Rune("Water Resistance Fix", "Pa Re Water", 3, 2.0, 0.0,  50, "rune_res_water"),  //did not found pa rune
        Rune("Air Resistance Fix", "Air Res", 1, 2.0, 0.0, 50, "rune_res_air"),
        Rune("Air Resistance Fix", "Pa Re Air", 3, 2.0, 0.0,  50, "rune_res_air_pa"),
        Rune("Wisdom", "Wis", 1, 3.0, 2.0, 33, "rune_wis"),
        Rune("Wisdom", "Pa Wis", 3, 3.0, 2.0,  33, "rune_wis_pa"),
        Rune("Wisdom", "Ra Wis", 10, 3.0, 2.0, 33, "rune_wis_ra"),
        Rune("Prospecting", "Prospe", 1, 3.0, 2.0, 33, "rune_prospe"),
        Rune("Prospecting", "Pa Prospe", 3, 3.0, 2.0,  33, "rune_prospe_pa"),
        Rune("Lock", "Loc", 1, 4.0, 2.0, 25, "rune_loc"),
        Rune("Lock", "Pa Loc", 3, 4.0, 2.0,  25, "rune_loc_pa"),
        Rune("Dodge", "Dod", 1, 4.0, 2.0, 25, "rune_dod"),
        Rune("Dodge", "Pa Dod", 3, 4.0, 2.0,  25, "rune_dod_pa"),
        Rune("Neutral Damage", "Neutral Dam", 1, 5.0, 2.5, 20, "rune_dam_neutral"),
        Rune("Neutral Damage", "Pa Neutral Dam", 3, 5.0, 2.5,  20, "rune_dam_neutral_pa"),
        Rune("Earth Damage", "Earth Dam", 1, 5.0, 2.5, 20, "rune_dam_earth"),
        Rune("Earth Damage", "Pa Earth Dam", 3, 5.0, 2.5,  20, "rune_dam_earth_pa"),
        Rune("Fire Damage", "Fire Dam", 1, 5.0, 2.5, 20, "rune_dam_fire"),
        Rune("Fire Damage", "Pa Fire Dam", 3, 5.0, 2.5,  20, "rune_dam_fire_pa"),
        Rune("Water Damage", "Water Dam", 1, 5.0, 2.5, 20, "rune_dam_water"),
        Rune("Water Damage", "Pa Water Dam", 3, 5.0, 2.5,  20, "rune_dam_water_pa"),
        Rune("Air Damage", "Air Dam", 1, 5.0, 2.5, 20, "rune_dam_air"),
        Rune("Air Damage", "Pa Air Dam", 3, 5.0, 2.5,  20, "rune_dam_air_pa"),
        Rune("Critical Damage", "Cri Dam", 1, 5.0, 3.0, 20, "rune_dam_cri"),
        Rune("Critical Damage", "Pa Cri Dam", 3, 5.0, 3.0,  20, "rune_dam_cri_pa"),
        Rune("Pushback Damage", "Psh Dam", 1, 5.0, 3.0, 20, "rune_dam_psh"),
        Rune("Pushback Damage", "Pa Psh Dam", 3, 5.0, 3.0,  20, "rune_dam_psh_pa"),
        Rune("Trap Damage", "Tra", 1, 5.0, 0.0, 20, "rune_tra"),
        Rune("Trap Damage", "Pa Tra", 3, 5.0, 0.0,  20, "rune_tra_pa"),
        Rune("Hunting", "Hunting", 1, 5.0, 0.0, 0, "rune_hunting"),
        Rune("% Neutral Resistance", "Neutral Res Per", 1, 6.0, 3.0, 16, "rune_res_neutral_per"),
        Rune("% Earth Resistance", "Earth Res Per", 1, 6.0, 3.0, 16, "rune_res_earth_per"),
        Rune("% Fire Resistance", "Fire Res Per", 1, 6.0, 3.0, 16, "rune_res_fire_per"),
        Rune("% Water Resistance", "Water Res Per", 1, 6.0, 3.0, 16, "rune_res_water_per"),
        Rune("% Air Resistance", "Air Res Per", 1, 6.0, 3.0, 16, "rune_res_air_per"),
        Rune("MP Reduction", "MP Red", 1, 7.0, 4.0, 14, "rune_mp_red"),
        Rune("MP Reduction", "Pa MP Red", 3, 7.0, 4.0, 14, "rune_mp_red_pa"),
        Rune("AP Reduction", "AP Red", 1, 7.0, 4.0, 14, "rune_ap_red"),
        Rune("AP Reduction", "Pa AP Red", 3, 7.0, 4.0, 14, "rune_ap_red_pa"),
        Rune("MP Resist", "MP Res", 1, 7.0, 4.0, 14, "rune_mp_res"),
        Rune("MP Resist", "Pa MP Res", 3, 7.0, 4.0, 14, "rune_mp_res_pa"),
        Rune("AP Resist", "AP Res", 1, 7.0, 4.0, 14, "rune_ap_res"),
        Rune("AP Resist", "Pa AP Res", 3, 7.0, 4.0, 14, "rune_ap_res_pa"),
        Rune("Heals", "Hea", 1, 10.0, 5.0, 10, "rune_hea"),
        Rune("Heals", "Pa Hea", 3, 10.0, 5.0, 10, "rune_hea_pa"),
        Rune("Critical Hit", "Cri", 1, 10.0, 5.0, 10, "rune_cri"),
        Rune("Reflect", "Dam Ref", 1, 10.0, 0.0, 10, "rune_ref"),
        Rune("% Spell Damage", "Spe Dam Per", 1, 15.0, 8.0, 6, "rune_spe_dam_per"),
        Rune("% Ranged Damage", "Ra Dam Per", 1, 15.0, 8.0, 6, "rune_ra_dam_per"),
        Rune("% Ranged Resistance", "Ra Res Per", 1, 15.0, 8.0, 6, "rune_ra_res_per"),
        Rune("% Weapon Damage", "Ra Dam Per", 1, 15.0, 8.0, 6, "rune_we_dam_per"),
        Rune("% Melee Damage", "Me Dam Per", 1, 15.0, 8.0, 6, "rune_me_dam_per"),
        Rune("% Melee Resistance", "Me Res Per", 1, 15.0, 8.0, 6, "rune_me_res_per"),
        Rune("Damage", "Dam", 1, 20.0, 0.0, 5, "rune_dam"),
        Rune("Summons", "Summo", 1, 30.0, 0.0, 3, "rune_summo"),
        Rune("Range", "Range", 1, 51.0, 25.0, 1, "rune_range"),
        Rune("MP", "MP Ga", 1, 90.0, 45.0, 1, "rune_mp"),
        Rune("AP", "AP Ga", 1, 100.0, 50.0, 1, "rune_ap")
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
        Stat("MP Resist", false, "stat_mp_parry"),
        Stat("AP Resist", false, "stat_ap_parry"),
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