package com.vitaz.sinkcalculator.MagusFragments.Main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.vitaz.sinkcalculator.R
import kotlinx.android.synthetic.main.fragment_main_magus.view.*

class MainMagusFragment : Fragment() {

    //private val adapter: MainRuneListAdapter by lazy {MainRuneListAdapter()}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_main_magus, container, false)



        val adapter = MainRuneListAdapter()
        val recyclerView = view.runeListRecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())


        return view
    }

}