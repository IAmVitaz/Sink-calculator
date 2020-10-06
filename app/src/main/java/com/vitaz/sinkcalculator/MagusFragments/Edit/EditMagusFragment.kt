package com.vitaz.sinkcalculator.MagusFragments.Edit

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.vitaz.sinkcalculator.Model.SinkModifier
import com.vitaz.sinkcalculator.R
import com.vitaz.sinkcalculator.Services.RunesService
import com.vitaz.sinkcalculator.ViewModel.MagusViewModel
import kotlinx.android.synthetic.main.fragment_edit_magus.view.*
import kotlin.math.round

class EditMagusFragment : Fragment() {

    lateinit var mMagusViewModel: MagusViewModel

    private val args by navArgs<EditMagusFragmentArgs>()

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
            mMagusViewModel.currentSink = calculateSink(mMagusViewModel.listOfSinkModifiers, mMagusViewModel.previousSink)

            val message = generageHistoryMessage(mMagusViewModel.listOfSinkModifiers, mMagusViewModel.currentSink, mMagusViewModel.previousSink)
            Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()

            findNavController().navigate(R.id.action_editMagusFragment_to_mainMagusFragment)
        }

        // Extract passed argument about rune selected:
        view.runeNameHeader.text = args.selectedRune.runeName
        val resourceId = requireContext().resources.getIdentifier(args.selectedRune.image, "drawable", requireContext().packageName)
        view.runeImageHeader.setImageResource(resourceId)



        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = parentFragment?.context?.let { EditMagusAdapter(it, mMagusViewModel.listOfSinkModifiers, args.selectedRune) }
        val recyclerView = view.characteristicEditListRecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
    }

    fun calculateSink(sinkModifierList: List<SinkModifier>, currentSink: Double): Double {
        var newSink = currentSink
        var sinkDifference = 0.0
        sinkModifierList.forEach() {
            sinkDifference += (it.sinkPositiveValue * it.statPositive) + (it.sinkNegativeValue * it.statNegative)
        }
        Log.d("Sink difference", sinkDifference.toString())
        newSink -= sinkDifference
        return round(newSink*100) /100
    }

    fun generageHistoryMessage (sinkModifierList: List<SinkModifier>, currentSink: Double, previousSink: Double): String {
        var historyMessage = ""
        sinkModifierList.forEach() {
            if (it.statPositive != 0) {
                if (historyMessage == "") {
                    if (it.statPositive > 0) historyMessage += "+${it.statPositive.toString()} ${it.statName}"
                    else historyMessage += "${it.statPositive.toString()} ${it.statName}"
                }
                else {
                    if (it.statPositive > 0) historyMessage += ", +${it.statPositive.toString()} ${it.statName}"
                    else historyMessage += ", ${it.statPositive.toString()} ${it.statName}"
                }
            }
            if (it.statNegative != 0) {
                if (historyMessage == "") {
                    if (it.statNegative > 0) historyMessage += "+${it.statNegative.toString()} ${it.statName}"
                    else historyMessage += "${it.statNegative.toString()} ${it.statName}"
                }
                else {
                    if (it.statNegative > 0) historyMessage += ", +${it.statNegative.toString()} ${it.statName}"
                    else historyMessage += ", ${it.statNegative.toString()} ${it.statName}"
                }
            }
        }
        if (historyMessage != "") {
            val sinkDifference = round((currentSink - previousSink)*100)/100
            if (sinkDifference > 0) historyMessage += ", +${currentSink - previousSink} sink"
            else historyMessage += ", ${currentSink - previousSink} sink"
        }
        return historyMessage
    }
}