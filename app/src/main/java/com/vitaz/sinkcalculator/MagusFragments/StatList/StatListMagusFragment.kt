package com.vitaz.sinkcalculator.MagusFragments.StatList

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.vitaz.sinkcalculator.R
import com.vitaz.sinkcalculator.Services.RunesService
import com.vitaz.sinkcalculator.ViewModel.MagusViewModel
import com.vitaz.sinkcalculator.databinding.FragmentStatListMagusBinding

class StatListMagusFragment : Fragment() {

    private var _binding: FragmentStatListMagusBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    lateinit var mMagusViewModel: MagusViewModel

    lateinit var preferenceManager: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentStatListMagusBinding.inflate(inflater, container, false)
        val view = binding.root

        mMagusViewModel = ViewModelProvider(requireActivity()).get(MagusViewModel::class.java)

        binding.moveToMain.setOnClickListener {

            mMagusViewModel.activeListOfRunes.clear()
            mMagusViewModel.activeListOfRunes = RunesService.createNewRuneSet(mMagusViewModel.activeListOfStats)

            if (mMagusViewModel.activeListOfRunes.size == 0) {
                Toast.makeText(requireContext(), "Please pick at least one stat to proceed", Toast.LENGTH_LONG).show()
            } else {
                findNavController().navigate(R.id.action_runeListMagusFragment_to_mainMagusFragment)
            }
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Show stats in recycler view
        val adapter = parentFragment?.context?.let { StatListAdapter(it, mMagusViewModel.activeListOfStats) }
        val recyclerView = binding.statListRecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        preferenceManager = this.requireActivity().getSharedPreferences("tutorial", Context.MODE_PRIVATE)

        // Run stat list intro if we are on the 3st step of tutorial
        runThirdStepOfTutorial()
    }

    private fun runThirdStepOfTutorial() {
        if (preferenceManager.getInt("tutorialCurrentStep", 0) == 3) {

            //wait till recycler view finished creation. otherwise empty viewItem and Null Pointer Exception
            binding.statListRecyclerView.viewTreeObserver.addOnGlobalLayoutListener(
                object : OnGlobalLayoutListener {
                    override fun onGlobalLayout() {

                        val viewItem = binding.statListRecyclerView.findViewHolderForAdapterPosition(2)

                        if (viewItem != null) {

//                            val fancyShowCaseView1 =
//                                FancyShowCaseView.Builder(requireActivity())
//                                    .title(getString(R.string.tutorial_3_1))
//                                    .focusOn(binding.statListRecyclerView)
//                                    .titleStyle(R.style.MyTitleStyle, Gravity.CENTER)
//                                    .focusShape(FocusShape.ROUNDED_RECTANGLE)
//                                    .roundRectRadius(90)
//                                    .enableAutoTextPosition()
//                                    .build()
//
//                            var target = viewItem?.itemView?.findViewById<View>(R.id.statRowBackground)
//                            val fancyShowCaseView2 =
//                                FancyShowCaseView.Builder(requireActivity())
//                                    .title(getString(R.string.tutorial_3_2))
//                                    .focusOn(target)
//                                    .titleStyle(R.style.MyTitleStyle, Gravity.CENTER)
//                                    .focusShape(FocusShape.ROUNDED_RECTANGLE)
//                                    .roundRectRadius(90)
//                                    .enableAutoTextPosition()
//                                    .build()
//
//                            target = viewItem?.itemView?.findViewById<View>(R.id.actionButton)
//                            val fancyShowCaseView3 =
//                                FancyShowCaseView.Builder(requireActivity())
//                                    .title(getString(R.string.tutorial_3_3))
//                                    .focusOn(target)
//                                    .titleStyle(R.style.MyTitleStyle, Gravity.CENTER)
//                                    .focusShape(FocusShape.ROUNDED_RECTANGLE)
//                                    .roundRectRadius(140)
//                                    .focusCircleRadiusFactor(2.0)
//                                    .enableAutoTextPosition()
//                                    .build()
//
//                            val fancyShowCaseView4 =
//                                FancyShowCaseView.Builder(requireActivity())
//                                    .title(getString(R.string.tutorial_3_4))
//                                    .focusOn(binding.moveToMain)
//                                    .titleStyle(R.style.MyTitleStyle, Gravity.CENTER)
//                                    .focusShape(FocusShape.ROUNDED_RECTANGLE)
//                                    .roundRectRadius(50)
//                                    .focusAnimationMaxValue(40) // Default max value is 20
//                                    .enableAutoTextPosition()
//                                    .build()
//
//                            val mQueue = FancyShowCaseQueue()
//                                .add(fancyShowCaseView1)
//                                .add(fancyShowCaseView2)
//                                .add(fancyShowCaseView3)
//                                .add(fancyShowCaseView4)
//                            mQueue.show()
//
//                            //Move to the 4th step
//                            preferenceManager.edit().putInt("tutorialCurrentStep", 4).apply()
                        }

                        // At this point the layout is complete
                        binding.statListRecyclerView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    }
                }
            )
        }
    }

}