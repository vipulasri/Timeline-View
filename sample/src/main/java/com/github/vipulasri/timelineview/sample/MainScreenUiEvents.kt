package com.github.vipulasri.timelineview.sample

/**
 * Created by Vipul Asri on 22/01/25.
 */

sealed class MainScreenUiEvents

data object ComposeSampleClicked : MainScreenUiEvents()
data object SimpleRecyclerViewSampleClicked : MainScreenUiEvents()
data object OrderTrackingRecyclerViewSampleClicked : MainScreenUiEvents()