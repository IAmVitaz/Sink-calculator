package com.vitaz.sinkcalculator.MagusFragments.Main

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
import kotlinx.android.synthetic.main.fragment_main_magus.view.*

class MainMagusFragment : Fragment() {

    lateinit var mMagusViewModel: MagusViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_main_magus, container, false)

        mMagusViewModel = ViewModelProvider(requireActivity()).get(MagusViewModel::class.java)

        mMagusViewModel.activeListOfRunes.clear()
        mMagusViewModel.activeListOfRunes = RunesService.createNewRuneSet(mMagusViewModel.activeListOfStats)


        view.editRuneList.setOnClickListener {
            findNavController().navigate(R.id.action_mainMagusFragment_to_runeListMagusFragment)
        }

        view.moveToHistory.setOnClickListener {
            findNavController().navigate(R.id.action_mainMagusFragment_to_historyMagusFragment)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Inflate recyclerView with data
        val adapter = parentFragment?.context?.let { MainRuneListAdapter(it, mMagusViewModel.activeListOfRunes) }
        val recyclerView = view.runeListRecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
    }

}