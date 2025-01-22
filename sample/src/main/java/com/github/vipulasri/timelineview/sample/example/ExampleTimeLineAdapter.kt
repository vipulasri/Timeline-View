package com.github.vipulasri.timelineview.sample.example

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
import com.github.vipulasri.timelineview.sample.model.TimeLineModel
import com.github.vipulasri.timelineview.sample.utils.VectorDrawableUtils

/**
 * Created by Vipul Asri on 13-12-2018.
 */

class ExampleTimeLineAdapter(private val feedList: List<TimeLineModel>) :
    RecyclerView.Adapter<ExampleTimeLineAdapter.TimeLineViewHolder>() {

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
        holder.bind(timeLineModel)
    }

    override fun getItemCount() = feedList.size

    inner class TimeLineViewHolder(
        private val binding: ItemTimelineBinding,
        private val viewType: Int
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.timeline.initLine(viewType)
        }

        fun bind(model: TimeLineModel) {
            when (model.status) {
                OrderStatus.INACTIVE -> {
                    setMarker(R.drawable.ic_marker_inactive, R.color.material_grey_500)
                }

                OrderStatus.ACTIVE -> {
                    setMarker(R.drawable.ic_marker_active, R.color.material_grey_500)
                }

                else -> {
                    setMarker(R.drawable.ic_marker, R.color.material_grey_500)
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
