package com.github.vipulasri.timelineview.sample.recyclerview

import android.graphics.drawable.Drawable
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.github.vipulasri.timelineview.sample.R

open class BaseActivity : AppCompatActivity() {

    var toolbar: Toolbar? = null

    //If back button is displayed in action bar, return false
    protected var isDisplayHomeAsUpEnabled: Boolean
        get() = false
        set(value) {
            supportActionBar?.setDisplayHomeAsUpEnabled(value)
        }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)

        injectViews()

        //Displaying the back button in the action bar
        if (isDisplayHomeAsUpEnabled) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    protected fun injectViews() {
        toolbar = findViewById(R.id.toolbar)
        setupToolbar()
    }

    fun setContentViewWithoutInject(layoutResId: Int) {
        super.setContentView(layoutResId)
    }

    protected fun setupToolbar() {
        toolbar?.let { toolbar ->
            setSupportActionBar(toolbar)
        }
    }

    fun setActivityTitle(title: Int) {
        supportActionBar?.setTitle(title)
    }

    fun setActivityTitle(title: String) {
        supportActionBar?.setTitle(title)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //Menu
        when (item.itemId) {
            //When home is clicked
            android.R.id.home -> {
                onActionBarHomeIconClicked()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun setHomeAsUpIndicator(drawable: Drawable) {
        supportActionBar?.setHomeAsUpIndicator(drawable)
    }

    //Method for when home button is clicked
    private fun onActionBarHomeIconClicked() {
        if (isDisplayHomeAsUpEnabled) {
            onBackPressed()
        } else {
            finish()
        }
    }
}
