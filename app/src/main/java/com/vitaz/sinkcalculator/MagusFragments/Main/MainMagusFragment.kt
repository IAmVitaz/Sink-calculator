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
import kotlinx.android.synthetic.main.fragment_stat_list_magus.*
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
                    .focusAnimationMaxValue(40)
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
                    .focusAnimationMaxValue(40)
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

        // Run rune intro if we are on the 4th step of tutorial
        if (preferenceManager.getInt("tutorialCurrentStep", 0) == 4) {

            //wait till recycler view finished creation. otherwise empty viewItem and Null Pointer Exception
            runeListRecyclerView.viewTreeObserver.addOnGlobalLayoutListener(
                object : ViewTreeObserver.OnGlobalLayoutListener {
                    override fun onGlobalLayout() {

                        val viewItem = runeListRecyclerView.findViewHolderForAdapterPosition(0)

                        if (viewItem != null) {

                            val fancyShowCaseView1 =
                                FancyShowCaseView.Builder(requireActivity())
                                    .title("All available runes corresponding to stat selected are shown in the list")
                                    .focusOn(runeListRecyclerView)
                                    .titleStyle(R.style.MyTitleStyle, Gravity.CENTER or Gravity.TOP)
                                    .focusShape(FocusShape.ROUNDED_RECTANGLE)
                                    .roundRectRadius(90)
                                    .enableAutoTextPosition()
                                    .build()

                            val target = viewItem?.itemView?.findViewById<View>(R.id.runeLayoutMain)
                            val fancyShowCaseView2 =
                                FancyShowCaseView.Builder(requireActivity())
                                    .title("This shows the rune name and number of stats that would be added to item on success")
                                    .focusOn(target)
                                    .titleStyle(R.style.MyTitleStyle, Gravity.CENTER)
                                    .focusShape(FocusShape.ROUNDED_RECTANGLE)
                                    .roundRectRadius(90)
                                    .enableAutoTextPosition()
                                    .build()

                            val target2 = viewItem?.itemView?.findViewById<View>(R.id.baseSinkLayoutMain)
                            val fancyShowCaseView3 =
                                FancyShowCaseView.Builder(requireActivity())
                                    .title(
                                        "This shows the rune base positive sink. " +
                                                "\nIt is specific to the stat"
                                    )
                                    .focusOn(target2)
                                    .titleStyle(R.style.MyTitleStyle, Gravity.CENTER)
                                    .focusShape(FocusShape.ROUNDED_RECTANGLE)
                                    .roundRectRadius(140)
                                    .enableAutoTextPosition()
                                    .build()

                            val target3 = viewItem?.itemView?.findViewById<View>(R.id.runeSinkLayoutMain)
                            val fancyShowCaseView4 =
                                FancyShowCaseView.Builder(requireActivity())
                                    .title(
                                        "This shows the rune total positive sink. " +
                                                "\nBasically it is the amount of sink being consumed to be able to apply this particular rune"
                                    )
                                    .focusOn(target3)
                                    .titleStyle(R.style.MyTitleStyle, Gravity.CENTER)
                                    .focusShape(FocusShape.ROUNDED_RECTANGLE)
                                    .roundRectRadius(140)
                                    .enableAutoTextPosition()
                                    .build()

                            val target4 = viewItem?.itemView?.findViewById<View>(R.id.rune_row_background)
                            val fancyShowCaseView5 =
                                FancyShowCaseView.Builder(requireActivity())
                                    .title("To apply the rune and calculate outcome press on the rune" +
                                            "\nThat is corresponding to regular magus action in the game when you have some stats added and removed" +
                                            "\nTry it now!")
                                    .focusOn(target4)
                                    .titleStyle(R.style.MyTitleStyle, Gravity.CENTER)
                                    .focusShape(FocusShape.ROUNDED_RECTANGLE)
                                    .roundRectRadius(50)
                                    .enableAutoTextPosition()
                                    .build()

                            val mQueue = FancyShowCaseQueue()
                                .add(fancyShowCaseView1)
                                .add(fancyShowCaseView2)
                                .add(fancyShowCaseView3)
                                .add(fancyShowCaseView4)
                                .add(fancyShowCaseView5)
                            mQueue.show()

                            //Move to the 5th step
                            preferenceManager.edit().putInt("tutorialCurrentStep", 5).apply()
                        }

                        // At this point the layout is complete
                        runeListRecyclerView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    }
                }
            )
        }

        // Run rune intro if we are on the 6th step of tutorial
        if (preferenceManager.getInt("tutorialCurrentStep", 0) == 6) {

            //wait till recycler view finished creation. otherwise empty viewItem and Null Pointer Exception
            runeListRecyclerView.viewTreeObserver.addOnGlobalLayoutListener(
                object : ViewTreeObserver.OnGlobalLayoutListener {
                    override fun onGlobalLayout() {

                        val viewItem = runeListRecyclerView.findViewHolderForAdapterPosition(0)

                        if (viewItem != null) {

                            val fancyShowCaseView1 =
                                FancyShowCaseView.Builder(requireActivity())
                                    .title("But what to do with CRITICAL FAILURES?" +
                                            "\nIt is a magus outcome when you have no stats modified, but lose sink equal to rune power.")
                                    .titleStyle(R.style.MyTitleStyle, Gravity.CENTER or Gravity.TOP)
                                    .enableAutoTextPosition()
                                    .build()

                            val target = viewItem?.itemView?.findViewById<View>(R.id.rune_row_background)
                            val fancyShowCaseView2 =
                                FancyShowCaseView.Builder(requireActivity())
                                    .title("To apply critical failure, just PRESS AND HOLD corresponding rune row." +
                                            "\nIn the dialog box you can choose which type of sink you age going to use (positive or negative)" +
                                            "\nConfirm your decision to apply action." +
                                            "\nTry it out now.")
                                    .focusOn(target)
                                    .titleStyle(R.style.MyTitleStyle, Gravity.CENTER)
                                    .focusShape(FocusShape.ROUNDED_RECTANGLE)
                                    .roundRectRadius(90)
                                    .enableAutoTextPosition()
                                    .build()

                            val mQueue = FancyShowCaseQueue()
                                .add(fancyShowCaseView1)
                                .add(fancyShowCaseView2)
                            mQueue.show()

                            //Move to the 5th step
                            preferenceManager.edit().putInt("tutorialCurrentStep", 7).apply()
                        }

                        // At this point the layout is complete
                        runeListRecyclerView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    }
                }
            )
        }

        // Run final intro if we are on the 9th step of tutorial
        if (preferenceManager.getInt("tutorialCurrentStep", 0) == 9) {

            val fancyShowCaseView1 =
                FancyShowCaseView.Builder(requireActivity())
                    .title("CONGRATULATIONS!" +
                            "\n\nHere is the end of tutorial." +
                            "\nYou have successfully learned the basics and ready to perform magus by yourself." +
                            "\n\nTutorial can be run again from title screen anytime." +
                            "\n\nFeel free to reach me out if you still have questions or thoughts regarding app improvements.")
                    .titleStyle(R.style.MyTitleStyle, Gravity.CENTER or Gravity.TOP)
                    .enableAutoTextPosition()
                    .build()
                    .show()

            //Move to the third step
            preferenceManager.edit().putInt("tutorialCurrentStep", 9).apply()
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

    fun runSeventhStepOfTutorial() {
        if (preferenceManager.getInt("tutorialCurrentStep", 0) == 7) {
            val fancyShowCaseView1 =
                FancyShowCaseView.Builder(requireActivity())
                    .title("Every single magus action performed in the app are being recorded as a logs." +
                            "\nYou can check it out on the History page." +
                            "\n\nPress History button to look at it right now.")
                    .focusOn(moveToHistory)
                    .titleStyle(R.style.MyTitleStyle, Gravity.CENTER)
                    .focusShape(FocusShape.ROUNDED_RECTANGLE)
                    .roundRectRadius(140)
                    .enableAutoTextPosition()
                    .build()
                    .show()

            //Move to the 8th step
            preferenceManager.edit().putInt("tutorialCurrentStep", 8).apply()
        }
    }
}