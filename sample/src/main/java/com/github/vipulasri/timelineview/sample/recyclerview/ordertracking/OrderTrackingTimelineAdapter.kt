package com.github.vipulasri.timelineview.sample.recyclerview.ordertracking

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.github.vipulasri.timelineview.TimelineView
import com.github.vipulasri.timelineview.sample.R
import com.github.vipulasri.timelineview.sample.databinding.ItemTimelineBinding
import com.github.vipulasri.timelineview.sample.extentions.formatDateTime
import com.github.vipulasri.timelineview.sample.extentions.setVisible
import com.github.vipulasri.timelineview.sample.model.OrderStatus
import com.github.vipulasri.timelineview.sample.model.TimelineModel
import com.github.vipulasri.timelineview.sample.utils.VectorDrawableUtils

/**
 * Created by Vipul Asri on 09-02-2025.
 */

class OrderTrackingTimelineAdapter(private val feedList: List<TimelineModel>) :
    RecyclerView.Adapter<OrderTrackingTimelineAdapter.TimelineViewHolder>() {

    private lateinit var layoutInflater: LayoutInflater

    override fun getItemViewType(position: Int): Int {
        return TimelineView.getTimeLineViewType(position, itemCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimelineViewHolder {

        if (!::layoutInflater.isInitialized) {
            layoutInflater = LayoutInflater.from(parent.context)
        }

        return TimelineViewHolder(
            ItemTimelineBinding.inflate(layoutInflater, parent, false),
            viewType
        )
    }

    override fun onBindViewHolder(holder: TimelineViewHolder, position: Int) {
        val timeLineModel = feedList[position]
        holder.bind(timeLineModel)
    }

    override fun getItemCount() = feedList.size

    inner class TimelineViewHolder(
        private val binding: ItemTimelineBinding,
        private val viewType: Int
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.timeline.initLine(viewType)
        }

        fun bind(model: TimelineModel) {
            when (model.status) {
                OrderStatus.INACTIVE -> {
                    setMarker(R.drawable.ic_marker_inactive, R.color.colorGrey500)
                    binding.timeline.run {
                        startLineStyle = TimelineView.LineStyle.DASHED
                        endLineStyle = TimelineView.LineStyle.DASHED
                    }
                }

                OrderStatus.ACTIVE -> {
                    setMarker(R.drawable.ic_marker_active, R.color.colorGrey500)
                    binding.timeline.run {
                        startLineStyle = TimelineView.LineStyle.DASHED
                        endLineStyle = TimelineView.LineStyle.NORMAL
                    }
                }

                else -> {
                    setMarker(R.drawable.ic_marker, R.color.colorAccent)
                    binding.timeline.lineStyle = TimelineView.LineStyle.NORMAL
                }
            }

            if (model.date.isNotEmpty()) {
                binding.textTimelineDate.run {
                    setVisible()
                    text = model.date.formatDateTime("yyyy-MM-dd HH:mm", "hh:mm a, dd-MMM-yyyy")
                }
            } else
                binding.textTimelineDate.isVisible = false

            binding.textTimelineTitle.text = model.message
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
