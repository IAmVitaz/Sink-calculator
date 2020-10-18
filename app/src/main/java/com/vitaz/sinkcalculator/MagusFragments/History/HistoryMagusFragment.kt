package com.vitaz.sinkcalculator.MagusFragments.History

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.vitaz.sinkcalculator.MagusFragments.Edit.EditMagusAdapter
import com.vitaz.sinkcalculator.R
import com.vitaz.sinkcalculator.ViewModel.MagusViewModel
import kotlinx.android.synthetic.main.fragment_edit_magus.view.*
import kotlinx.android.synthetic.main.fragment_history_magus.*
import kotlinx.android.synthetic.main.fragment_history_magus.view.*
import kotlinx.android.synthetic.main.fragment_main_magus.*
import me.toptas.fancyshowcase.FancyShowCaseQueue
import me.toptas.fancyshowcase.FancyShowCaseView
import me.toptas.fancyshowcase.FocusShape

class HistoryMagusFragment : Fragment() {

    lateinit var mMagusViewModel: MagusViewModel

    lateinit var preferenceManager: SharedPreferences

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

        preferenceManager = this.requireActivity().getSharedPreferences("tutorial", Context.MODE_PRIVATE)

        // Run history intro if we are on the 8th step of tutorial
        runEighthStepOfTutorial()

    }

    private fun runEighthStepOfTutorial() {
        if (preferenceManager.getInt("tutorialCurrentStep", 0) == 8) {

            //wait till recycler view finished creation. otherwise empty viewItem and Null Pointer Exception
            historyRecyclerView.viewTreeObserver.addOnGlobalLayoutListener(
                object : ViewTreeObserver.OnGlobalLayoutListener {
                    override fun onGlobalLayout() {

                        val fancyShowCaseView1 =
                            FancyShowCaseView.Builder(requireActivity())
                                .title(getString(R.string.tutorial_8_1))
                                .focusOn(historyRecyclerView)
                                .titleStyle(R.style.MyTitleStyle, Gravity.CENTER)
                                .focusShape(FocusShape.ROUNDED_RECTANGLE)
                                .roundRectRadius(90)
                                .enableAutoTextPosition()
                                .build()

                        val viewItem = historyRecyclerView.findViewHolderForAdapterPosition(mMagusViewModel.historyLogList.size - 1)

                        if (viewItem != null) {

                            var target: View = viewItem?.itemView?.findViewById<View>(R.id.historyRowBackground)
                            val fancyShowCaseView2 =
                                FancyShowCaseView.Builder(requireActivity())
                                    .title(getString(R.string.tutorial_8_2))
                                    .focusOn(target)
                                    .titleStyle(R.style.MyTitleStyle, Gravity.CENTER)
                                    .focusShape(FocusShape.ROUNDED_RECTANGLE)
                                    .roundRectRadius(90)
                                    .enableAutoTextPosition()
                                    .build()

                            target = viewItem?.itemView?.findViewById<View>(R.id.log)
                            val fancyShowCaseView3 =
                                FancyShowCaseView.Builder(requireActivity())
                                    .title(getString(R.string.tutorial_8_3))
                                    .focusOn(target)
                                    .titleStyle(R.style.MyTitleStyle, Gravity.CENTER)
                                    .focusShape(FocusShape.ROUNDED_RECTANGLE)
                                    .roundRectRadius(90)
                                    .enableAutoTextPosition()
                                    .build()

                            target = viewItem?.itemView?.findViewById<View>(R.id.sink)
                            val fancyShowCaseView4 =
                                FancyShowCaseView.Builder(requireActivity())
                                    .title(getString(R.string.tutorial_8_4))
                                    .focusOn(target)
                                    .titleStyle(R.style.MyTitleStyle, Gravity.CENTER)
                                    .focusShape(FocusShape.ROUNDED_RECTANGLE)
                                    .roundRectRadius(90)
                                    .enableAutoTextPosition()
                                    .build()

                            val fancyShowCaseView5 =
                                FancyShowCaseView.Builder(requireActivity())
                                    .title(getString(R.string.tutorial_8_5))
                                    .titleStyle(R.style.MyTitleStyle, Gravity.CENTER or Gravity.TOP)
                                    .enableAutoTextPosition()
                                    .build()

                            val fancyShowCaseView6 =
                                FancyShowCaseView.Builder(requireActivity())
                                    .title(getString(R.string.tutorial_8_6))
                                    .focusOn(backToMain)
                                    .titleStyle(R.style.MyTitleStyle, Gravity.CENTER)
                                    .focusShape(FocusShape.ROUNDED_RECTANGLE)
                                    .roundRectRadius(180)
                                    .enableAutoTextPosition()
                                    .build()

                            val mQueue = FancyShowCaseQueue()
                                .add(fancyShowCaseView1)
                                .add(fancyShowCaseView2)
                                .add(fancyShowCaseView3)
                                .add(fancyShowCaseView4)
                                .add(fancyShowCaseView5)
                                .add(fancyShowCaseView6)
                            mQueue.show()

                            //Move to the 9th step
                            preferenceManager.edit().putInt("tutorialCurrentStep", 9).apply()
                        }

                        // At this point the layout is complete
                        historyRecyclerView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    }
                }
            )
        }
    }
}