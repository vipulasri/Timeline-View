package com.github.vipulasri.timelineview.sample

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.github.vipulasri.timelineview.sample.model.Orientation
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

/**
 * Created by Vipul Asri on 07-06-2016.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        verticalTimeLineButton.setOnClickListener { onButtonClick(Orientation.VERTICAL, false) }

        horizontalTimeLineButton.setOnClickListener { onButtonClick(Orientation.HORIZONTAL, false) }

        verticalTimeLineButtonWPadding.setOnClickListener { onButtonClick(Orientation.VERTICAL, true) }

        horizontalTimeLineButtonWPadding.setOnClickListener { onButtonClick(Orientation.HORIZONTAL, true) }

    }

    private fun onButtonClick(orientation: Orientation, withLinePadding: Boolean) {
        val intent = Intent(this, TimeLineActivity::class.java).apply {
            putExtra(EXTRA_ORIENTATION, orientation)
            putExtra(EXTRA_WITH_LINE_PADDING, withLinePadding)
        }
        startActivity(intent)
    }

    companion object {
        val EXTRA_ORIENTATION = "EXTRA_ORIENTATION"
        val EXTRA_WITH_LINE_PADDING = "EXTRA_WITH_LINE_PADDING"
    }

}
