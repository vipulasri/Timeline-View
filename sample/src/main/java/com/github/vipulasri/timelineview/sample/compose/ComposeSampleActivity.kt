package com.github.vipulasri.timelineview.sample.compose

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

/**
 * Created by Vipul Asri on 22/01/25.
 */

class ComposeSampleActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeSampleScreen(
                onBackClick = {
                    onBackPressed()
                }
            )
        }
    }

    companion object {
        fun launch(activity: ComponentActivity) {
            activity.startActivity(Intent(activity, ComposeSampleActivity::class.java))
        }
    }

}