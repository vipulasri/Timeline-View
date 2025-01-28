package com.github.vipulasri.timelineview.sample

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
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
                    .padding(16.dp)
            ) {
                OutlinedButton(
                    onClick = { onClick(ComposeSampleClicked) },
                ) {
                    Text(
                        text = "Compose Sample"
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedButton(
                    onClick = { onClick(RecyclerViewSampleClicked) },
                ) {
                    Text(
                        text = "RecyclerView Sample"
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedButton(
                    onClick = { onClick(RecyclerViewCustomizationSampleClicked) },
                ) {
                    Text(
                        text = "RecyclerView Customization Sample"
                    )
                }
            }
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