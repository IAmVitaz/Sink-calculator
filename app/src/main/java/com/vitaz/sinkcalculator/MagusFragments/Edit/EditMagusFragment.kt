package com.vitaz.sinkcalculator.MagusFragments.Edit

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.vitaz.sinkcalculator.MagusFragments.StatList.StatListAdapter
import com.vitaz.sinkcalculator.R
import com.vitaz.sinkcalculator.Services.RunesService
import com.vitaz.sinkcalculator.ViewModel.MagusViewModel
import kotlinx.android.synthetic.main.fragment_edit_magus.view.*
import kotlinx.android.synthetic.main.fragment_stat_list_magus.view.*

class EditMagusFragment : Fragment() {

    lateinit var mMagusViewModel: MagusViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_edit_magus, container, false)

        mMagusViewModel = ViewModelProvider(requireActivity()).get(MagusViewModel::class.java)

        view.currentSink.text = mMagusViewModel.currentSink.toString()

        // Create a new set of SinkModifier objects to count sink change
        mMagusViewModel.listOfSinkModifiers.clear()
        mMagusViewModel.listOfSinkModifiers = RunesService.createNewListOfSinkModifiers(mMagusViewModel.activeListOfStats)

        view.confirmEditing.setOnClickListener {
            mMagusViewModel.previousSink = mMagusViewModel.currentSink
            mMagusViewModel.currentSink = RunesService.calculateSink(mMagusViewModel.listOfSinkModifiers, mMagusViewModel.previousSink)
            findNavController().navigate(R.id.action_editMagusFragment_to_mainMagusFragment)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = parentFragment?.context?.let { EditMagusAdapter(it, mMagusViewModel.listOfSinkModifiers) }
        val recyclerView = view.characteristicEditListRecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
    }
}