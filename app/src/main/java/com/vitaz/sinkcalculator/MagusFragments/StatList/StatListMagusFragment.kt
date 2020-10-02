package com.vitaz.sinkcalculator.MagusFragments.StatList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.vitaz.sinkcalculator.R
import com.vitaz.sinkcalculator.Services.RunesService
import com.vitaz.sinkcalculator.ViewModel.MagusViewModel
import kotlinx.android.synthetic.main.fragment_stat_list_magus.view.*

class StatListMagusFragment : Fragment() {

    lateinit var mMagusViewModel: MagusViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_stat_list_magus, container, false)

        mMagusViewModel = ViewModelProvider(requireActivity()).get(MagusViewModel::class.java)

        view.moveToMain.setOnClickListener {

            mMagusViewModel.activeListOfRunes.clear()
            mMagusViewModel.activeListOfRunes = RunesService.createNewRuneSet(mMagusViewModel.activeListOfStats)

            findNavController().navigate(R.id.action_runeListMagusFragment_to_mainMagusFragment)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Show stats in recycler view
        val adapter = parentFragment?.context?.let { StatListAdapter(it, mMagusViewModel.activeListOfStats) }
        val recyclerView = view.statListRecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
    }
}