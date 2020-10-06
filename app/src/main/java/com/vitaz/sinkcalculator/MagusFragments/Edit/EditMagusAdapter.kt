package com.vitaz.sinkcalculator.MagusFragments.Edit

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.vitaz.sinkcalculator.MagusFragments.Main.MainRuneListAdapter
import com.vitaz.sinkcalculator.Model.Rune
import com.vitaz.sinkcalculator.Model.SinkModifier
import com.vitaz.sinkcalculator.Model.Stat
import com.vitaz.sinkcalculator.R
import kotlinx.android.synthetic.main.fragment_edit_magus_list_item.view.*
import kotlinx.android.synthetic.main.fragment_main_magus_list_item.view.*

class EditMagusAdapter (
    val context: Context,
    val statList: MutableList<SinkModifier>,
    val selectedRune: Rune
): RecyclerView.Adapter<EditMagusAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_edit_magus_list_item, parent, false)
        return EditMagusAdapter.MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return statList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        
        if (statList[position].statName == selectedRune.statName) {
            statList[position].statPositive = selectedRune.bonus
            holder.itemView.positiveSinkStat.setText(selectedRune.bonus.toString())

        }

        // Arrange Negative Sink block
        holder.itemView.negativeSinkLayout.visibility = View.GONE
        holder.itemView.negativeLayoutSwitch.setOnCheckedChangeListener { _, switchIsChecked ->
            if (switchIsChecked) {
                holder.itemView.negativeSinkLayout.visibility = View.VISIBLE
                holder.itemView.statNameLayout.visibility = View.GONE
            } else {
                holder.itemView.negativeSinkLayout.visibility = View.GONE
                holder.itemView.statNameLayout.visibility = View.VISIBLE
            }
        }

        holder.itemView.positiveSinkStatMinus.setOnClickListener {
            statList[position].statPositive--
            holder.itemView.positiveSinkStat.setText(statList[position].statPositive.toString())
        }

        holder.itemView.positiveSinkStatPlus.setOnClickListener {
            statList[position].statPositive++
            holder.itemView.positiveSinkStat.setText(statList[position].statPositive.toString())
        }

        holder.itemView.negativeSinkStatMinus.setOnClickListener {
            statList[position].statNegative--
            holder.itemView.negativeSinkStat.setText(statList[position].statNegative.toString())
        }

        holder.itemView.negativeSinkStatPlus.setOnClickListener {
            statList[position].statNegative++
            holder.itemView.negativeSinkStat.setText(statList[position].statNegative.toString())
        }

        holder.itemView.positiveSinkStat.addTextChangedListener {
            if (it.toString() != "-"){
                if (it.toString() == "") {
                    statList[position].statPositive = 0
                } else statList[position].statPositive = it.toString().toInt()

            }
        }

        holder.itemView.negativeSinkStat.addTextChangedListener {
            if (it.toString() != "-") {
                if (it.toString() == "") {
                    statList[position].statNegative = 0
                } else statList[position].statNegative = it.toString().toInt()
            }
        }

        holder.itemView.statName.text = statList[position].statName

        val resourceId = context.resources.getIdentifier(statList[position].statImage, "drawable", context.packageName)
        holder.itemView.statImage.setImageResource(resourceId)


    }
}