package com.github.vipulasri.timelineview.sample

import android.graphics.drawable.Drawable
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.github.vipulasri.timelineview.sample.example.whenNotNull

open class BaseActivity : AppCompatActivity() {

    var toolbar: Toolbar? = null

    //If back button is displayed in action bar, return false
    protected var isDisplayHomeAsUpEnabled: Boolean
        get() = false
        set(value) {
            whenNotNull(supportActionBar) {
                it.setDisplayHomeAsUpEnabled(value)
            }

        }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)

        injectViews()

        //Displaying the back button in the action bar
        if (isDisplayHomeAsUpEnabled) {
            whenNotNull(supportActionBar) {
                it.setDisplayHomeAsUpEnabled(true)
            }
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
        whenNotNull(toolbar) {
            setSupportActionBar(it)
        }
    }

    fun setActivityTitle(title: Int) {
        whenNotNull(supportActionBar) {
            it.setTitle(title)
        }
    }

    fun setActivityTitle(title: String) {
        whenNotNull(supportActionBar) {
            it.setTitle(title)
        }
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
        whenNotNull(supportActionBar) {
            it.setHomeAsUpIndicator(drawable)
        }
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
