package com.github.vipulasri.timelineview.sample

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.github.vipulasri.timelineview.TimelineView
import com.github.vipulasri.timelineview.sample.databinding.ItemTimelineBinding
import com.github.vipulasri.timelineview.sample.databinding.ItemTimelineHorizontalBinding
import com.github.vipulasri.timelineview.sample.model.OrderStatus
import com.github.vipulasri.timelineview.sample.model.Orientation
import com.github.vipulasri.timelineview.sample.model.TimeLineModel
import com.github.vipulasri.timelineview.sample.model.TimelineAttributes
import com.github.vipulasri.timelineview.sample.utils.VectorDrawableUtils
import com.github.vipulasri.timelineview.sample.extentions.formatDateTime
import com.github.vipulasri.timelineview.sample.extentions.setGone
import com.github.vipulasri.timelineview.sample.extentions.setVisible

class TimeLineAdapter(
    private val mFeedList: List<TimeLineModel>,
    private var mAttributes: TimelineAttributes
) : RecyclerView.Adapter<TimeLineAdapter.BaseViewHolder>() {

    private lateinit var mLayoutInflater: LayoutInflater

    override fun getItemViewType(position: Int): Int {
        return TimelineView.getTimeLineViewType(position, itemCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        if (!::mLayoutInflater.isInitialized) {
            mLayoutInflater = LayoutInflater.from(parent.context)
        }
        val binding = ItemTimelineBinding.inflate(mLayoutInflater, parent, false)

        return VerticalViewHolder(binding, mAttributes, viewType)

        /*if (mAttributes.orientation == Orientation.HORIZONTAL) {
            val binding = ItemTimelineHorizontalBinding.inflate(mLayoutInflater, parent, false)
            HorizontalViewHolder(binding, mAttributes, viewType)
        } else {
            val binding = ItemTimelineBinding.inflate(mLayoutInflater, parent, false)
            VerticalViewHolder(binding, mAttributes, viewType)
        }*/
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val timeLineModel = mFeedList[position]

        when (timeLineModel.status) {
            OrderStatus.INACTIVE -> {
                holder.timeline.marker = VectorDrawableUtils.getDrawable(
                    holder.itemView.context,
                    R.drawable.ic_marker_inactive,
                    mAttributes.markerColor
                )
            }
            OrderStatus.ACTIVE -> {
                holder.timeline.marker = VectorDrawableUtils.getDrawable(
                    holder.itemView.context,
                    R.drawable.ic_marker_active,
                    mAttributes.markerColor
                )
            }
            else -> {
                holder.timeline.setMarker(
                    ContextCompat.getDrawable(holder.itemView.context, R.drawable.ic_marker),
                    mAttributes.markerColor
                )
            }
        }

        if (timeLineModel.date.isNotEmpty()) {
            holder.date.setVisible()
            holder.date.text = timeLineModel.date.formatDateTime("yyyy-MM-dd HH:mm", "hh:mm a, dd-MMM-yyyy")
        } else {
            holder.date.setGone()
        }

        holder.message.text = timeLineModel.message
    }

    override fun getItemCount() = mFeedList.size

    // Base ViewHolder
    abstract class BaseViewHolder(binding: ItemTimelineBinding) : RecyclerView.ViewHolder(binding.root) {
        abstract val date: TextView
        abstract val message: TextView
        abstract val timeline: TimelineView
    }

    // ViewHolder for Vertical Orientation
    class VerticalViewHolder(
        private val binding: ItemTimelineBinding,
        private val mAttributes: TimelineAttributes,
        viewType: Int
    ) : BaseViewHolder(binding) {
        override val date = binding.textTimelineDate
        override val message = binding.textTimelineTitle
        override val timeline = binding.timeline

        init {
            initTimeline(viewType)
        }

        private fun initTimeline(viewType: Int) {
            timeline.initLine(viewType)
            timeline.markerSize = mAttributes.markerSize
            timeline.setMarkerColor(mAttributes.markerColor)
            timeline.isMarkerInCenter = mAttributes.markerInCenter
            timeline.markerPaddingLeft = mAttributes.markerLeftPadding
            timeline.markerPaddingTop = mAttributes.markerTopPadding
            timeline.markerPaddingRight = mAttributes.markerRightPadding
            timeline.markerPaddingBottom = mAttributes.markerBottomPadding
            timeline.linePadding = mAttributes.linePadding
            timeline.lineWidth = mAttributes.lineWidth
            timeline.setStartLineColor(mAttributes.startLineColor, viewType)
            timeline.setEndLineColor(mAttributes.endLineColor, viewType)
            timeline.lineStyle = mAttributes.lineStyle
            timeline.lineStyleDashLength = mAttributes.lineDashWidth
            timeline.lineStyleDashGap = mAttributes.lineDashGap
        }
    }

    // ViewHolder for Horizontal Orientation
    /*class HorizontalViewHolder(
        private val binding: ItemTimelineHorizontalBinding,
        private val mAttributes: TimelineAttributes,
        viewType: Int
    ) : BaseViewHolder(binding) {
        override val date = binding.textTimelineDate
        override val message = binding.textTimelineTitle
        override val timeline = binding.timeline

        init {
            initTimeline(viewType)
        }

        private fun initTimeline(viewType: Int) {
            timeline.initLine(viewType)
            timeline.markerSize = mAttributes.markerSize
            timeline.setMarkerColor(mAttributes.markerColor)
            timeline.isMarkerInCenter = mAttributes.markerInCenter
            timeline.markerPaddingLeft = mAttributes.markerLeftPadding
            timeline.markerPaddingTop = mAttributes.markerTopPadding
            timeline.markerPaddingRight = mAttributes.markerRightPadding
            timeline.markerPaddingBottom = mAttributes.markerBottomPadding
            timeline.linePadding = mAttributes.linePadding
            timeline.lineWidth = mAttributes.lineWidth
            timeline.setStartLineColor(mAttributes.startLineColor, viewType)
            timeline.setEndLineColor(mAttributes.endLineColor, viewType)
            timeline.lineStyle = mAttributes.lineStyle
            timeline.lineStyleDashLength = mAttributes.lineDashWidth
            timeline.lineStyleDashGap = mAttributes.lineDashGap
        }
    }*/
}
