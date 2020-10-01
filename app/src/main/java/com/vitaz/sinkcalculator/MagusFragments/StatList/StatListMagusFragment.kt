package com.vitaz.sinkcalculator.MagusFragments.StatList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.vitaz.sinkcalculator.R
import com.vitaz.sinkcalculator.Services.RunesService
import com.vitaz.sinkcalculator.ViewModel.MagusViewModel
import kotlinx.android.synthetic.main.fragment_stat_list_magus.view.*

class StatListMagusFragment : Fragment() {

    private val mMagusViewModel: MagusViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_stat_list_magus, container, false)

        //Show stats in recycler view
        val adapter = parentFragment?.context?.let { StatListAdapter(it, mMagusViewModel.activeListOfStats) }
        val recyclerView = view.statListRecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        return view
    }

}