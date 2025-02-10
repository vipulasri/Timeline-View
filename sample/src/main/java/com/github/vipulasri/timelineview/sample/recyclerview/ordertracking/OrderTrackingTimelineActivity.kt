package com.github.vipulasri.timelineview.sample.recyclerview.ordertracking

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.vipulasri.timelineview.sample.databinding.ActivityBasicTimelineBinding
import com.github.vipulasri.timelineview.sample.model.OrderStatus
import com.github.vipulasri.timelineview.sample.model.TimelineModel
import com.github.vipulasri.timelineview.sample.recyclerview.BaseActivity

/**
 * Created by Vipul Asri on 09-02-2025.
 */
class OrderTrackingTimelineActivity : BaseActivity() {

    private lateinit var binding: ActivityBasicTimelineBinding

    private lateinit var adapter: OrderTrackingTimelineAdapter
    private val dataList = ArrayList<TimelineModel>()
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBasicTimelineBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar(binding.toolbar, true)
        setToolbarTitle("Order Tracking Timeline")

        setDataListItems()
        initRecyclerView()
    }

    private fun setDataListItems() {
        dataList.add(TimelineModel("Item successfully delivered", "", OrderStatus.INACTIVE))
        dataList.add(
            TimelineModel(
                "Courier is out to delivery your order",
                "2025-02-12 08:00",
                OrderStatus.ACTIVE
            )
        )
        dataList.add(
            TimelineModel(
                "Item has reached courier facility at New Delhi",
                "2025-02-11 21:00",
                OrderStatus.COMPLETED
            )
        )
        dataList.add(
            TimelineModel(
                "Item has been given to the courier",
                "2025-02-11 18:00",
                OrderStatus.COMPLETED
            )
        )
        dataList.add(
            TimelineModel(
                "Item is packed and will dispatch soon",
                "2025-02-11 09:30",
                OrderStatus.COMPLETED
            )
        )
        dataList.add(
            TimelineModel(
                "Order is being readied for dispatch",
                "2025-02-11 08:00",
                OrderStatus.COMPLETED
            )
        )
        dataList.add(
            TimelineModel(
                "Order processing initiated",
                "2025-02-10 15:00",
                OrderStatus.COMPLETED
            )
        )
        dataList.add(
            TimelineModel(
                "Order confirmed by seller",
                "2025-02-10 14:30",
                OrderStatus.COMPLETED
            )
        )
        dataList.add(
            TimelineModel(
                "Order placed successfully",
                "2025-02-10 14:00",
                OrderStatus.COMPLETED
            )
        )
    }

    private fun initRecyclerView() {
        layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.recyclerView.layoutManager = layoutManager
        adapter = OrderTrackingTimelineAdapter(dataList)
        binding.recyclerView.adapter = adapter
    }

    companion object {
        fun launch(context: Activity) {
            context.startActivity(Intent(context, OrderTrackingTimelineActivity::class.java))
        }
    }

}
