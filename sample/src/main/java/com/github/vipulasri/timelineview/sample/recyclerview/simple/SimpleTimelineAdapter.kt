package com.github.vipulasri.timelineview.sample.recyclerview.simple

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.github.vipulasri.timelineview.TimelineView
import com.github.vipulasri.timelineview.sample.R
import com.github.vipulasri.timelineview.sample.databinding.ItemTimelineBinding
import com.github.vipulasri.timelineview.sample.utils.VectorDrawableUtils

/**
 * Created by Vipul Asri on 13-12-2018.
 */

class SimpleTimelineAdapter(private val feedList: List<String>) :
    RecyclerView.Adapter<SimpleTimelineAdapter.TimeLineViewHolder>() {

    private lateinit var layoutInflater: LayoutInflater

    override fun getItemViewType(position: Int): Int {
        return TimelineView.getTimeLineViewType(position, itemCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeLineViewHolder {

        if (!::layoutInflater.isInitialized) {
            layoutInflater = LayoutInflater.from(parent.context)
        }

        return TimeLineViewHolder(
            ItemTimelineBinding.inflate(layoutInflater, parent, false),
            viewType
        )
    }

    override fun onBindViewHolder(holder: TimeLineViewHolder, position: Int) {
        val timeLineModel = feedList[position]
        holder.bind(position, timeLineModel)
    }

    override fun getItemCount() = feedList.size

    inner class TimeLineViewHolder(
        private val binding: ItemTimelineBinding,
        private val viewType: Int
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.timeline.initLine(viewType)
        }

        fun bind(position: Int, model: String) {
            when (position) {
                0 -> {
                    setMarker(R.drawable.ic_marker_inactive, R.color.colorGrey500)
                }

                1 -> {
                    setMarker(R.drawable.ic_marker_active, R.color.colorGrey500)
                }

                else -> {
                    setMarker(R.drawable.ic_marker, R.color.colorGrey500)
                }
            }

            binding.textTimelineDate.isVisible = false
            binding.textTimelineTitle.text = model
            binding.timeline.lineStyle = TimelineView.LineStyle.NORMAL
        }

        private fun setMarker(drawableResId: Int, colorFilter: Int) {
            binding.timeline.marker = VectorDrawableUtils.getDrawable(
                binding.timeline.context,
                drawableResId,
                ContextCompat.getColor(binding.timeline.context, colorFilter)
            )
        }
    }

}
