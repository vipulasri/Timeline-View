package com.github.vipulasri.timelineview.sample.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.vipulasri.timelineview.sample.ui.theme.TimelineTheme
import com.vipulasri.timelineview.compose.DashedLine
import com.vipulasri.timelineview.compose.LineStyle
import com.vipulasri.timelineview.compose.SolidLine
import com.vipulasri.timelineview.compose.Timeline
import com.vipulasri.timelineview.compose.getLineType

/**
 * Created by Vipul Asri on 13/02/25.
 */

@Composable
fun CustomMarkersTimeline() {
    Column(Modifier.padding(horizontal = 16.dp)) {
        val totalItems = 4
        repeat(totalItems) { position ->

            var modifier = Modifier.height(100.dp)

            if (position != 1) {
                modifier = modifier.then(Modifier.padding(horizontal = 13.dp))
            }

            Timeline(
                modifier = modifier,
                lineType = getLineType(position, totalItems),
                lineStyle = when (position) {
                    0 -> {
                        LineStyle.dashed(
                            color = Color.Gray,
                            width = 3.dp
                        )
                    }

                    1 -> {
                        LineStyle(
                            startLine = DashedLine(
                                color = Color.Gray,
                                width = 3.dp
                            ),
                            endLine = SolidLine(
                                color = MaterialTheme.colorScheme.primary,
                                width = 3.dp
                            ),
                        )
                    }

                    else -> {
                        LineStyle.solid(
                            color = MaterialTheme.colorScheme.primary,
                            width = 3.dp
                        )
                    }
                }
            ) {
                // Custom marker examples
                when (position) {
                    0 -> Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(24.dp)
                    )

                    1 -> Box(
                        modifier = Modifier
                            .size(50.dp)
                            .background(
                                MaterialTheme.colorScheme.primary,
                                RoundedCornerShape(4.dp)
                            )
                    )

                    2 -> Box(
                        Modifier
                            .size(24.dp)
                            .background(MaterialTheme.colorScheme.primary, CircleShape)
                    ) {
                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            text = "3",
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    }

                    else -> {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .background(MaterialTheme.colorScheme.primary, CircleShape)
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
private fun CustomMarkersTimelinePreview() {
    TimelineTheme {
        CustomMarkersTimeline()
    }
}