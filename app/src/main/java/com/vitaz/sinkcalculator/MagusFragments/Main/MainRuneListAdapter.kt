package com.vitaz.sinkcalculator.MagusFragments.Main

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.vitaz.sinkcalculator.Model.HistoryLog
import com.vitaz.sinkcalculator.Model.Rune
import com.vitaz.sinkcalculator.R
import com.vitaz.sinkcalculator.ViewModel.MagusViewModel
import kotlinx.android.synthetic.main.fragment_main_magus_list_item.view.*
import java.util.*

class MainRuneListAdapter(
    val context: Context,
    val runeList: List<Rune>,
    val mMagusViewModel: MagusViewModel,
    val fragment: MainMagusFragment
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
        holder.itemView.runeBonus.text = "+$statValue"
        holder.itemView.baseSink.text = "$statSink"
        holder.itemView.runeSink.text = "$runeSink"

        holder.itemView.rune_row_background.setOnClickListener {
            val action = MainMagusFragmentDirections.actionMainMagusFragmentToEditMagusFragment(runeList[position])
            holder.itemView.findNavController().navigate(action)
        }

        holder.itemView.rune_row_background.setOnLongClickListener(OnLongClickListener {
            showDialog(runeList[position].runeName, runeList[position].sinkValue, runeList[position].minusSinkValue, runeList[position].bonus)
            false
        })

        //change row color
        if (position % 2 == 1) {
            holder.itemView.rune_row_background.setBackgroundColor(Color.parseColor("#BB2A2E27"))
        } else holder.itemView.rune_row_background.setBackgroundColor(Color.parseColor("#BB21251C"))

    }

    private fun showDialog(runeName: String, sinkValue: Double, minusSinkValue: Double, bonus: Int) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_message_critical_failure)
        val runeMessage = dialog.findViewById(R.id.message1) as TextView
        runeMessage.text = "You are about to confirm that you hit critical failure when use $runeName rune."
        val sinkMessage = dialog.findViewById(R.id.message2) as TextView
        val yesBtn = dialog.findViewById(R.id.yesButton) as Button
        val noBtn = dialog.findViewById(R.id.noButton) as Button
        val switch = dialog.findViewById(R.id.positiveSwitch) as Switch
        val positivePower = sinkValue * bonus
        val negativePower = minusSinkValue * bonus
        var sink = positivePower
        sinkMessage.text = "Your sink would be decreased by $sink sink."
        switch.setOnCheckedChangeListener { _, switchIsChecked ->
            if (switchIsChecked) {
                sink = negativePower
                sinkMessage.text = "Your sink would be decreased by $sink sink."
            } else {
                sink = positivePower
                sinkMessage.text = "Your sink would be decreased by $sink sink."
            }
        }
        yesBtn.setOnClickListener {
            // update and calculate sink
            mMagusViewModel.previousSink = mMagusViewModel.currentSink
            mMagusViewModel.currentSink -= sink

            //update outcomeStatus
            mMagusViewModel.magusOutcome = false

            // update history log list
            val message = "-$sink sink"
            mMagusViewModel.historyLogList.add(0, HistoryLog(Date(), message, mMagusViewModel.currentSink, mMagusViewModel.magusOutcome))
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()

            // update current sink value in parent fragment
            fragment.modifySinkValueOnTheMain(fragment.requireView(), mMagusViewModel)

            dialog.hide()
        }
        noBtn.setOnClickListener {
            dialog.hide()
        }
        dialog.show()

    }
}