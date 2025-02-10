package com.github.vipulasri.timelineview.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.github.vipulasri.timelineview.sample.compose.ComposeSampleActivity
import com.github.vipulasri.timelineview.sample.recyclerview.ordertracking.OrderTrackingTimelineActivity
import com.github.vipulasri.timelineview.sample.recyclerview.simple.SimpleTimelineActivity

/**
 * Created by Vipul Asri on 21/01/25.
 */

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen(
                onClick = ::handleUiEvent
            )
        }
    }

    private fun handleUiEvent(uiEvent: MainScreenUiEvents) {
        when (uiEvent) {
            ComposeSampleClicked -> {
                ComposeSampleActivity.launch(this)
            }

            SimpleRecyclerViewSampleClicked -> {
                SimpleTimelineActivity.launch(this)
            }

            OrderTrackingRecyclerViewSampleClicked -> {
                OrderTrackingTimelineActivity.launch(this)
            }
        }
    }
}