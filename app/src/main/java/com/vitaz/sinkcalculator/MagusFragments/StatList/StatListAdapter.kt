package com.vitaz.sinkcalculator.MagusFragments.StatList

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginLeft
import androidx.recyclerview.widget.RecyclerView
import com.vitaz.sinkcalculator.MagusFragments.Main.MainRuneListAdapter
import com.vitaz.sinkcalculator.Model.Stat
import com.vitaz.sinkcalculator.R
import kotlinx.android.synthetic.main.fragment_main_magus_list_item.view.*
import kotlinx.android.synthetic.main.fragment_stat_list_magus_item.view.*

class StatListAdapter (
    val context: Context,
    val statList: List<Stat>
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

        holder.itemView.statName.text = statList[position].stat
//        if (statList[position].statImage == null) {
//            val parameter = holder.itemView.statName.layoutParams
//            holder.itemView.statName.layoutParams()
//        }


        //set stat image. remove if null
        if (statList[position].statImage != null){
            val resourceId = context.resources.getIdentifier(statList[position].statImage, "drawable", context.packageName)
            holder.itemView.statImage.setImageResource(resourceId)
        } else {
            holder.itemView.statImage.visibility = View.INVISIBLE
        }

        // set add/remove button. remove if null
        when {
            statList[position].isSelected == null -> {
                holder.itemView.actionButton.visibility = View.INVISIBLE
            }
            statList[position].isSelected!! -> {
                holder.itemView.actionButton.setImageResource(R.drawable.add_circle_icon)
            }
            else -> {
                holder.itemView.actionButton.setImageResource(R.drawable.remove_circle_icon)
            }
        }

    }

}