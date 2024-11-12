package com.github.vipulasri.timelineview.sample.example

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.vipulasri.timelineview.sample.BaseActivity
import com.github.vipulasri.timelineview.sample.R
import com.github.vipulasri.timelineview.sample.model.OrderStatus
import com.github.vipulasri.timelineview.sample.model.TimeLineModel
import com.github.vipulasri.timelineview.sample.databinding.ActivityExampleBinding

class ExampleActivity : BaseActivity() {

    private lateinit var binding: ActivityExampleBinding
    private lateinit var mAdapter: ExampleTimeLineAdapter
    private val mDataList = ArrayList<TimeLineModel>()
    private lateinit var mLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Use view binding instead of setContentView
        binding = ActivityExampleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set activity title and back navigation
        setActivityTitle(getString(R.string.activity_example_label))
        isDisplayHomeAsUpEnabled = true

        // Set up the data and RecyclerView
        setDataListItems()
        initRecyclerView()
    }

    // Populate the data list with timeline events
    private fun setDataListItems() {
        mDataList.apply {
            add(TimeLineModel("Item successfully delivered", "", OrderStatus.INACTIVE))
            add(TimeLineModel("Courier is out to delivery your order", "2017-02-12 08:00", OrderStatus.ACTIVE))
            add(TimeLineModel("Item has reached courier facility at New Delhi", "2017-02-11 21:00", OrderStatus.COMPLETED))
            add(TimeLineModel("Item has been given to the courier", "2017-02-11 18:00", OrderStatus.COMPLETED))
            add(TimeLineModel("Item is packed and will dispatch soon", "2017-02-11 09:30", OrderStatus.COMPLETED))
            add(TimeLineModel("Order is being readied for dispatch", "2017-02-11 08:00", OrderStatus.COMPLETED))
            add(TimeLineModel("Order processing initiated", "2017-02-10 15:00", OrderStatus.COMPLETED))
            add(TimeLineModel("Order confirmed by seller", "2017-02-10 14:30", OrderStatus.COMPLETED))
            add(TimeLineModel("Order placed successfully", "2017-02-10 14:00", OrderStatus.COMPLETED))
        }
    }

    // Initialize the RecyclerView with the timeline adapter and layout manager
    private fun initRecyclerView() {
        mLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        // Set up RecyclerView using view binding
        binding.recyclerView.apply {
            layoutManager = mLayoutManager
            mAdapter = ExampleTimeLineAdapter(mDataList)
            adapter = mAdapter
        }
    }

}
