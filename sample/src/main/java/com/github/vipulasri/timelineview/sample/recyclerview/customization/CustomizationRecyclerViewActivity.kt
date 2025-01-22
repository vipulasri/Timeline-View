package com.github.vipulasri.timelineview.sample.recyclerview.customization

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.vipulasri.timelineview.TimelineView
import com.github.vipulasri.timelineview.sample.R
import com.github.vipulasri.timelineview.sample.databinding.ActivityCustomizationRecyclerviewBinding
import com.github.vipulasri.timelineview.sample.recyclerview.basic.BasicTimelineActivity
import com.github.vipulasri.timelineview.sample.extentions.dpToPx
import com.github.vipulasri.timelineview.sample.extentions.getColorCompat
import com.github.vipulasri.timelineview.sample.extentions.setGone
import com.github.vipulasri.timelineview.sample.extentions.setVisible
import com.github.vipulasri.timelineview.sample.model.OrderStatus
import com.github.vipulasri.timelineview.sample.model.Orientation
import com.github.vipulasri.timelineview.sample.model.TimeLineModel
import com.github.vipulasri.timelineview.sample.model.TimelineAttributes
import com.github.vipulasri.timelineview.sample.recyclerview.BaseActivity

/**
 * Created by Vipul Asri on 07-06-2016.
 */
class CustomizationRecyclerViewActivity : BaseActivity() {

    private val mDataList = ArrayList<TimeLineModel>()
    private lateinit var mLayoutManager: LinearLayoutManager
    private lateinit var mAttributes: TimelineAttributes

    private lateinit var binding: ActivityCustomizationRecyclerviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomizationRecyclerviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //default values
        mAttributes = TimelineAttributes(
            markerSize = dpToPx(20f),
            markerColor = getColorCompat(R.color.material_grey_500),
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

        setDataListItems()
        initRecyclerView()

        binding.actionExampleActivity.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    BasicTimelineActivity::class.java
                )
            )
        }

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

        mAttributes.onOrientationChanged = { oldValue, newValue ->
            if (oldValue != newValue) initRecyclerView()
        }

        mAttributes.orientation = Orientation.VERTICAL
    }

    private fun setDataListItems() {
        mDataList.add(TimeLineModel("Item successfully delivered", "", OrderStatus.INACTIVE))
        mDataList.add(
            TimeLineModel(
                "Courier is out to delivery your order",
                "2017-02-12 08:00",
                OrderStatus.ACTIVE
            )
        )
        mDataList.add(
            TimeLineModel(
                "Item has reached courier facility at New Delhi",
                "2017-02-11 21:00",
                OrderStatus.COMPLETED
            )
        )
        mDataList.add(
            TimeLineModel(
                "Item has been given to the courier",
                "2017-02-11 18:00",
                OrderStatus.COMPLETED
            )
        )
        mDataList.add(
            TimeLineModel(
                "Item is packed and will dispatch soon",
                "2017-02-11 09:30",
                OrderStatus.COMPLETED
            )
        )
        mDataList.add(
            TimeLineModel(
                "Order is being readied for dispatch",
                "2017-02-11 08:00",
                OrderStatus.COMPLETED
            )
        )
        mDataList.add(
            TimeLineModel(
                "Order processing initiated",
                "2017-02-10 15:00",
                OrderStatus.COMPLETED
            )
        )
        mDataList.add(
            TimeLineModel(
                "Order confirmed by seller",
                "2017-02-10 14:30",
                OrderStatus.COMPLETED
            )
        )
        mDataList.add(
            TimeLineModel(
                "Order placed successfully",
                "2017-02-10 14:00",
                OrderStatus.COMPLETED
            )
        )
    }

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
