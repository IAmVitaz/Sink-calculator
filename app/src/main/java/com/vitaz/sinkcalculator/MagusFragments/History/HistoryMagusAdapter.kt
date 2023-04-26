package com.vitaz.sinkcalculator.MagusFragments.History

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.vitaz.sinkcalculator.Model.HistoryLog
import com.vitaz.sinkcalculator.R
import com.vitaz.sinkcalculator.databinding.FragmentHistoryMagusListItemBinding
import java.text.SimpleDateFormat

class HistoryMagusAdapter(
    val context: Context,
    val logList: List<HistoryLog>
): RecyclerView.Adapter<HistoryMagusAdapter.MyViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = FragmentHistoryMagusListItemBinding.inflate(inflater, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return logList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val log = logList[position]
        holder.bindViewHolder(log, position)
    }

    inner class MyViewHolder(
        private val view: FragmentHistoryMagusListItemBinding
    ) : RecyclerView.ViewHolder(view.root) {
        fun bindViewHolder(log: HistoryLog, position: Int) {
            val format = SimpleDateFormat("HH:mm:ss")
            val date = logList[position].timeStamp
            val timeStamp = format.format(date)
            view.timeStamp.text = timeStamp

            view.log.text = logList[position].message
            view.sink.text = "${logList[position].sink} sink"

            // change row background
            if (position % 2 == 1) {
                view.historyRowBackground.setBackgroundColor(ContextCompat.getColor(context, R.color.tableRowBackgroundOdd))
            } else view.historyRowBackground.setBackgroundColor(ContextCompat.getColor(context, R.color.tableRowBackgroundEven))

            when {
                logList[position].isPositiveOutcome == null -> {
                    view.log.setTypeface(null, Typeface.BOLD)
                    view.log.setTextColor(ContextCompat.getColor(context, R.color.white))
//                    holder.setIsRecyclable(false)
                }
                logList[position].isPositiveOutcome!! -> {
                    view.log.setTextColor(ContextCompat.getColor(context, R.color.success))
                }
                else -> {
                    view.log.setTextColor(ContextCompat.getColor(context, R.color.failure))
                }
            }
        }
    }
}