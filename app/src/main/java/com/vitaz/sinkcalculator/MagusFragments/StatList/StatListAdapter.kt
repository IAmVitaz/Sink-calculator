package com.vitaz.sinkcalculator.MagusFragments.StatList

import android.content.Context
import android.graphics.Color
import android.graphics.Color.parseColor
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vitaz.sinkcalculator.Model.Stat
import com.vitaz.sinkcalculator.R
import kotlinx.android.synthetic.main.fragment_main_magus_list_item.view.*
import kotlinx.android.synthetic.main.fragment_stat_list_magus_item.view.*


class StatListAdapter (
    val context: Context,
    var statList: MutableList<Stat>
): RecyclerView.Adapter<StatListAdapter.MyViewHolder>() {

    private var lowerHeaderPosition = findLowerHeaderPosition(statList)

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

        holder.itemView.statName.text = statList[position].statName
        if (statList[position].isSelected == null) {
            holder.itemView.statName.setTypeface(null, Typeface.BOLD)
        } else {
            holder.itemView.statName.setTypeface(null, Typeface.NORMAL)
        }

        //set stat image. remove if null
        holder.itemView.statImage.visibility = View.VISIBLE
        if (statList[position].statImage != null){
            val resourceId = context.resources.getIdentifier(statList[position].statImage, "drawable", context.packageName)
            holder.itemView.statImage.setImageResource(resourceId)
        } else {
            holder.itemView.statImage.visibility = View.INVISIBLE
        }

        // change row background
        if (statList[position].isSelected == null) {
            holder.itemView.statRowBackground.setBackgroundColor(Color.parseColor("#00181814"))
        } else if (position % 2 == 1) {
            holder.itemView.statRowBackground.setBackgroundColor(Color.parseColor("#BB2A2E27"))
        } else holder.itemView.statRowBackground.setBackgroundColor(Color.parseColor("#BB21251C"))

        // set add/remove button. remove if null
        holder.itemView.actionButton.visibility = View.VISIBLE
        when {
            statList[position].isSelected == null -> {
                holder.itemView.actionButton.visibility = View.INVISIBLE
            }
            statList[position].isSelected!! -> {
                holder.itemView.actionButton.setImageResource(R.drawable.remove_circle_icon)
                holder.itemView.actionButton.drawable.setTint(parseColor("#d45e57"))
            }
            else -> {
                holder.itemView.actionButton.setImageResource(R.drawable.add_circle_icon)
                holder.itemView.actionButton.drawable.setTint(parseColor("#15AF25"))
            }
        }

        holder.itemView.actionButton.setOnClickListener {

//            if (lowerHeaderPosition < 1) lowerHeaderPosition = 1

            val itemToMove = statList[position]

            if (!statList[position].isSelected!!){
                statList[position].isSelected = true
                holder.itemView.actionButton.setImageResource(R.drawable.remove_circle_icon)
                holder.itemView.actionButton.drawable.setTint(parseColor("#FF0000"))
                statList.add(lowerHeaderPosition, itemToMove)
                statList.removeAt(position+1)
                lowerHeaderPosition ++
                notifyDataSetChanged()
            } else {
                statList[position].isSelected = false
                holder.itemView.actionButton.setImageResource(R.drawable.add_circle_icon)
                holder.itemView.actionButton.drawable.setTint(parseColor("#008000"))
                statList.add(lowerHeaderPosition+1, itemToMove)
                statList.removeAt(position)
                lowerHeaderPosition --
                notifyDataSetChanged()
            }

        }

    }


    private fun findLowerHeaderPosition(list: MutableList<Stat>): Int {
        var position = 1
        list.forEach(){
            if (it.isSelected == true) {
                position ++
            }
        }
        return position
    }




}