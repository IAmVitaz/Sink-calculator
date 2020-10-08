package com.vitaz.sinkcalculator.MagusFragments.History

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.vitaz.sinkcalculator.MagusFragments.Edit.EditMagusAdapter
import com.vitaz.sinkcalculator.R
import com.vitaz.sinkcalculator.ViewModel.MagusViewModel
import kotlinx.android.synthetic.main.fragment_edit_magus.view.*
import kotlinx.android.synthetic.main.fragment_history_magus.view.*

class HistoryMagusFragment : Fragment() {

    lateinit var mMagusViewModel: MagusViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_history_magus, container, false)

        mMagusViewModel = ViewModelProvider(requireActivity()).get(MagusViewModel::class.java)

        view.backToMain.setOnClickListener {
            // move to main magus fragment
            findNavController().navigate(R.id.action_historyMagusFragment_to_mainMagusFragment)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = parentFragment?.context?.let { HistoryMagusAdapter(it, mMagusViewModel.historyLogList) }
        val recyclerView = view.historyRecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())

    }
}