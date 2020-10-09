package com.vitaz.sinkcalculator.MagusFragments.Main

import android.graphics.Color
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

        view.editRuneList.setOnClickListener {
            findNavController().navigate(R.id.action_mainMagusFragment_to_runeListMagusFragment)
        }

        view.moveToHistory.setOnClickListener {
            findNavController().navigate(R.id.action_mainMagusFragment_to_historyMagusFragment)
        }

        //view.curentSinkValue.text = mMagusViewModel.currentSink.toString()
        modifySinkValueOnTheMain(view, mMagusViewModel)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Inflate recyclerView with data
        val adapter = parentFragment?.context?.let { MainRuneListAdapter(it, mMagusViewModel.activeListOfRunes, mMagusViewModel, this) }
        val recyclerView = view.runeListRecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
    }

    fun modifySinkValueOnTheMain(view: View, viewModel: MagusViewModel) {

        when {
            viewModel.currentSink > viewModel.previousSink -> {
                view.curentSinkValue.setTextColor(Color.parseColor("#008000"))
                view.sinkDynamic.visibility = View.VISIBLE
                view.sinkDynamic.setImageResource(R.drawable.arrow_upward_icon)
                view.sinkDynamic.drawable.setTint(Color.parseColor("#008000"))
            }
            viewModel.currentSink < viewModel.previousSink -> {
                view.curentSinkValue.setTextColor(Color.parseColor("#FF0000"))
                view.sinkDynamic.visibility = View.VISIBLE
                view.sinkDynamic.setImageResource(R.drawable.arrow_downward_icon)
                view.sinkDynamic.drawable.setTint(Color.parseColor("#FF0000"))
            }
            else -> {
                view.sinkDynamic.visibility = View.GONE
                view.curentSinkValue.setTextColor(Color.parseColor("#000000"))
            }
        }
        view.curentSinkValue.text = mMagusViewModel.currentSink.toString()

    }

}