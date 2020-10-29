package com.vitaz.sinkcalculator.MagusFragments.Edit

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.vitaz.sinkcalculator.Model.HistoryLog
import com.vitaz.sinkcalculator.Model.Rune
import com.vitaz.sinkcalculator.Model.SinkModifier
import com.vitaz.sinkcalculator.R
import com.vitaz.sinkcalculator.Services.RunesService
import com.vitaz.sinkcalculator.ViewModel.MagusViewModel
import kotlinx.android.synthetic.main.fragment_edit_magus.*
import kotlinx.android.synthetic.main.fragment_edit_magus.view.*
import kotlinx.android.synthetic.main.fragment_edit_magus_list_item.view.*
import kotlinx.android.synthetic.main.fragment_main_magus.*
import me.toptas.fancyshowcase.FancyShowCaseQueue
import me.toptas.fancyshowcase.FancyShowCaseView
import me.toptas.fancyshowcase.FocusShape
import java.util.*
import kotlin.math.round

class EditMagusFragment : Fragment() {

    lateinit var mMagusViewModel: MagusViewModel

    lateinit var preferenceManager: SharedPreferences

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

        // Extract passed argument about rune selected:
        val selectedRuneName = args.selectedRune.runeName
        val selectedRuneImage = args.selectedRune.image

        view.runeNameHeader.text = selectedRuneName
        val resourceId = requireContext().resources.getIdentifier(selectedRuneImage, "drawable", requireContext().packageName)
        view.runeImageHeader.setImageResource(resourceId)

        //Actions on confirm button click:
        view.confirmEditing.setOnClickListener {

            // update and calculate sink
            mMagusViewModel.previousSink = mMagusViewModel.currentSink
            mMagusViewModel.currentSink = calculateSink(mMagusViewModel.listOfSinkModifiers, mMagusViewModel.previousSink, args.selectedRune)

            //update outcomeStatus
            mMagusViewModel.magusOutcome = modifyMagusOutcome(mMagusViewModel.listOfSinkModifiers)

            // update history log list
            val message = generateHistoryMessage(mMagusViewModel.listOfSinkModifiers, mMagusViewModel.currentSink, mMagusViewModel.previousSink)
            addHistoryLog(mMagusViewModel.historyLogList, message, mMagusViewModel.currentSink, mMagusViewModel.magusOutcome!!)
            Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()

            //hide annoying keyboard
            view.hideKeyboard()

            // move to main magus fragment
            findNavController().navigate(R.id.action_editMagusFragment_to_mainMagusFragment)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = parentFragment?.context?.let { EditMagusAdapter(it, mMagusViewModel.listOfSinkModifiers, args.selectedRune) }
        val recyclerView = view.characteristicEditListRecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        preferenceManager = this.requireActivity().getSharedPreferences("tutorial", Context.MODE_PRIVATE)

        // Run rune intro if we are on the 5th step of tutorial
        runFifthStepOfTutorial()

    }

    private fun calculateSink(sinkModifierList: List<SinkModifier>, currentSink: Double, selectedRune: Rune): Double {
        var newSink = currentSink
        var sinkDifference = 0.0
        sinkModifierList.forEach() {

            sinkDifference += when {
                (it.statName == selectedRune.statName) and (it.statPositive == 0) and (it.statNegative == 0) and !it.isNegative -> {
                    it.sinkPositiveValue * selectedRune.bonus
                }
                (it.statName == selectedRune.statName) and(it.statPositive == 0) and (it.statNegative == 0) and it.isNegative -> {
                    it.sinkNegativeValue * selectedRune.bonus
                }
                else -> {
                    (it.sinkPositiveValue * it.statPositive) + (it.sinkNegativeValue * it.statNegative)
                }
            }
        }
        Log.d("Sink difference", sinkDifference.toString())
        newSink -= sinkDifference
        return round(newSink*100) /100
    }

