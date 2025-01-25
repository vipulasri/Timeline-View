package com.github.vipulasri.timelineview.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.github.vipulasri.timelineview.sample.compose.ComposeSampleActivity
import com.github.vipulasri.timelineview.sample.recyclerview.basic.BasicTimelineActivity
import com.github.vipulasri.timelineview.sample.recyclerview.customization.CustomizationRecyclerViewActivity

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

            RecyclerViewSampleClicked -> {
                BasicTimelineActivity.launch(this)
            }

            RecyclerViewCustomizationSampleClicked -> {
                CustomizationRecyclerViewActivity.launch(this)
            }
        }
    }
}