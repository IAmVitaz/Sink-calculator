package com.vitaz.sinkcalculator.MagusFragments.Edit

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.vitaz.sinkcalculator.Model.Rune
import com.vitaz.sinkcalculator.Model.SinkModifier
import com.vitaz.sinkcalculator.R
import com.vitaz.sinkcalculator.databinding.FragmentEditMagusListItemBinding

class EditMagusAdapter (
    val context: Context,
    val statList: MutableList<SinkModifier>,
    val selectedRune: Rune
): RecyclerView.Adapter<EditMagusAdapter.MyViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = FragmentEditMagusListItemBinding.inflate(inflater, parent, false)
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
        private val view: FragmentEditMagusListItemBinding
    ) : RecyclerView.ViewHolder(view.root) {

        fun bindViewHolder(stat: SinkModifier, position: Int) {
            if (statList[position].statName == selectedRune.statName) {
                statList[position].statPositive = selectedRune.bonus
                view.positiveSinkStat.setText(selectedRune.bonus.toString())

            }

            // Arrange Negative Sink block
            view.negativeSinkLayout.visibility = View.GONE
            view.negativeLayoutSwitch.setOnCheckedChangeListener { _, switchIsChecked ->
                if (switchIsChecked) {
                    view.negativeSinkLayout.visibility = View.VISIBLE
                    view.statNameLayout.visibility = View.GONE
                    statList[position].isNegative = true

                    statList[position].statNegative = statList[position].statPositive
                    view.negativeSinkStat.setText(statList[position].statNegative.toString())
                    statList[position].statPositive = 0
                    view.positiveSinkStat.setText(statList[position].statPositive.toString())
                } else {
                    view.negativeSinkLayout.visibility = View.GONE
                    view.statNameLayout.visibility = View.VISIBLE
                    statList[position].isNegative = false

                    statList[position].statPositive = statList[position].statNegative
                    view.positiveSinkStat.setText(statList[position].statPositive.toString())
                    statList[position].statNegative = 0
                    view.negativeSinkStat.setText(statList[position].statNegative.toString())
                }
            }

            view.positiveSinkStatMinus.setOnClickListener {
                statList[position].statPositive--
                view.positiveSinkStat.setText(statList[position].statPositive.toString())
            }

            view.positiveSinkStatPlus.setOnClickListener {
                statList[position].statPositive++
                view.positiveSinkStat.setText(statList[position].statPositive.toString())
            }

            view.negativeSinkStatMinus.setOnClickListener {
                statList[position].statNegative--
                view.negativeSinkStat.setText(statList[position].statNegative.toString())
            }

            view.negativeSinkStatPlus.setOnClickListener {
                statList[position].statNegative++
                view.negativeSinkStat.setText(statList[position].statNegative.toString())
            }

            view.positiveSinkStat.addTextChangedListener {
                if (it.toString() != "-"){
                    if (it.toString() == "") {
                        statList[position].statPositive = 0
                    } else statList[position].statPositive = it.toString().toInt()

                }
            }

            view.negativeSinkStat.addTextChangedListener {
                if (it.toString() != "-") {
                    if (it.toString() == "") {
                        statList[position].statNegative = 0
                    } else statList[position].statNegative = it.toString().toInt()
                }
            }

            //change row color
            if (position % 2 == 1) {
                view.editStatRowBackground.setBackgroundColor(ContextCompat.getColor(context, R.color.tableRowBackgroundOdd))
            } else view.editStatRowBackground.setBackgroundColor(ContextCompat.getColor(context, R.color.tableRowBackgroundEven))

            view.statName.text = statList[position].statName

            val resourceId = context.resources.getIdentifier(statList[position].statImage, "drawable", context.packageName)
            view.statImage.setImageResource(resourceId)
        }
    }

//    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//
//        if (statList[position].statName == selectedRune.statName) {
//            statList[position].statPositive = selectedRune.bonus
//            holder.itemView.positiveSinkStat.setText(selectedRune.bonus.toString())
//
//        }
//
//        // Arrange Negative Sink block
//        holder.itemView.negativeSinkLayout.visibility = View.GONE
//        holder.itemView.negativeLayoutSwitch.setOnCheckedChangeListener { _, switchIsChecked ->
//            if (switchIsChecked) {
//                holder.itemView.negativeSinkLayout.visibility = View.VISIBLE
//                holder.itemView.statNameLayout.visibility = View.GONE
//                statList[position].isNegative = true
//
//                statList[position].statNegative = statList[position].statPositive
//                holder.itemView.negativeSinkStat.setText(statList[position].statNegative.toString())
//                statList[position].statPositive = 0
//                holder.itemView.positiveSinkStat.setText(statList[position].statPositive.toString())
//            } else {
//                holder.itemView.negativeSinkLayout.visibility = View.GONE
//                holder.itemView.statNameLayout.visibility = View.VISIBLE
//                statList[position].isNegative = false
//
//                statList[position].statPositive = statList[position].statNegative
//                holder.itemView.positiveSinkStat.setText(statList[position].statPositive.toString())
//                statList[position].statNegative = 0
//                holder.itemView.negativeSinkStat.setText(statList[position].statNegative.toString())
//            }
//        }
//
//        holder.itemView.positiveSinkStatMinus.setOnClickListener {
//            statList[position].statPositive--
//            holder.itemView.positiveSinkStat.setText(statList[position].statPositive.toString())
//        }
//
//        holder.itemView.positiveSinkStatPlus.setOnClickListener {
//            statList[position].statPositive++
//            holder.itemView.positiveSinkStat.setText(statList[position].statPositive.toString())
//        }
//
//        holder.itemView.negativeSinkStatMinus.setOnClickListener {
//            statList[position].statNegative--
//            holder.itemView.negativeSinkStat.setText(statList[position].statNegative.toString())
//        }
//
//        holder.itemView.negativeSinkStatPlus.setOnClickListener {
//            statList[position].statNegative++
//            holder.itemView.negativeSinkStat.setText(statList[position].statNegative.toString())
//        }
//
//        holder.itemView.positiveSinkStat.addTextChangedListener {
//            if (it.toString() != "-"){
//                if (it.toString() == "") {
//                    statList[position].statPositive = 0
//                } else statList[position].statPositive = it.toString().toInt()
//
//            }
//        }
//
//        holder.itemView.negativeSinkStat.addTextChangedListener {
//            if (it.toString() != "-") {
//                if (it.toString() == "") {
//                    statList[position].statNegative = 0
//                } else statList[position].statNegative = it.toString().toInt()
//            }
//        }
//
//        //change row color
//        if (position % 2 == 1) {
//            holder.itemView.editStat_row_background.setBackgroundColor(ContextCompat.getColor(context, R.color.tableRowBackgroundOdd))
//        } else holder.itemView.editStat_row_background.setBackgroundColor(ContextCompat.getColor(context, R.color.tableRowBackgroundEven))
//
//        holder.itemView.statName.text = statList[position].statName
//
//        val resourceId = context.resources.getIdentifier(statList[position].statImage, "drawable", context.packageName)
//        holder.itemView.statImage.setImageResource(resourceId)
//
//
//    }
}