package com.github.vipulasri.timelineview.sample.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.vipulasri.timelineview.sample.R
import com.github.vipulasri.timelineview.sample.extentions.formatDateTime
import com.github.vipulasri.timelineview.sample.model.OrderStatus
import com.github.vipulasri.timelineview.sample.model.TimelineModel
import com.github.vipulasri.timelineview.sample.ui.theme.TimelineTheme
import com.vipulasri.timelineview.compose.DashedLine
import com.vipulasri.timelineview.compose.LineStyle
import com.vipulasri.timelineview.compose.SolidLine
import com.vipulasri.timelineview.compose.Timeline
import com.vipulasri.timelineview.compose.TimelineOrientation
import com.vipulasri.timelineview.compose.getLineType

/**
 * Created by Vipul Asri on 13/02/25.
 */

@Composable
fun OrderTrackingTimeline() {
    val items = listOf(
        TimelineModel("Item successfully delivered", "", OrderStatus.INACTIVE),
        TimelineModel(
            "Courier is out to delivery your order",
            "2025-02-12 08:00",
            OrderStatus.ACTIVE
        ),
        TimelineModel(
            "Item has reached courier facility at New Delhi",
            "2025-02-11 21:00",
            OrderStatus.COMPLETED
        ),
        TimelineModel(
            "Item has been given to the courier",
            "2025-02-11 18:00",
            OrderStatus.COMPLETED
        ),
        TimelineModel(
            "Item is packed and will dispatch soon",
            "2025-02-11 09:30",
            OrderStatus.COMPLETED
        ),
        TimelineModel(
            "Order is being readied for dispatch",
            "2025-02-11 08:00",
            OrderStatus.COMPLETED
        ),
        TimelineModel(
            "Order processing initiated",
            "2025-02-11 06:30",
            OrderStatus.COMPLETED
        ),
        TimelineModel(
            "Order confirmed by seller",
            "2025-02-11 06:00",
            OrderStatus.COMPLETED
        ),
        TimelineModel(
            "Order placed successfully",
            "2025-02-11 05:30",
            OrderStatus.COMPLETED
        )
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        itemsIndexed(items) { index, item ->
            Row(
                modifier = Modifier.height(IntrinsicSize.Min)
            ) {
                Timeline(
                    modifier = Modifier.fillMaxHeight(),
                    lineType = getLineType(index, items.size),
                    orientation = TimelineOrientation.Vertical,
                    lineStyle = when (item.status) {
                        OrderStatus.INACTIVE -> LineStyle.dashed(
                            color = MaterialTheme.colorScheme.primary,
                            width = 3.dp
                        )

                        OrderStatus.ACTIVE -> LineStyle(
                            startLine = DashedLine(
                                color = MaterialTheme.colorScheme.primary,
                                width = 3.dp
                            ),
                            endLine = SolidLine(
                                color = MaterialTheme.colorScheme.primary,
                                width = 3.dp
                            ),
                        )

                        OrderStatus.COMPLETED -> LineStyle.solid(
                            color = MaterialTheme.colorScheme.secondary,
                            width = 3.dp
                        )
                    },
                    marker = {
                        when (item.status) {
                            OrderStatus.INACTIVE -> {
                                Image(
                                    modifier = Modifier.size(24.dp),
                                    painter = painterResource(R.drawable.ic_marker_inactive),
                                    contentDescription = null,
                                    colorFilter = ColorFilter.tint(Color.Gray)
                                )
                            }

                            OrderStatus.ACTIVE -> {
                                Image(
                                    modifier = Modifier.size(24.dp),
                                    painter = painterResource(R.drawable.ic_marker_active),
                                    contentDescription = null,
                                    colorFilter = ColorFilter.tint(Color.Gray)
                                )
                            }

                            OrderStatus.COMPLETED -> Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .background(MaterialTheme.colorScheme.secondary, CircleShape)
                            )
                        }
                    }
                )
                OutlinedCard(
                    modifier = Modifier.padding(16.dp),
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        if (item.date.isNotEmpty()) {
                            Text(
                                text = item.date.formatDateTime(
                                    "yyyy-MM-dd HH:mm",
                                    "hh:mm a, dd-MMM-yyyy"
                                ),
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                        Text(
                            text = item.message,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
private fun OrderTrackingTimelinePreview() {
    TimelineTheme {
        OrderTrackingTimeline()
    }
}