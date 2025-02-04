package com.github.vipulasri.timelineview.sample.recyclerview.basic

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.vipulasri.timelineview.sample.recyclerview.BaseActivity
import com.github.vipulasri.timelineview.sample.R
import com.github.vipulasri.timelineview.sample.databinding.ActivityBasicTimelineBinding
import com.github.vipulasri.timelineview.sample.model.OrderStatus
import com.github.vipulasri.timelineview.sample.model.TimeLineModel
import java.util.ArrayList

/**
 * Created by Vipul Asri on 13-12-2018.
 */
class BasicTimelineActivity : BaseActivity() {

    private lateinit var binding: ActivityBasicTimelineBinding

    private lateinit var adapter: BasicTimeLineAdapter
    private val dataList = ArrayList<TimeLineModel>()
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBasicTimelineBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupToolbar(binding.toolbar, true)

        setDataListItems()
        initRecyclerView()
    }

    private fun setDataListItems() {
        dataList.add(TimeLineModel("Item successfully delivered", "", OrderStatus.INACTIVE))
        dataList.add(TimeLineModel("Courier is out to delivery your order", "2017-02-12 08:00", OrderStatus.ACTIVE))
        dataList.add(TimeLineModel("Item has reached courier facility at New Delhi", "2017-02-11 21:00", OrderStatus.COMPLETED))
        dataList.add(TimeLineModel("Item has been given to the courier", "2017-02-11 18:00", OrderStatus.COMPLETED))
        dataList.add(TimeLineModel("Item is packed and will dispatch soon", "2017-02-11 09:30", OrderStatus.COMPLETED))
        dataList.add(TimeLineModel("Order is being readied for dispatch", "2017-02-11 08:00", OrderStatus.COMPLETED))
        dataList.add(TimeLineModel("Order processing initiated", "2017-02-10 15:00", OrderStatus.COMPLETED))
        dataList.add(TimeLineModel("Order confirmed by seller", "2017-02-10 14:30", OrderStatus.COMPLETED))
        dataList.add(TimeLineModel("Order placed successfully", "2017-02-10 14:00", OrderStatus.COMPLETED))
    }

    private fun initRecyclerView() {
        layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.recyclerView.layoutManager = layoutManager
        adapter = BasicTimeLineAdapter(dataList)
        binding.recyclerView.adapter = adapter
    }

    companion object {
        fun launch(context: Activity) {
            context.startActivity(Intent(context, BasicTimelineActivity::class.java))
        }
    }

}
