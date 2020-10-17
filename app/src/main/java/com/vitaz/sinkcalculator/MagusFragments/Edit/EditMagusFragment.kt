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

        view.confirmEditing.setOnClickListener {

            // update and calculate sink
            mMagusViewModel.previousSink = mMagusViewModel.currentSink
            mMagusViewModel.currentSink = calculateSink(mMagusViewModel.listOfSinkModifiers, mMagusViewModel.previousSink)

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

        preferenceManager = this.requireActivity().getSharedPreferences("tutorial", Context.MODE_PRIVATE)

        // Run rune intro if we are on the 5th step of tutorial
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
                                    .title("Here you can modify your stat values according to magus outcome")
                                    .focusOn(characteristicEditListRecyclerView)
                                    .titleStyle(R.style.MyTitleStyle, Gravity.CENTER or Gravity.TOP)
                                    .focusShape(FocusShape.ROUNDED_RECTANGLE)
                                    .roundRectRadius(90)
                                    .enableAutoTextPosition()
                                    .build()

                            var target: View

                            target = viewItem?.itemView?.findViewById<View>(R.id.positiveSinkLayout)
                            val fancyShowCaseView2 =
                                FancyShowCaseView.Builder(requireActivity())
                                    .title("Put you outcome results here" +
                                            "\nIf you modify the stat which is positive on item, use this field." +
                                            "\nValue would be positive if you gain stat on item and negative otherwise." +
                                            "\nThis field would be most commonly used.")
                                    .focusOn(target)
                                    .titleStyle(R.style.MyTitleStyle, Gravity.CENTER)
                                    .focusShape(FocusShape.ROUNDED_RECTANGLE)
                                    .roundRectRadius(90)
                                    .enableAutoTextPosition()
                                    .build()

                            target = viewItem?.itemView?.findViewById<View>(R.id.negativeSinkLayout)
                            val fancyShowCaseView3 =
                                FancyShowCaseView.Builder(requireActivity())
                                    .title("Use this field ONLY if you modify the stat which is negative on the item." +
                                            "\nNegative sink would be considered in calculations.")
                                    .focusOn(target)
                                    .titleStyle(R.style.MyTitleStyle, Gravity.CENTER)
                                    .focusShape(FocusShape.ROUNDED_RECTANGLE)
                                    .roundRectRadius(90)
                                    .enableAutoTextPosition()
                                    .build()

                            target = viewItem?.itemView?.findViewById<View>(R.id.negativeLayoutSwitch)
                            val fancyShowCaseView4 =
                                FancyShowCaseView.Builder(requireActivity())
                                    .title("Since negative sink would not be used very often, Negative sink block is hidden by default." +
                                            "\nYou can show it up using this Advance Switch")
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

    private fun calculateSink(sinkModifierList: List<SinkModifier>, currentSink: Double): Double {
        var newSink = currentSink
        var sinkDifference = 0.0
        sinkModifierList.forEach() {
            sinkDifference += (it.sinkPositiveValue * it.statPositive) + (it.sinkNegativeValue * it.statNegative)
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
        if (historyMessage != "") {
            val sinkDifference = round((currentSink - previousSink)*100)/100
            if (sinkDifference > 0) historyMessage += ", +$sinkDifference sink"
            else historyMessage += ", $sinkDifference sink"
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

}