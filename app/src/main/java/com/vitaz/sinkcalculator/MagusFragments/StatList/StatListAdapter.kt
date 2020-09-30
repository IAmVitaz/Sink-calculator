package com.vitaz.sinkcalculator.MagusFragments.StatList

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vitaz.sinkcalculator.MagusFragments.Main.MainRuneListAdapter
import com.vitaz.sinkcalculator.R
import kotlinx.android.synthetic.main.fragment_stat_list_magus_item.view.*

class StatListAdapter (
    val context: Context,
    val statList: List<String>
): RecyclerView.Adapter<StatListAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_stat_list_magus_item, parent, false)
        return StatListAdapter.MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return statList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.statName.text = statList[position].toString()

    }

}