package com.github.vipulasri.timelineview.sample

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.github.vipulasri.timelineview.sample.model.OrderStatus
import com.github.vipulasri.timelineview.sample.model.Orientation
import com.github.vipulasri.timelineview.sample.model.TimeLineModel
import com.github.vipulasri.timelineview.sample.utils.DateTimeUtils
import com.github.vipulasri.timelineview.sample.utils.VectorDrawableUtils
import com.github.vipulasri.timelineview.TimelineView
import kotlinx.android.synthetic.main.item_timeline.view.*

/**
 * Created by Vipul Asri on 05-12-2015.
 */

class TimeLineAdapter(private val mFeedList: List<TimeLineModel>, private val mOrientation: Orientation, private val mWithLinePadding: Boolean) : RecyclerView.Adapter<TimeLineAdapter.TimeLineViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return TimelineView.getTimeLineViewType(position, itemCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeLineViewHolder {
        val  layoutInflater = LayoutInflater.from(parent.context)
        val view: View

        if (mOrientation == Orientation.HORIZONTAL) {
            view = layoutInflater.inflate(if (mWithLinePadding) R.layout.item_timeline_horizontal_line_padding else R.layout.item_timeline_horizontal, parent, false)
        } else {
            view = layoutInflater.inflate(if (mWithLinePadding) R.layout.item_timeline_line_padding else R.layout.item_timeline, parent, false)
        }

        return TimeLineViewHolder(view, viewType)
    }

    override fun onBindViewHolder(holder: TimeLineViewHolder, position: Int) {

        val timeLineModel = mFeedList[position]

        if (timeLineModel.status == OrderStatus.INACTIVE) {
            holder.timeline.setMarker(VectorDrawableUtils.getDrawable(holder.itemView.context, R.drawable.ic_marker_inactive, android.R.color.darker_gray))
        } else if (timeLineModel.status == OrderStatus.ACTIVE) {
            holder.timeline.setMarker(VectorDrawableUtils.getDrawable(holder.itemView.context, R.drawable.ic_marker_active, R.color.colorPrimary))
        } else {
            holder.timeline.setMarker(ContextCompat.getDrawable(holder.itemView.context, R.drawable.ic_marker), ContextCompat.getColor(holder.itemView.context, R.color.colorPrimary))
        }

        if (!timeLineModel.date.isEmpty()) {
            holder.date.visibility = View.VISIBLE
            holder.date.text = DateTimeUtils.parseDateTime(timeLineModel.date, "yyyy-MM-dd HH:mm", "hh:mm a, dd-MMM-yyyy")
        } else
            holder.date.visibility = View.GONE

        holder.message.text = timeLineModel.message
    }

    override fun getItemCount(): Int {
        return mFeedList.size
    }

    inner class TimeLineViewHolder(itemView: View, viewType: Int) : RecyclerView.ViewHolder(itemView) {

        val date = itemView.text_timeline_date
        val message = itemView.text_timeline_title
        val timeline = itemView.time_marker

        init {
            timeline.initLine(viewType)
        }
    }

}
