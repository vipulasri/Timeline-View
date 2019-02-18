package com.github.vipulasri.timelineview.sample

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.vipulasri.timelineview.TimelineView
import com.github.vipulasri.timelineview.sample.example.ExampleActivity
import com.github.vipulasri.timelineview.sample.example.toPx
import com.github.vipulasri.timelineview.sample.model.OrderStatus
import com.github.vipulasri.timelineview.sample.model.Orientation
import com.github.vipulasri.timelineview.sample.model.TimeLineModel
import com.github.vipulasri.timelineview.sample.model.TimelineAttributes
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

/**
 * Created by Vipul Asri on 07-06-2016.
 */
class MainActivity : BaseActivity() {

    private lateinit var mAdapter: TimeLineAdapter
    private val mDataList = ArrayList<TimeLineModel>()
    private lateinit var mLayoutManager: LinearLayoutManager
    private lateinit var mAttributes: TimelineAttributes

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentViewWithoutInject(R.layout.activity_main)

        //default values
        mAttributes = TimelineAttributes(
                markerSize = 20.toPx,
                markerColor = ContextCompat.getColor(this, R.color.material_grey_500),
                markerInCenter = true,
                linePadding = 2.toPx,
                startLineColor = ContextCompat.getColor(this, R.color.colorAccent),
                endLineColor = ContextCompat.getColor(this, R.color.colorAccent),
                lineStyle = TimelineView.LineStyle.NORMAL,
                lineWidth = 2.toPx,
                lineDashWidth = 4.toPx,
                lineDashGap = 2.toPx
        )

        setDataListItems()
        initRecyclerView()

        action_example_activity.setOnClickListener { startActivity(Intent(this, ExampleActivity::class.java)) }

        fab_options.setOnClickListener {
            TimelineAttributesBottomSheet.showDialog(supportFragmentManager, mAttributes, object: TimelineAttributesBottomSheet.Callbacks {
                override fun onAttributesChanged(attributes: TimelineAttributes) {
                    mAttributes = attributes
                    initAdapter()
                }
            })
        }

        mAttributes.onOrientationChanged = { oldValue, newValue ->
            if(oldValue != newValue) initRecyclerView()
        }

        mAttributes.orientation = Orientation.VERTICAL
    }

    private fun setDataListItems() {
        mDataList.add(TimeLineModel("Item successfully delivered", "", OrderStatus.INACTIVE))
        mDataList.add(TimeLineModel("Courier is out to delivery your order", "2017-02-12 08:00", OrderStatus.ACTIVE))
        mDataList.add(TimeLineModel("Item has reached courier facility at New Delhi", "2017-02-11 21:00", OrderStatus.COMPLETED))
        mDataList.add(TimeLineModel("Item has been given to the courier", "2017-02-11 18:00", OrderStatus.COMPLETED))
        mDataList.add(TimeLineModel("Item is packed and will dispatch soon", "2017-02-11 09:30", OrderStatus.COMPLETED))
        mDataList.add(TimeLineModel("Order is being readied for dispatch", "2017-02-11 08:00", OrderStatus.COMPLETED))
        mDataList.add(TimeLineModel("Order processing initiated", "2017-02-10 15:00", OrderStatus.COMPLETED))
        mDataList.add(TimeLineModel("Order confirmed by seller", "2017-02-10 14:30", OrderStatus.COMPLETED))
        mDataList.add(TimeLineModel("Order placed successfully", "2017-02-10 14:00", OrderStatus.COMPLETED))
    }

    private fun initRecyclerView() {
        initAdapter()
        recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            @SuppressLint("LongLogTag")
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                dropshadow.visibility = if(recyclerView.getChildAt(0).top < 0) View.VISIBLE else View.GONE
            }
        })
    }

    private fun initAdapter() {

        mLayoutManager = if (mAttributes.orientation == Orientation.HORIZONTAL) {
            LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        } else {
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        }

        recyclerView.layoutManager = mLayoutManager

        mAdapter = TimeLineAdapter(mDataList, mAttributes)
        recyclerView.adapter = mAdapter
    }

}
