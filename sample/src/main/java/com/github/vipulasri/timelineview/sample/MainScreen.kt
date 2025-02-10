package com.github.vipulasri.timelineview.sample

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.vipulasri.timelineview.sample.ui.theme.TimelineTheme

/**
 * Created by Vipul Asri on 22/01/25.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onClick: (MainScreenUiEvents) -> Unit
) {
    TimelineTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = stringResource(id = R.string.app_name)
                        )
                    }
                )
            },
            containerColor = Color.White
        ) { contentPadding ->
            Column(
                modifier = Modifier
                    .padding(contentPadding)
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                SampleCard(
                    title = "Compose Sample",
                    description = "A simple Compose sample",
                    onClick = { onClick(ComposeSampleClicked) }
                )

                SampleCard(
                    title = "Simple RecyclerView",
                    description = "A simple RecyclerView sample",
                    onClick = { onClick(SimpleRecyclerViewSampleClicked) }
                )

                SampleCard(
                    title = "Order Tracking RecyclerView",
                    description = "A order tracking sample with RecyclerView",
                    onClick = { onClick(OrderTrackingRecyclerViewSampleClicked) }
                )
            }
        }
    }
}

@Composable
private fun SampleCard(
    title: String,
    description: String,
    onClick: () -> Unit
) {
    OutlinedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = description,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
@Preview
private fun MainScreenPreview() {
    MainScreen(
        onClick = {}
    )
}