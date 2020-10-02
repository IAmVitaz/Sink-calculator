package com.vitaz.sinkcalculator.MagusFragments.Main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.vitaz.sinkcalculator.Model.Rune
import com.vitaz.sinkcalculator.R
import kotlinx.android.synthetic.main.fragment_main_magus_list_item.view.*

class MainRuneListAdapter(
    val context: Context,
    val runeList: List<Rune>
): RecyclerView.Adapter<MainRuneListAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_main_magus_list_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return runeList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.itemView.runeName.text = runeList[position].runeName

        //set up rune images
        val resourceId = context.resources.getIdentifier(runeList[position].image, "drawable", context.packageName)
        holder.itemView.runeImage.setImageResource(resourceId)

        //set up rune characteristics
        val statValue = runeList[position].bonus
        val statSink = runeList[position].sinkValue
        val runeSink = statValue * statSink
        holder.itemView.runeBonus.text = "+${statValue.toString()}"
        holder.itemView.baseSink.text = "$statSink"
        holder.itemView.runeSink.text = "$runeSink"

        holder.itemView.rune_row_background.setOnClickListener {
            holder.itemView.findNavController().navigate(R.id.action_mainMagusFragment_to_editMagusFragment)
        }

    }
}