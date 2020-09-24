package com.vitaz.sinkcalculator.MagusFragments.Main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vitaz.sinkcalculator.R
import com.vitaz.sinkcalculator.Services.RunesService
import kotlinx.android.synthetic.main.fragment_main_magus_list_item.view.*

class MainRuneListAdapter: RecyclerView.Adapter<MainRuneListAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_main_magus_list_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return RunesService.runes.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.runeName.text = RunesService.runes[position].runeName

        val statValue = RunesService.runes[position].bonus
        val statSink = RunesService.runes[position].sinkValue
        val runeSink = statValue * statSink
        holder.itemView.runeBonus.text = "+${statValue.toString()}"
        holder.itemView.runeSink.text = "${runeSink.toInt()}  ($statSink*$statValue)"


    }
}