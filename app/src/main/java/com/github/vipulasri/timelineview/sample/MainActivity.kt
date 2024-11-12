package com.github.vipulasri.timelineview.sample

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.vipulasri.timelineview.TimelineView
import com.github.vipulasri.timelineview.sample.databinding.ActivityMainBinding
import com.github.vipulasri.timelineview.sample.example.ExampleActivity
import com.github.vipulasri.timelineview.sample.extentions.dpToPx
import com.github.vipulasri.timelineview.sample.extentions.setGone
import com.github.vipulasri.timelineview.sample.extentions.setVisible
import com.github.vipulasri.timelineview.sample.model.OrderStatus
import com.github.vipulasri.timelineview.sample.model.Orientation
import com.github.vipulasri.timelineview.sample.model.TimeLineModel
import com.github.vipulasri.timelineview.sample.model.TimelineAttributes
import com.github.vipulasri.timelineview.sample.utils.ColorUtils

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mDataList = ArrayList<TimeLineModel>()
    private lateinit var mLayoutManager: LinearLayoutManager
    private lateinit var mAttributes: TimelineAttributes

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize view binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Default values for TimelineAttributes
        mAttributes = TimelineAttributes(
            markerSize = dpToPx(20f),
            markerColor = getColorCompat(com.dmitrymalkovich.android.materialdesigndimens.R.color.material_grey_500),
            markerInCenter = true,
            markerLeftPadding = dpToPx(0f),
            markerTopPadding = dpToPx(0f),
            markerRightPadding = dpToPx(0f),
            markerBottomPadding = dpToPx(0f),
            linePadding = dpToPx(2f),
            startLineColor = getColorCompat(R.color.colorAccent),
            endLineColor = getColorCompat(R.color.colorAccent),
            lineStyle = TimelineView.LineStyle.NORMAL,
            lineWidth = dpToPx(2f),
            lineDashWidth = dpToPx(4f),
            lineDashGap = dpToPx(2f)
        )

        // Setup data and RecyclerView
        setDataListItems()
        initRecyclerView()

        // Start ExampleActivity on button click
        binding.actionExampleActivity.setOnClickListener {
            startActivity(Intent(this, ExampleActivity::class.java))
        }

        // Open bottom sheet to change timeline attributes
        binding.fabOptions.setOnClickListener {
            TimelineAttributesBottomSheet.showDialog(
                supportFragmentManager,
                mAttributes,
                object : TimelineAttributesBottomSheet.Callbacks {
                    override fun onAttributesChanged(attributes: TimelineAttributes) {
                        mAttributes = attributes
                        initAdapter()
                    }
                })
        }

        // Handle orientation change for timeline
        mAttributes.onOrientationChanged = { oldValue, newValue ->
            if (oldValue != newValue) initRecyclerView()
        }

        mAttributes.orientation = Orientation.VERTICAL
    }

    // Populate the data list with timeline events
    private fun setDataListItems() {
        mDataList.apply {
            add(TimeLineModel("Item successfully delivered", "", OrderStatus.INACTIVE))
            add(
                TimeLineModel(
                    "Courier is out to deliver your order",
                    "2024-02-12 08:00",
                    OrderStatus.ACTIVE
                )
            )
            add(
                TimeLineModel(
                    "Item has reached courier facility at New Delhi",
                    "2024-02-11 21:00",
                    OrderStatus.COMPLETED
                )
            )
            add(
                TimeLineModel(
                    "Item has been given to the courier",
                    "2024-02-11 18:00",
                    OrderStatus.COMPLETED
                )
            )
            add(
                TimeLineModel(
                    "Item is packed and will dispatch soon",
                    "2024-02-11 09:30",
                    OrderStatus.COMPLETED
                )
            )
            add(
                TimeLineModel(
                    "Order is being readied for dispatch",
                    "2024-02-11 08:00",
                    OrderStatus.COMPLETED
                )
            )
            add(
                TimeLineModel(
                    "Order processing initiated",
                    "2024-02-10 15:00",
                    OrderStatus.COMPLETED
                )
            )
            add(
                TimeLineModel(
                    "Order confirmed by seller",
                    "2024-02-10 14:30",
                    OrderStatus.COMPLETED
                )
            )
            add(
                TimeLineModel(
                    "Order placed successfully",
                    "2024-02-10 14:00",
                    OrderStatus.COMPLETED
                )
            )
        }
    }

    // Initialize RecyclerView and set up its adapter
    private fun initRecyclerView() {
        initAdapter()

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            @SuppressLint("LongLogTag")
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (recyclerView.getChildAt(0).top < 0) binding.dropshadow.setVisible() else binding.dropshadow.setGone()
            }
        })
    }

    // Set up the adapter based on orientation
    private fun initAdapter() {
        mLayoutManager = if (mAttributes.orientation == Orientation.HORIZONTAL) {
            LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        } else {
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        }

        binding.recyclerView.apply {
            layoutManager = mLayoutManager
            adapter = TimeLineAdapter(mDataList, mAttributes)
        }
    }
}

fun Context.getColorCompat(colorResId: Int): Int {
    return ContextCompat.getColor(this, colorResId)
}