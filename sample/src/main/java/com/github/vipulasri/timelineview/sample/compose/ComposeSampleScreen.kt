package com.github.vipulasri.timelineview.sample.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.vipulasri.timelineview.sample.ui.theme.TimelineTheme

/**
 * Created by Vipul Asri on 22/01/25.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComposeSampleScreen(
    onBackClick: () -> Unit
) {
    TimelineTheme {

        val selectedType = remember {
            mutableStateOf(TimelineSampleType.VERTICAL)
        }

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = {
                        TimelineSampleType(
                            types = TimelineSampleType.entries,
                            selectedType = selectedType.value,
                            onTypeSelected = {
                                selectedType.value = it
                            }
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
            Column(
                modifier = Modifier.padding(contentPadding)
            ) {
                when (selectedType.value) {
                    TimelineSampleType.VERTICAL -> VerticalTimeline()
                    TimelineSampleType.HORIZONTAL -> HorizontalTimeline()
                    TimelineSampleType.ORDER_TRACKING -> OrderTrackingTimeline()
                    TimelineSampleType.CUSTOM_MARKERS -> CustomMarkersTimeline()
                }
            }
        }
    }
}

@Composable
private fun TimelineSampleType(
    types: List<TimelineSampleType>,
    selectedType: TimelineSampleType,
    onTypeSelected: (TimelineSampleType) -> Unit = {}
) {

    val isDropDownExpanded = remember {
        mutableStateOf(false)
    }

    Box {
        Row(
            modifier = Modifier
                .clickable {
                    isDropDownExpanded.value = true
                }
                .padding(16.dp)
        ) {
            Text(
                text = selectedType.title,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "dropdown",
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }

        DropdownMenu(
            expanded = isDropDownExpanded.value,
            onDismissRequest = {
                isDropDownExpanded.value = false
            }
        ) {
            types.forEach {
                DropdownMenuItem(
                    text = {
                        Text(text = it.title)
                    },
                    onClick = {
                        isDropDownExpanded.value = false
                        onTypeSelected(it)
                    }
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
private fun ComposeSampleScreenPreview() {
    ComposeSampleScreen(
        onBackClick = { }
    )
}

enum class TimelineSampleType(val title: String) {
    VERTICAL("Vertical Timeline"),
    HORIZONTAL("Horizontal Timeline"),
    ORDER_TRACKING("Order Tracking"),
    CUSTOM_MARKERS("Custom Marker")
}