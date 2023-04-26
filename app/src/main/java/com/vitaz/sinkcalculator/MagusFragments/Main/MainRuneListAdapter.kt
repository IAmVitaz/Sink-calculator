package com.vitaz.sinkcalculator.MagusFragments.Main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.vitaz.sinkcalculator.Model.Rune
import com.vitaz.sinkcalculator.R
import com.vitaz.sinkcalculator.ViewModel.MagusViewModel
import com.vitaz.sinkcalculator.databinding.FragmentMainMagusListItemBinding

class MainRuneListAdapter(
    val context: Context,
    val listener: SelectRuneListener,
    val runeList: List<Rune>,
    val mMagusViewModel: MagusViewModel,
    val fragment: MainMagusFragment
): RecyclerView.Adapter<MainRuneListAdapter.MyViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = FragmentMainMagusListItemBinding.inflate(inflater, parent, false)
        return MyViewHolder(view)

    }

    override fun getItemCount(): Int {
        return runeList.size
    }

    override fun onBindViewHolder(holder: MainRuneListAdapter.MyViewHolder, position: Int) {
        val rune = runeList[position]
        holder.bindViewHolder(rune, position)
    }

    inner class MyViewHolder(
        private val view: FragmentMainMagusListItemBinding
    ) : RecyclerView.ViewHolder(view.root) {
        fun bindViewHolder(stat: Rune, position: Int) {
            view.runeName.text = runeList[position].runeName

            //set up rune images
            val resourceId = context.resources.getIdentifier(runeList[position].image, "drawable", context.packageName)
            view.runeImage.setImageResource(resourceId)

            //set up rune characteristics
            val statValue = runeList[position].bonus
            val statSink = runeList[position].sinkValue
            val runeSink = statValue * statSink
            view.runeBonus.text = "+$statValue"
            view.baseSink.text = "$statSink"
            view.runeSink.text = "$runeSink"

            view.runeRowBackground.setOnClickListener {
                listener.selectRune(runeList[position])
            }

            //change row color
            if (position % 2 == 1) {
                view.runeRowBackground.setBackgroundColor(ContextCompat.getColor(context, R.color.tableRowBackgroundOdd))
            } else view.runeRowBackground.setBackgroundColor(ContextCompat.getColor(context, R.color.tableRowBackgroundEven))
        }
    }

    interface SelectRuneListener {
        fun selectRune(rune: Rune)
    }

}