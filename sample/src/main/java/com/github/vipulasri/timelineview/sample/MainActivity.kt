package com.github.vipulasri.timelineview.sample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.vipulasri.timelineview.sample.recyclerview.customization.CustomizationRecyclerViewActivity

/**
 * Created by Vipul Asri on 21/01/25.
 */

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startActivity(Intent(this, CustomizationRecyclerViewActivity::class.java))
    }

}