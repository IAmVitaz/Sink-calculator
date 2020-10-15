package com.vitaz.sinkcalculator.MagusFragments.Main

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.vitaz.sinkcalculator.MagusActivity
import com.vitaz.sinkcalculator.MainActivity
import com.vitaz.sinkcalculator.R
import com.vitaz.sinkcalculator.ViewModel.MagusViewModel
import kotlinx.android.synthetic.main.fragment_main_magus.*
import kotlinx.android.synthetic.main.fragment_main_magus.view.*
import me.toptas.fancyshowcase.FancyShowCaseQueue
import me.toptas.fancyshowcase.FancyShowCaseView
import me.toptas.fancyshowcase.FocusShape

class MainMagusFragment : Fragment() {

    lateinit var mMagusViewModel: MagusViewModel

    lateinit var preferenceManager: SharedPreferences

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


        val itemName = (activity as MagusActivity).itemName
        this.itemName.text = itemName

        val itemCategory = (activity as MagusActivity).itemCategory
        val itemCategoryResourceId = this.resources.getIdentifier(itemCategory, "drawable", requireContext().packageName)
        this.itemCategory.setImageResource(itemCategoryResourceId)

        preferenceManager = this.requireActivity().getSharedPreferences("tutorial", Context.MODE_PRIVATE)

        // Run Magus overview intro if we are on the 2st step of tutorial
        if (preferenceManager.getInt("tutorialCurrentStep", 0) == 2) {

            val fancyShowCaseView1 =
                FancyShowCaseView.Builder(requireActivity())
                    .title("Here is the overview panel")
                    .focusOn(overviewPanel)
                    .titleStyle(R.style.MyTitleStyle, Gravity.CENTER)
                    .focusShape(FocusShape.ROUNDED_RECTANGLE)
                    .roundRectRadius(90)
                    .enableAutoTextPosition()
                    .build()

            val fancyShowCaseView2 =
                FancyShowCaseView.Builder(requireActivity())
                    .title("Current sink. " +
                            "\nIt is the main parameter in the whole app" +
                            "\nSink value would be different in color depending on changes during the maging")
                    .focusOn(sinkLayourMain)
                    .titleStyle(R.style.MyTitleStyle, Gravity.CENTER)
                    .enableAutoTextPosition()
                    .build()

            val fancyShowCaseView3 =
                FancyShowCaseView.Builder(requireActivity())
                    .title("Available runes would be listed here")
                    .focusOn(runeListRecyclerView)
                    .titleStyle(R.style.MyTitleStyle, Gravity.CENTER_HORIZONTAL)
                    .focusShape(FocusShape.ROUNDED_RECTANGLE)
                    .roundRectRadius(90)
                    .enableAutoTextPosition()
                    .build()

            val fancyShowCaseView4 =
                FancyShowCaseView.Builder(requireActivity())
                    .title("However no characteristics have been chosen yet." +
                            "\nLet's pick something!" +
                            "\nPress 'Edit Rune List' button")
                    .focusOn(editRuneList)
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

            //Move to the third step
            preferenceManager.edit().putInt("tutorialCurrentStep", 3).apply()
        }

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
                view.curentSinkValue.setTextColor(Color.parseColor("#D45E57"))
                view.sinkDynamic.visibility = View.VISIBLE
                view.sinkDynamic.setImageResource(R.drawable.arrow_downward_icon)
                view.sinkDynamic.drawable.setTint(Color.parseColor("#D45E57"))
            }
            else -> {
                view.sinkDynamic.visibility = View.GONE
                view.curentSinkValue.setTextColor(Color.parseColor("#3A3939"))
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