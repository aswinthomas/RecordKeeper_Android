package com.aswindev.recordkeeperapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.aswindev.recordkeeperapp.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupActionBar()
        binding.bottomNav.setOnItemSelectedListener(this)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.reset_running -> {
            Toast.makeText(this, "Clicked the Reset Running", Toast.LENGTH_LONG).show()
            true
        }

        R.id.reset_cycling -> {
            Toast.makeText(this, "Clicked the Reset Cycling", Toast.LENGTH_LONG).show()
            true
        }

        R.id.reset_all -> {
            Toast.makeText(this, "Clicked the Reset All", Toast.LENGTH_LONG).show()
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }

    }

    private fun setupActionBar() {
        setSupportActionBar(binding.mainToolbarRunning)
        supportActionBar?.title = getString(R.string.app_name)
    }

    // return true to make onNavigationItemSelected concise
    private fun onRunningClicked(): Boolean {
        supportFragmentManager.commit {
            // cant use binding here since ID is needed
            replace(R.id.frame_content, RunningFragment())
        }
        return true
    }

    private fun onCyclingClicked(): Boolean {
        supportFragmentManager.commit {
            // cant use binding here since ID is needed
            replace(R.id.frame_content, CyclingFragment())
        }
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.nav_cycling -> onCyclingClicked()
        R.id.nav_running -> onRunningClicked()
        else -> false
    }
}


