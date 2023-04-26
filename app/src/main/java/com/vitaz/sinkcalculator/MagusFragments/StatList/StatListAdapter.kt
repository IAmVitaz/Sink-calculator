package com.vitaz.sinkcalculator.MagusFragments.StatList

import android.content.Context
import android.graphics.Color.TRANSPARENT
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.vitaz.sinkcalculator.Model.Stat
import com.vitaz.sinkcalculator.R
import com.vitaz.sinkcalculator.databinding.FragmentStatListMagusItemBinding

class StatListAdapter (
    val context: Context,
    var statList: MutableList<Stat>
): RecyclerView.Adapter<StatListAdapter.MyViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    private var lowerHeaderPosition = findLowerHeaderPosition(statList)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = FragmentStatListMagusItemBinding.inflate(inflater, parent, false)
        return MyViewHolder(view)

    }

    override fun getItemCount(): Int {
        return statList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val stat = statList[position]
        holder.bindViewHolder(stat, position)
    }

    inner class MyViewHolder(
        private val view: FragmentStatListMagusItemBinding
    ) : RecyclerView.ViewHolder(view.root) {
        fun bindViewHolder(log: Stat, position: Int) {
            view.statName.text = statList[position].statName
            if (statList[position].isSelected == null) {
                view.statName.setTypeface(null, Typeface.BOLD)
            } else {
                view.statName.setTypeface(null, Typeface.NORMAL)
            }

            //set stat image. remove if null
            view.statImage.visibility = View.VISIBLE
            if (statList[position].statImage != null){
                val resourceId = context.resources.getIdentifier(statList[position].statImage, "drawable", context.packageName)
                view.statImage.setImageResource(resourceId)
            } else {
                view.statImage.visibility = View.INVISIBLE
            }

            // change row background
            if (statList[position].isSelected == null) {
                view.statRowBackground.setBackgroundColor(TRANSPARENT)
            } else if (position % 2 == 1) {
                view.statRowBackground.setBackgroundColor(ContextCompat.getColor(context, R.color.tableRowBackgroundOdd))
            } else view.statRowBackground.setBackgroundColor(ContextCompat.getColor(context, R.color.tableRowBackgroundEven))

            // set add/remove button. remove if null
            view.actionButton.visibility = View.VISIBLE
            when {
                statList[position].isSelected == null -> {
                    view.actionButton.visibility = View.INVISIBLE
                }
                statList[position].isSelected!! -> {
                    view.actionButton.setImageResource(R.drawable.remove_circle_icon)
                    view.actionButton.drawable.setTint(ContextCompat.getColor(context, R.color.failure))
                }
                else -> {
                    view.actionButton.setImageResource(R.drawable.add_circle_icon)
                    view.actionButton.drawable.setTint(ContextCompat.getColor(context, R.color.success))
                }
            }

            view.statRowBackground.setOnClickListener {

//            if (lowerHeaderPosition < 1) lowerHeaderPosition = 1
                if (view.actionButton.isVisible) {
                    val itemToMove = statList[position]

                    if (!statList[position].isSelected!!){
                        statList[position].isSelected = true
                        view.actionButton.setImageResource(R.drawable.remove_circle_icon)
                        view.actionButton.drawable.setTint(ContextCompat.getColor(context, R.color.failure))
                        statList.add(lowerHeaderPosition, itemToMove)
                        statList.removeAt(position+1)
                        lowerHeaderPosition ++
                        notifyDataSetChanged()
                    } else {
                        statList[position].isSelected = false
                        view.actionButton.setImageResource(R.drawable.add_circle_icon)
                        view.actionButton.drawable.setTint(ContextCompat.getColor(context, R.color.success))
                        statList.add(lowerHeaderPosition+1, itemToMove)
                        statList.removeAt(position)
                        lowerHeaderPosition --
                        notifyDataSetChanged()
                    }
                }
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
