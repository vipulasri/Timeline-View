package com.github.vipulasri.timelineview.sample.example

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.github.vipulasri.timelineview.sample.model.OrderStatus
import com.github.vipulasri.timelineview.sample.model.TimeLineModel
import com.github.vipulasri.timelineview.sample.utils.VectorDrawableUtils
import com.github.vipulasri.timelineview.TimelineView
import com.github.vipulasri.timelineview.sample.R
import com.github.vipulasri.timelineview.sample.databinding.ItemTimelineBinding
import com.github.vipulasri.timelineview.sample.extentions.formatDateTime
import com.github.vipulasri.timelineview.sample.extentions.setGone
import com.github.vipulasri.timelineview.sample.extentions.setVisible

class ExampleTimeLineAdapter(private val mFeedList: List<TimeLineModel>) : RecyclerView.Adapter<ExampleTimeLineAdapter.TimeLineViewHolder>() {

    private lateinit var mLayoutInflater: LayoutInflater

    override fun getItemViewType(position: Int): Int {
        return TimelineView.getTimeLineViewType(position, itemCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeLineViewHolder {
        if (!::mLayoutInflater.isInitialized) {
            mLayoutInflater = LayoutInflater.from(parent.context)
        }

        // Inflate the binding
        val binding = ItemTimelineBinding.inflate(mLayoutInflater, parent, false)
        return TimeLineViewHolder(binding, viewType)
    }

    override fun onBindViewHolder(holder: TimeLineViewHolder, position: Int) {
        val timeLineModel = mFeedList[position]

        when {
            timeLineModel.status == OrderStatus.INACTIVE -> {
                setMarker(holder, R.drawable.ic_marker_inactive, com.dmitrymalkovich.android.materialdesigndimens.R.color.material_grey_500)
            }
            timeLineModel.status == OrderStatus.ACTIVE -> {
                setMarker(holder, R.drawable.ic_marker_active, com.dmitrymalkovich.android.materialdesigndimens.R.color.material_grey_500)
            }
            else -> {
                setMarker(holder, R.drawable.ic_marker, com.dmitrymalkovich.android.materialdesigndimens.R.color.material_grey_500)
            }
        }

        if (timeLineModel.date.isNotEmpty()) {
            holder.binding.textTimelineDate.setVisible()
            holder.binding.textTimelineDate.text = timeLineModel.date.formatDateTime("yyyy-MM-dd HH:mm", "hh:mm a, dd-MMM-yyyy")
        } else {
            holder.binding.textTimelineDate.setGone()
        }

        holder.binding.textTimelineTitle.text = timeLineModel.message
    }

    private fun setMarker(holder: TimeLineViewHolder, drawableResId: Int, colorFilter: Int) {
        holder.binding.timeline.marker = VectorDrawableUtils.getDrawable(
            holder.itemView.context,
            drawableResId,
            ContextCompat.getColor(holder.itemView.context, colorFilter)
        )
    }

    override fun getItemCount() = mFeedList.size

    inner class TimeLineViewHolder(val binding: ItemTimelineBinding, viewType: Int) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.timeline.initLine(viewType)
        }
    }
}