    private fun generateHistoryMessage (sinkModifierList: List<SinkModifier>, currentSink: Double, previousSink: Double): String {
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

        val sinkDifference = round((currentSink - previousSink)*100)/100
        when {
            (historyMessage == "") && (sinkDifference > 0) -> historyMessage += "+$sinkDifference sink"
            (historyMessage == "") && (sinkDifference <= 0) -> historyMessage += "$sinkDifference sink"
            sinkDifference > 0 -> historyMessage += ", +$sinkDifference sink"
            else -> historyMessage += ", $sinkDifference sink"
        }

        return historyMessage
    }

    private fun modifyMagusOutcome(sinkModifierList: List<SinkModifier>): Boolean {
        var isOutcomePositive: Boolean = false
        sinkModifierList.forEach() {
            if (it.statPositive > 0 || it.statNegative > 0) isOutcomePositive = true
        }
        return isOutcomePositive
    }

    private fun addHistoryLog (logList: MutableList<HistoryLog>, message: String, currentSink: Double, isPositiveOutcome: Boolean) {

        logList.add(0, HistoryLog(Date(), message, currentSink, isPositiveOutcome))
    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    private fun runFifthStepOfTutorial() {
        if (preferenceManager.getInt("tutorialCurrentStep", 0) == 5) {

            //wait till recycler view finished creation. otherwise empty viewItem and Null Pointer Exception
            characteristicEditListRecyclerView.viewTreeObserver.addOnGlobalLayoutListener(
                object : ViewTreeObserver.OnGlobalLayoutListener {
                    override fun onGlobalLayout() {

                        val viewItem = characteristicEditListRecyclerView.findViewHolderForAdapterPosition(0)

                        if (viewItem != null) {

                            viewItem?.itemView?.negativeLayoutSwitch?.isChecked = true

                            val fancyShowCaseView1 =
                                FancyShowCaseView.Builder(requireActivity())
                                    .title(getString(R.string.tutorial_5_1))
                                    .focusOn(characteristicEditListRecyclerView)
                                    .titleStyle(R.style.MyTitleStyle, Gravity.CENTER or Gravity.TOP)
                                    .focusShape(FocusShape.ROUNDED_RECTANGLE)
                                    .roundRectRadius(90)
                                    .enableAutoTextPosition()
                                    .build()

                            var target: View = viewItem?.itemView?.findViewById<View>(R.id.positiveSinkLayout)
                            val fancyShowCaseView2 =
                                FancyShowCaseView.Builder(requireActivity())
                                    .title(getString(R.string.tutorial_5_2))
                                    .focusOn(target)
                                    .titleStyle(R.style.MyTitleStyle, Gravity.CENTER)
                                    .focusShape(FocusShape.ROUNDED_RECTANGLE)
                                    .roundRectRadius(90)
                                    .enableAutoTextPosition()
                                    .build()

                            target = viewItem?.itemView?.findViewById<View>(R.id.negativeSinkLayout)
                            val fancyShowCaseView3 =
                                FancyShowCaseView.Builder(requireActivity())
                                    .title(getString(R.string.tutorial_5_3))
                                    .focusOn(target)
                                    .titleStyle(R.style.MyTitleStyle, Gravity.CENTER)
                                    .focusShape(FocusShape.ROUNDED_RECTANGLE)
                                    .roundRectRadius(90)
                                    .enableAutoTextPosition()
                                    .build()

                            target = viewItem?.itemView?.findViewById<View>(R.id.negativeLayoutSwitch)
                            val fancyShowCaseView4 =
                                FancyShowCaseView.Builder(requireActivity())
                                    .title(getString(R.string.tutorial_5_4))
                                    .focusOn(target)
                                    .titleStyle(R.style.MyTitleStyle, Gravity.CENTER)
                                    .focusShape(FocusShape.ROUNDED_RECTANGLE)
                                    .roundRectRadius(90)
                                    .enableAutoTextPosition()
                                    .build()

                            val mQueue = FancyShowCaseQueue()
                                .add(fancyShowCaseView1)
                                .add(fancyShowCaseView2)
                                .add(fancyShowCaseView3)
                                .add(fancyShowCaseView4)
                            mQueue.show()

                            //Move to the 5th step
                            preferenceManager.edit().putInt("tutorialCurrentStep", 6).apply()
                        }

                        // At this point the layout is complete
                        characteristicEditListRecyclerView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    }
                }
            )
        }
    }

}