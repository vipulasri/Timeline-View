package com.github.vipulasri.timelineview.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.MenuItem

import com.github.vipulasri.timelineview.sample.R
import com.github.vipulasri.timelineview.sample.model.OrderStatus
import com.github.vipulasri.timelineview.sample.model.Orientation
import com.github.vipulasri.timelineview.sample.model.TimeLineModel
import kotlinx.android.synthetic.main.activity_timeline.*
import kotlinx.android.synthetic.main.content_timeline.*

import java.util.ArrayList

/**
 * Created by Vipul Asri on 05-12-2015.
 */
class TimeLineActivity : AppCompatActivity() {

    private lateinit var mTimeLineAdapter: TimeLineAdapter
    private val mDataList = ArrayList<TimeLineModel>()
    private lateinit var mOrientation: Orientation
    private var mWithLinePadding: Boolean = false

    private val linearLayoutManager: LinearLayoutManager
        get() = if (mOrientation == Orientation.HORIZONTAL) {
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        } else {
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timeline)
        setSupportActionBar(toolbar)

        if (supportActionBar != null)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        mOrientation = intent.getSerializableExtra(MainActivity.EXTRA_ORIENTATION) as Orientation
        mWithLinePadding = intent.getBooleanExtra(MainActivity.EXTRA_WITH_LINE_PADDING, false)

        title = if (mOrientation == Orientation.HORIZONTAL) resources.getString(R.string.horizontal_timeline) else resources.getString(R.string.vertical_timeline)

        recyclerView.layoutManager = linearLayoutManager
        recyclerView.setHasFixedSize(true)

        initView()
    }

    private fun initView() {
        setDataListItems()
        mTimeLineAdapter = TimeLineAdapter(mDataList, mOrientation, mWithLinePadding)
        recyclerView.adapter = mTimeLineAdapter
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //Menu
        when (item.itemId) {
            //When home is clicked
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.putSerializable(MainActivity.EXTRA_ORIENTATION, mOrientation)
        super.onSaveInstanceState(savedInstanceState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(MainActivity.EXTRA_ORIENTATION)) {
                mOrientation = savedInstanceState.getSerializable(MainActivity.EXTRA_ORIENTATION) as Orientation
            }
        }
        super.onRestoreInstanceState(savedInstanceState)
    }
}
