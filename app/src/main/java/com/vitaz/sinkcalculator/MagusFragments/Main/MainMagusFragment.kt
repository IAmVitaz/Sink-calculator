package com.vitaz.sinkcalculator.MagusFragments.Main

import android.animation.ValueAnimator
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.SystemClock
import android.util.TypedValue
import android.view.*
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vitaz.sinkcalculator.MagusActivity
import com.vitaz.sinkcalculator.MainActivity
import com.vitaz.sinkcalculator.Model.HistoryLog
import com.vitaz.sinkcalculator.Model.Rune
import com.vitaz.sinkcalculator.R
import com.vitaz.sinkcalculator.ViewModel.MagusViewModel
import com.vitaz.sinkcalculator.databinding.FragmentMainMagusBinding
import me.toptas.fancyshowcase.FancyShowCaseQueue
import me.toptas.fancyshowcase.FancyShowCaseView
import me.toptas.fancyshowcase.FocusShape
import java.util.*
import kotlin.math.round


class MainMagusFragment : Fragment(), MainRuneListAdapter.SelectRuneListener {

    private var _binding: FragmentMainMagusBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    lateinit var mMagusViewModel: MagusViewModel

    lateinit var preferenceManager: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //set menu
        setHasOptionsMenu(true)

        _binding = FragmentMainMagusBinding.inflate(inflater, container, false)
        val view = binding.root

        mMagusViewModel = ViewModelProvider(requireActivity()).get(MagusViewModel::class.java)

        binding.editRuneList.setOnClickListener {
            findNavController().navigate(R.id.action_mainMagusFragment_to_runeListMagusFragment)
        }

        binding.moveToHistory.setOnClickListener {
            findNavController().navigate(R.id.action_mainMagusFragment_to_historyMagusFragment)
        }

