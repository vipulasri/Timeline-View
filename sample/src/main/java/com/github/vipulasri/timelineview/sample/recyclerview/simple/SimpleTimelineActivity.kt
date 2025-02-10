package com.github.vipulasri.timelineview.sample.recyclerview.simple

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.vipulasri.timelineview.sample.databinding.ActivityBasicTimelineBinding
import com.github.vipulasri.timelineview.sample.recyclerview.BaseActivity

/**
 * Created by Vipul Asri on 13-12-2018.
 */
class SimpleTimelineActivity : BaseActivity() {

    private lateinit var binding: ActivityBasicTimelineBinding

    private lateinit var adapter: SimpleTimelineAdapter
    private val dataList = ArrayList<String>()
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
        dataList.addAll(
            listOf(
                "Item 1",
                "Item 2",
                "Item 3",
                "Item 4",
                "Item 5",
                "Item 6",
                "Item 7",
                "Item 8",
                "Item 9",
                "Item 10",
            )
        )
    }

    private fun initRecyclerView() {
        layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.recyclerView.layoutManager = layoutManager
        adapter = SimpleTimelineAdapter(dataList)
        binding.recyclerView.adapter = adapter
    }

    companion object {
        fun launch(context: Activity) {
            context.startActivity(Intent(context, SimpleTimelineActivity::class.java))
        }
    }

}
