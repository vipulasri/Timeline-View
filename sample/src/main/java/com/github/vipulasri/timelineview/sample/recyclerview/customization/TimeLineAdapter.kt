package com.github.vipulasri.timelineview.sample.recyclerview.customization

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.github.vipulasri.timelineview.TimelineView
import com.github.vipulasri.timelineview.sample.R
import com.github.vipulasri.timelineview.sample.databinding.ItemTimelineBinding
import com.github.vipulasri.timelineview.sample.databinding.ItemTimelineHorizontalBinding
import com.github.vipulasri.timelineview.sample.extentions.formatDateTime
import com.github.vipulasri.timelineview.sample.model.OrderStatus
import com.github.vipulasri.timelineview.sample.model.Orientation
import com.github.vipulasri.timelineview.sample.model.TimeLineModel
import com.github.vipulasri.timelineview.sample.model.TimelineAttributes
import com.github.vipulasri.timelineview.sample.utils.VectorDrawableUtils

/**
 * Created by Vipul Asri on 05-12-2015.
 */

class TimeLineAdapter(
    private val feedList: List<TimeLineModel>,
    private var attributes: TimelineAttributes
) : RecyclerView.Adapter<TimeLineAdapter.BaseTimeLineViewHolder>() {

    private lateinit var layoutInflater: LayoutInflater

    override fun getItemViewType(position: Int): Int {
        return TimelineView.getTimeLineViewType(position, itemCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseTimeLineViewHolder {
        if (!::layoutInflater.isInitialized) {
            layoutInflater = LayoutInflater.from(parent.context)
        }

        return when (attributes.orientation) {
            Orientation.HORIZONTAL -> HorizontalTimeLineViewHolder(
                ItemTimelineHorizontalBinding.inflate(layoutInflater, parent, false),
                viewType
            ).apply {
                initializeTimeline()
            }

            Orientation.VERTICAL -> VerticalTimeLineViewHolder(
                ItemTimelineBinding.inflate(layoutInflater, parent, false),
                viewType
            ).apply {
                initializeTimeline()
            }
        }
    }

    override fun onBindViewHolder(holder: BaseTimeLineViewHolder, position: Int) {
        holder.bind(feedList[position])
    }

    override fun getItemCount() = feedList.size

    abstract inner class BaseTimeLineViewHolder(
        private val binding: ViewBinding,
        private val viewType: Int
    ) : RecyclerView.ViewHolder(binding.root) {

        abstract val timeline: TimelineView
        abstract val textTimelineDate: TextView
        abstract val textTimelineTitle: TextView

        fun initializeTimeline() {
            timeline.apply {
                initLine(viewType)
                markerSize = attributes.markerSize
                setMarkerColor(attributes.markerColor)
                isMarkerInCenter = attributes.markerInCenter
                markerPaddingLeft = attributes.markerLeftPadding
                markerPaddingTop = attributes.markerTopPadding
                markerPaddingRight = attributes.markerRightPadding
                markerPaddingBottom = attributes.markerBottomPadding
                linePadding = attributes.linePadding
                lineWidth = attributes.lineWidth
                setStartLineColor(attributes.startLineColor, viewType)
                setEndLineColor(attributes.endLineColor, viewType)
                lineStyle = attributes.lineStyle
                lineStyleDashLength = attributes.lineDashWidth
                lineStyleDashGap = attributes.lineDashGap
            }
        }

        fun bind(model: TimeLineModel) {
            val marker = when (model.status) {
                OrderStatus.INACTIVE -> {
                    VectorDrawableUtils.getDrawable(
                        timeline.context,
                        R.drawable.ic_marker_inactive,
                        attributes.markerColor
                    )
                }

                OrderStatus.ACTIVE -> {
                    VectorDrawableUtils.getDrawable(
                        timeline.context,
                        R.drawable.ic_marker_active,
                        attributes.markerColor
                    )
                }

                else -> {
                    ContextCompat.getDrawable(
                        timeline.context,
                        R.drawable.ic_marker
                    )
                }
            }

            timeline.setMarker(marker)

            if (model.date.isNotEmpty()) {
                textTimelineDate.apply {
                    isVisible = true
                    text = model.date.formatDateTime("yyyy-MM-dd HH:mm", "hh:mm a, dd-MMM-yyyy")
                }
            } else {
                textTimelineDate.isVisible = false
            }
            textTimelineTitle.text = model.message
        }
    }

    inner class VerticalTimeLineViewHolder(
        private val binding: ItemTimelineBinding,
        viewType: Int
    ) : BaseTimeLineViewHolder(binding, viewType) {
        override val timeline: TimelineView = binding.timeline
        override val textTimelineDate: TextView = binding.textTimelineDate
        override val textTimelineTitle: TextView = binding.textTimelineTitle
    }

    inner class HorizontalTimeLineViewHolder(
        private val binding: ItemTimelineHorizontalBinding,
        viewType: Int
    ) : BaseTimeLineViewHolder(binding, viewType) {
        override val timeline: TimelineView = binding.timeline
        override val textTimelineDate: TextView = binding.textTimelineDate
        override val textTimelineTitle: TextView = binding.textTimelineTitle
    }
}
