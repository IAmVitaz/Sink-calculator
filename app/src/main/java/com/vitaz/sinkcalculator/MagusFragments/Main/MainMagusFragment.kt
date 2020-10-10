package com.vitaz.sinkcalculator.MagusFragments.Main

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.vitaz.sinkcalculator.MainActivity
import com.vitaz.sinkcalculator.Model.HistoryLog
import com.vitaz.sinkcalculator.R
import com.vitaz.sinkcalculator.ViewModel.MagusViewModel
import kotlinx.android.synthetic.main.fragment_main_magus.view.*
import java.util.*

class MainMagusFragment : Fragment() {

    lateinit var mMagusViewModel: MagusViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //set menu
        setHasOptionsMenu(true)

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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_magus_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_exit_magus_activity) {
            val dialog = Dialog(requireContext())
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(true)
            dialog.setContentView(R.layout.dialog_message_exit_to_title)

            val yesBtn = dialog.findViewById(R.id.exitYesButton) as Button
            val noBtn = dialog.findViewById(R.id.exitNoButton) as Button

            yesBtn.setOnClickListener {
                returnToTitle()
                dialog.hide()
            }
            noBtn.setOnClickListener {
                dialog.hide()
            }
            dialog.show()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun returnToTitle () {
        val returnToTitle = Intent(activity, MainActivity::class.java)
        startActivity(returnToTitle)
        requireActivity().finish();
    }
}