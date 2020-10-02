package com.vitaz.sinkcalculator.MagusFragments.Edit

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vitaz.sinkcalculator.MagusFragments.Main.MainRuneListAdapter
import com.vitaz.sinkcalculator.Model.Rune
import com.vitaz.sinkcalculator.Model.Stat
import com.vitaz.sinkcalculator.R
import kotlinx.android.synthetic.main.fragment_edit_magus_list_item.view.*
import kotlinx.android.synthetic.main.fragment_main_magus_list_item.view.*

class EditMagusAdapter (
    val context: Context,
    val statList: List<Stat>
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

        holder.itemView.statName.text = statList[position].statName

        val resourceId = context.resources.getIdentifier(statList[position].statImage, "drawable", context.packageName)
        holder.itemView.statImage.setImageResource(resourceId)

    }
}