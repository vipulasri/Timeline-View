package com.github.vipulasri.timelineview.sample

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.github.vipulasri.timelineview.sample.recyclerview.basic.BasicTimelineActivity
import com.github.vipulasri.timelineview.sample.recyclerview.customization.CustomizationRecyclerViewActivity

/**
 * Created by Vipul Asri on 21/01/25.
 */

class MainActivity : AppCompatActivity() {

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