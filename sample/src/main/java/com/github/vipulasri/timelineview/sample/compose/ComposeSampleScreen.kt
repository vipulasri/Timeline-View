package com.github.vipulasri.timelineview.sample.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vipulasri.timelineview.compose.LineStyle
import com.vipulasri.timelineview.compose.Timeline
import com.vipulasri.timelineview.compose.TimelineOrientation
import com.vipulasri.timelineview.compose.getLineType

/**
 * Created by Vipul Asri on 22/01/25.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComposeSampleScreen(
    onBackClick: () -> Unit
) {
    MaterialTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Compose Sample"
                        )
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors().copy(
                        containerColor = Color.Transparent,
                    ),
                    navigationIcon = {
                        IconButton(onBackClick) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "back"
                            )
                        }
                    }
                )
            },
            containerColor = Color.White
        ) { contentPadding ->

            val items = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20)

            LazyColumn(
                modifier = Modifier
                    .padding(contentPadding)
                    .fillMaxSize(),
                contentPadding = PaddingValues(16.dp)
            ) {
                itemsIndexed(items) { index, item ->
                    Row(
                        modifier = Modifier.height(IntrinsicSize.Min)
                    ) {
                        Timeline(
                            modifier = Modifier.fillMaxHeight(),
                            lineType = getLineType(index, items.size),
                            orientation = TimelineOrientation.Vertical,
                            lineStyle = LineStyle.solid(
                                color = Color(0xFF4CAF50),
                                width = 2.dp
                            ),
                            marker = {
                                Box(
                                    modifier = Modifier
                                        .size(24.dp)
                                        .background(Color(0xFF2196F3), CircleShape)
                                )
                            }
                        )
                        Text(
                            text = "Item $item",
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview
private fun ComposeSampleScreenPreview() {
    ComposeSampleScreen(
        onBackClick = { }
    )
}