        //view.curentSinkValue.text = mMagusViewModel.currentSink.toString()
        modifySinkValueOnTheMain(view, mMagusViewModel)

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Inflate recyclerView with data
        val adapter = parentFragment?.context?.let { MainRuneListAdapter(it, this, mMagusViewModel.activeListOfRunes, mMagusViewModel, this) }
        val recyclerView = binding.runeListRecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())


        val itemName = (activity as MagusActivity).itemName
        binding.itemName.text = itemName

        val itemCategory = (activity as MagusActivity).itemCategory
        val itemCategoryResourceId = this.resources.getIdentifier(itemCategory, "drawable", requireContext().packageName)
        binding.itemCategory.setImageResource(itemCategoryResourceId)

        preferenceManager = this.requireActivity().getSharedPreferences("tutorial", Context.MODE_PRIVATE)

        // Run Magus overview intro if we are on the 2st step of tutorial
        runSecondStepOfTutorial()

        // Run rune intro if we are on the 4th step of tutorial
        runFourthStepOfTutorial()

        // Run swiping left intro if we are on the 6th step of tutorial
        runSixthStepOfTutorial()

        //7th step is being run from MainRuneListAdapter

        // Run final intro if we are on the 9th step of tutorial
        runTenthStepOfTutorial()

        // implement swipes:
        val myCallback = object: ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                if (direction == ItemTouchHelper.LEFT) {
                    swipeMagusAction(mMagusViewModel, viewHolder.adapterPosition, "success")
                    runSeventhStepOfTutorial()
                } else if (direction == ItemTouchHelper.RIGHT) {
                    swipeMagusAction(mMagusViewModel, viewHolder.adapterPosition, "fail")
                    runEightStepOfTutorial()
                }
                recyclerView.adapter?.notifyItemChanged(viewHolder.adapterPosition);
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    var icon = ContextCompat.getDrawable(context!!, R.drawable.failure_icon)
                    var iconLeft = 0
                    var iconRight = 0
                    val background: ColorDrawable
                    val itemView = viewHolder.itemView
                        val dip = 16f
                        val r: Resources = resources
                    val margin = TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP,
                            dip,
                            r.displayMetrics
                        ).toInt()
                    val iconWidth = icon!!.intrinsicWidth
                    val iconHeight = icon.intrinsicHeight
                    val cellHeight = itemView.bottom - itemView.top
                    val iconTop = itemView.top + (cellHeight - iconHeight) / 2
                    val iconBottom = iconTop + iconHeight
                    var actionHintText: String = ""
                    val actionHintTextView: TextView

                    // Right swipe.
                    if (dX > 0) {
                        icon = ContextCompat.getDrawable(context!!, R.drawable.failure_icon)
                        icon?.setTint(Color.BLACK)
                        background = ColorDrawable(ContextCompat.getColor(requireContext(), R.color.failure))
                        background.setBounds(itemView.left, itemView.top, (itemView.left + dX).toInt(), itemView.bottom)
                        iconLeft = margin
                        iconRight = margin + iconWidth
                    } /*Left swipe.*/ else if (dX < 0) {
                        icon = ContextCompat.getDrawable(context!!, R.drawable.success_icon)
                        icon?.setTint(Color.BLACK)
                        background = ColorDrawable(ContextCompat.getColor(requireContext(), R.color.success))
                        background.setBounds((itemView.right + dX).toInt(), itemView.top, (itemView.right - dX).toInt(), itemView.bottom)
                        iconLeft = itemView.right - margin - iconWidth
                        iconRight = itemView.right - margin
                    } else {
                        background = ColorDrawable(Color.TRANSPARENT)
                    }
                    background.draw(c)
                    icon?.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                    icon?.draw(c)
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                }
            }
        }

        val myHelper = ItemTouchHelper(myCallback)
        myHelper.attachToRecyclerView(recyclerView)
    }

    private fun swipeMagusAction(viewModel: MagusViewModel, position: Int, result: String) {

        val sink = round (viewModel.activeListOfRunes[position].sinkValue * viewModel.activeListOfRunes[position].bonus) * 100 / 100

        // update
        viewModel.previousSink = mMagusViewModel.currentSink

        // update history log list
        var message = ""
        when (result) {
            "fail" -> {
                viewModel.currentSink -= sink
                viewModel.magusOutcome = false
                message = "-$sink sink"
            }
            "success" -> {
                viewModel.currentSink -= sink
                viewModel.magusOutcome = true
                message += "+${viewModel.activeListOfRunes[position].bonus.toString()} ${viewModel.activeListOfRunes[position].statName}"
                message +=  ", -$sink sink"
            }
        }
        viewModel.historyLogList.add(0, HistoryLog(Date(), message, viewModel.currentSink, viewModel.magusOutcome))
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

        // update current sink value in parent fragment
        modifySinkValueOnTheMain(requireView(), viewModel)
    }

    fun modifySinkValueOnTheMain(view: View, viewModel: MagusViewModel) {

        when {
            viewModel.currentSink > viewModel.previousSink -> {
                binding.curentSinkValue.setTextColor(ContextCompat.getColor(requireContext(), R.color.sinkSuccess))
                binding.sinkDynamic.visibility = View.VISIBLE
                binding.sinkDynamic.setImageResource(R.drawable.arrow_upward_icon)
                binding.sinkDynamic.drawable.setTint(ContextCompat.getColor(requireContext(), R.color.sinkSuccess))
            }
            viewModel.currentSink < viewModel.previousSink -> {
                binding.curentSinkValue.setTextColor(ContextCompat.getColor(requireContext(), R.color.failure))
                binding.sinkDynamic.visibility = View.VISIBLE
                binding.sinkDynamic.setImageResource(R.drawable.arrow_downward_icon)
                binding.sinkDynamic.drawable.setTint(ContextCompat.getColor(requireContext(), R.color.failure))
            }
            else -> {
                binding.sinkDynamic.visibility = View.GONE
                binding.curentSinkValue.setTextColor(ContextCompat.getColor(requireContext(), R.color.sinkDefault))
            }
        }
        binding.curentSinkValue.text = mMagusViewModel.currentSink.toString()

    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_magus_menu, menu)
    }

    @Deprecated("Deprecated in Java")
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
        else if (item.itemId == R.id.menu_reset_magus) {
            val dialog = Dialog(requireContext())
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(true)
            dialog.setContentView(R.layout.dialog_message_restart_magus)

            val yesBtn = dialog.findViewById(R.id.exitYesButton) as Button
            val noBtn = dialog.findViewById(R.id.exitNoButton) as Button

            yesBtn.setOnClickListener {
                restartMagus(mMagusViewModel)
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

    private fun restartMagus (viewModel: MagusViewModel) {
        viewModel.previousSink = 0.0
        viewModel.currentSink = 0.0

        viewModel.historyLogList.clear()
        viewModel.historyLogList.add(HistoryLog(Date(),"${viewModel.itemName} smithmagus", 0.0, null))

        viewModel.magusOutcome = null

        modifySinkValueOnTheMain(requireView(), viewModel)

        val message = "Current item magus has been restarted. Sink value has been zeroed, history cleaned up"
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
    
    private fun runSecondStepOfTutorial() {
        if (preferenceManager.getInt("tutorialCurrentStep", 0) == 2) {

            val fancyShowCaseView1 =
                FancyShowCaseView.Builder(requireActivity())
                    .title(getString(R.string.tutorial_2_1))
                    .focusOn(binding.overviewPanel)
                    .titleStyle(R.style.MyTitleStyle, Gravity.CENTER)
                    .focusShape(FocusShape.ROUNDED_RECTANGLE)
                    .roundRectRadius(90)
                    .enableAutoTextPosition()
                    .build()

            val fancyShowCaseView2 =
                FancyShowCaseView.Builder(requireActivity())
                    .title(getString(R.string.tutorial_2_2))
                    .focusOn(binding.sinkLayourMain)
                    .titleStyle(R.style.MyTitleStyle, Gravity.CENTER)
                    .enableAutoTextPosition()
                    .build()

            val fancyShowCaseView3 =
                FancyShowCaseView.Builder(requireActivity())
                    .title(getString(R.string.tutorial_2_3))
                    .focusOn(binding.runeListRecyclerView)
                    .titleStyle(R.style.MyTitleStyle, Gravity.CENTER_HORIZONTAL)
                    .focusShape(FocusShape.ROUNDED_RECTANGLE)
                    .roundRectRadius(90)
                    .focusAnimationMaxValue(40)
                    .enableAutoTextPosition()
                    .build()

            val fancyShowCaseView4 =
                FancyShowCaseView.Builder(requireActivity())
                    .title(getString(R.string.tutorial_2_4))
                    .focusOn(binding.editRuneList)
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
    }

    private fun runFourthStepOfTutorial() {
        if (preferenceManager.getInt("tutorialCurrentStep", 0) == 4) {

            //wait till recycler view finished creation. otherwise empty viewItem and Null Pointer Exception
            binding.runeListRecyclerView.viewTreeObserver.addOnGlobalLayoutListener(
                object : ViewTreeObserver.OnGlobalLayoutListener {
                    override fun onGlobalLayout() {

                        val viewItem = binding.runeListRecyclerView.findViewHolderForAdapterPosition(0)

                        if (viewItem != null) {

//                            val fancyShowCaseView1 =
//                                FancyShowCaseView.Builder(requireActivity())
//                                    .title(getString(R.string.tutorial_4_1))
//                                    .focusOn(binding.runeListRecyclerView)
//                                    .titleStyle(R.style.MyTitleStyle, Gravity.CENTER or Gravity.TOP)
//                                    .focusShape(FocusShape.ROUNDED_RECTANGLE)
//                                    .roundRectRadius(90)
//                                    .enableAutoTextPosition()
//                                    .build()
//
//                            var target = viewItem?.itemView?.findViewById<View>(R.id.runeLayoutMain)
//                            val fancyShowCaseView2 =
//                                FancyShowCaseView.Builder(requireActivity())
//                                    .title(getString(R.string.tutorial_4_2))
//                                    .focusOn(target)
//                                    .titleStyle(R.style.MyTitleStyle, Gravity.CENTER)
//                                    .focusShape(FocusShape.ROUNDED_RECTANGLE)
//                                    .roundRectRadius(90)
//                                    .enableAutoTextPosition()
//                                    .build()
//
//                            target = viewItem?.itemView?.findViewById<View>(R.id.baseSinkLayoutMain)
//                            val fancyShowCaseView3 =
//                                FancyShowCaseView.Builder(requireActivity())
//                                    .title(getString(R.string.tutorial_4_3))
//                                    .focusOn(target)
//                                    .titleStyle(R.style.MyTitleStyle, Gravity.CENTER)
//                                    .focusShape(FocusShape.ROUNDED_RECTANGLE)
//                                    .roundRectRadius(140)
//                                    .enableAutoTextPosition()
//                                    .build()
//
//                            target = viewItem?.itemView?.findViewById<View>(R.id.runeSinkLayoutMain)
//                            val fancyShowCaseView4 =
//                                FancyShowCaseView.Builder(requireActivity())
//                                    .title(getString(R.string.tutorial_4_4))
//                                    .focusOn(target)
//                                    .titleStyle(R.style.MyTitleStyle, Gravity.CENTER)
//                                    .focusShape(FocusShape.ROUNDED_RECTANGLE)
//                                    .roundRectRadius(140)
//                                    .enableAutoTextPosition()
//                                    .build()
//
//                            target = viewItem?.itemView?.findViewById<View>(R.id.rune_row_background)
//                            val fancyShowCaseView5 =
//                                FancyShowCaseView.Builder(requireActivity())
//                                    .title(getString(R.string.tutorial_4_5))
//                                    .focusOn(target)
//                                    .titleStyle(R.style.MyTitleStyle, Gravity.CENTER)
//                                    .focusShape(FocusShape.ROUNDED_RECTANGLE)
//                                    .roundRectRadius(50)
//                                    .enableAutoTextPosition()
//                                    .build()
//
//                            val mQueue = FancyShowCaseQueue()
//                                .add(fancyShowCaseView1)
//                                .add(fancyShowCaseView2)
//                                .add(fancyShowCaseView3)
//                                .add(fancyShowCaseView4)
//                                .add(fancyShowCaseView5)
//                            mQueue.show()
//
//                            //Move to the 5th step
//                            preferenceManager.edit().putInt("tutorialCurrentStep", 5).apply()
                        }

                        // At this point the layout is complete
                        binding.runeListRecyclerView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    }
                }
            )
        }
    }

    private fun runSixthStepOfTutorial() {
        if (preferenceManager.getInt("tutorialCurrentStep", 0) == 6) {

            //wait till recycler view finished creation. otherwise empty viewItem and Null Pointer Exception
            binding.runeListRecyclerView.viewTreeObserver.addOnGlobalLayoutListener(
                object : ViewTreeObserver.OnGlobalLayoutListener {
                    override fun onGlobalLayout() {

                        val viewItem = binding.runeListRecyclerView.findViewHolderForAdapterPosition(0)

                        if (viewItem != null) {

//                            var target = viewItem?.itemView?.findViewById<View>(R.id.rune_row_background)
//                            val fancyShowCaseView1 =
//                                FancyShowCaseView.Builder(requireActivity())
//                                    .title(getString(R.string.tutorial_6_1))
//                                    .focusOn(target)
//                                    .titleStyle(R.style.MyTitleStyle, Gravity.CENTER or Gravity.TOP)
//                                    .focusShape(FocusShape.ROUNDED_RECTANGLE)
//                                    .roundRectRadius(90)
//                                    .enableAutoTextPosition()
//                                    .build()
//                                    .show()
//
//                            var x = viewItem?.itemView?.findViewById<View>(R.id.rune_row_background)
//                            swipeRecyclerViewItem(binding.runeListRecyclerView, 1, x.width /2,  ItemTouchHelper.START, 2000)
//
//
//                            //Move to the 7th step
//                            preferenceManager.edit().putInt("tutorialCurrentStep", 7).apply()
                        }

                        // At this point the layout is complete
                        binding.runeListRecyclerView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    }
                }
            )
        }
    }

    private fun runSeventhStepOfTutorial() {
        if (preferenceManager.getInt("tutorialCurrentStep", 0) == 7) {

            //wait till recycler view finished creation. otherwise empty viewItem and Null Pointer Exception
            binding.runeListRecyclerView.viewTreeObserver.addOnGlobalLayoutListener(
                object : ViewTreeObserver.OnGlobalLayoutListener {
                    override fun onGlobalLayout() {

                        val viewItem = binding.runeListRecyclerView.findViewHolderForAdapterPosition(1)

                        if (viewItem != null) {

//                            var target = viewItem?.itemView?.findViewById<View>(R.id.rune_row_background)
//                            val fancyShowCaseView1 =
//                                FancyShowCaseView.Builder(requireActivity())
//                                    .title(getString(R.string.tutorial_7_1))
//                                    .focusOn(target)
//                                    .titleStyle(R.style.MyTitleStyle, Gravity.CENTER or Gravity.TOP)
//                                    .focusShape(FocusShape.ROUNDED_RECTANGLE)
//                                    .roundRectRadius(90)
//                                    .enableAutoTextPosition()
//                                    .build()
//                                    .show()
//
//                            var x = viewItem?.itemView?.findViewById<View>(R.id.rune_row_background)
//                            swipeRecyclerViewItem(binding.runeListRecyclerView, 2, x.width /2,  ItemTouchHelper.END, 2000)
//
//                            //Move to the 8th step
//                            preferenceManager.edit().putInt("tutorialCurrentStep", 8).apply()
                        }

                        // At this point the layout is complete
                        binding.runeListRecyclerView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    }
                }
            )
        }
    }

    fun runEightStepOfTutorial() {
        if (preferenceManager.getInt("tutorialCurrentStep", 0) == 8) {
            val fancyShowCaseView1 =
                FancyShowCaseView.Builder(requireActivity())
                    .title(getString(R.string.tutorial_8_1))
                    .focusOn(binding.moveToHistory)
                    .titleStyle(R.style.MyTitleStyle, Gravity.CENTER)
                    .focusShape(FocusShape.ROUNDED_RECTANGLE)
                    .roundRectRadius(140)
                    .enableAutoTextPosition()
                    .build()
                    .show()

            //Move to the 8th step
            preferenceManager.edit().putInt("tutorialCurrentStep", 9).apply()
        }
    }

    private fun runTenthStepOfTutorial() {
        if (preferenceManager.getInt("tutorialCurrentStep", 0) == 10) {

            val fancyShowCaseView1 =
                FancyShowCaseView.Builder(requireActivity())
                    .title(getString(R.string.tutorial_10_1))
                    .titleStyle(R.style.MyTitleStyle, Gravity.CENTER or Gravity.TOP)
                    .enableAutoTextPosition()
                    .build()
                    .show()

            //Move to the third step
            preferenceManager.edit().putInt("tutorialCurrentStep", 0).apply()
        }
    }

    private fun swipeRecyclerViewItem(
        recyclerView: RecyclerView,
        index: Int,
        distance: Int,
        direction: Int,
        time: Long
    ) {
        val childView = recyclerView.getChildAt(index) ?: return
        val x = childView.width / 2F
        val viewLocation = IntArray(2)
        val parentLocation = IntArray(2)
        childView.getLocationInWindow(viewLocation)
        recyclerView.getLocationInWindow(parentLocation)
        val y = (viewLocation[1] - parentLocation[1] + childView.height) / 2F
        val downTime = SystemClock.uptimeMillis()
        recyclerView.dispatchTouchEvent(
            MotionEvent.obtain(
                downTime,
                downTime,
                MotionEvent.ACTION_DOWN,
                x,
                y,
                0
            )
        )
        ValueAnimator.ofInt(0, distance).apply {
            duration = time
            addUpdateListener {
                val dX = it.animatedValue as Int
                val mX = when (direction) {
                    ItemTouchHelper.END -> x + dX
                    ItemTouchHelper.START -> x - dX
                    else -> 0F
                }
                recyclerView.dispatchTouchEvent(
                    MotionEvent.obtain(
                        downTime,
                        SystemClock.uptimeMillis(),
                        MotionEvent.ACTION_MOVE,
                        mX,
                        y,
                        0
                    )
                )
            }
        }.start()
    }

    override fun selectRune(rune: Rune) {
        val action = MainMagusFragmentDirections.actionMainMagusFragmentToEditMagusFragment(rune)
        findNavController().navigate(action)
    }
}