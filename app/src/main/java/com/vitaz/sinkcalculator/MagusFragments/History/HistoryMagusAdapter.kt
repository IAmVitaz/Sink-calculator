package com.vitaz.sinkcalculator.MagusFragments.History

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.vitaz.sinkcalculator.Model.HistoryLog
import com.vitaz.sinkcalculator.R
import kotlinx.android.synthetic.main.fragment_history_magus_list_item.view.*
import kotlinx.android.synthetic.main.fragment_stat_list_magus_item.view.*
import java.text.SimpleDateFormat

class HistoryMagusAdapter(
    val context: Context,
    val logList: List<HistoryLog>
): RecyclerView.Adapter<HistoryMagusAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.fragment_history_magus_list_item, parent, false)
        return HistoryMagusAdapter.MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return logList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val format = SimpleDateFormat("HH:mm:ss")
        val date = logList[position].timeStamp
        val timeStamp = format.format(date)
        holder.itemView.timeStamp.text = timeStamp

        holder.itemView.log.text = logList[position].message
        holder.itemView.sink.text = "${logList[position].sink} sink"

        // change row background
        if (position % 2 == 1) {
            holder.itemView.historyRowBackground.setBackgroundColor(ContextCompat.getColor(context, R.color.tableRowBackgroundOdd))
        } else holder.itemView.historyRowBackground.setBackgroundColor(ContextCompat.getColor(context, R.color.tableRowBackgroundEven))

        when {
            logList[position].isPositiveOutcome == null -> {
                holder.itemView.log.setTypeface(null, Typeface.BOLD)
                holder.itemView.log.setTextColor(ContextCompat.getColor(context, R.color.white))
                holder.setIsRecyclable(false)
            }
            logList[position].isPositiveOutcome!! -> {
                holder.itemView.log.setTextColor(ContextCompat.getColor(context, R.color.success))
            }
            else -> {
                holder.itemView.log.setTextColor(ContextCompat.getColor(context, R.color.failure))
            }
        }
    }
}