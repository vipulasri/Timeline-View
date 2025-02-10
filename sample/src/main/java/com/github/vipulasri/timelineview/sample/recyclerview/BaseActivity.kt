package com.github.vipulasri.timelineview.sample.recyclerview

import android.graphics.drawable.Drawable
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

open class BaseActivity : AppCompatActivity() {

    protected fun setupToolbar(toolbar: Toolbar, isHomeActionEnabled: Boolean) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(isHomeActionEnabled)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //Menu
        when (item.itemId) {
            //When home is clicked
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun setHomeAsUpIndicator(drawable: Drawable) {
        supportActionBar?.setHomeAsUpIndicator(drawable)
    }

    fun setToolbarTitle(title: String) {
        supportActionBar?.title = title
    }
}
