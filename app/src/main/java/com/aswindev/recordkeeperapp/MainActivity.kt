package com.aswindev.recordkeeperapp

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.fragment.app.commit
import com.aswindev.recordkeeperapp.cycling.CyclingFragment
import com.aswindev.recordkeeperapp.databinding.ActivityMainBinding
import com.aswindev.recordkeeperapp.running.RunningFragment
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.snackbar.Snackbar

const val RUNNING = "running"
const val CYCLING = "cycling"
const val ALL = "all"

class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupActionBar()
        binding.bottomNav.setOnItemSelectedListener(this)
        onBackPressedDispatcher.addCallback {
            showExitDialog()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val menuClickHandled = when (item.itemId) {
            R.id.reset_running -> {
                showConfirmationDialog(RUNNING)
                true
            }

            R.id.reset_cycling -> {
                showConfirmationDialog(CYCLING)
                true
            }

            R.id.reset_all -> {
                showConfirmationDialog(ALL)
                true
            }

            else -> {
                super.onOptionsItemSelected(item)
            }
        }

        return menuClickHandled
    }

    private fun showConfirmationDialog(selection: String) {
        val dialog = AlertDialog.Builder(this)
            .setTitle("Reset $selection Records")
            .setMessage("Are you sure you want to clear $selection records?")
            .setPositiveButton("Yes") { _, _ ->
                when (selection) {
                    ALL -> {
                        getSharedPreferences(RUNNING, MODE_PRIVATE).edit { clear() }
                        getSharedPreferences(CYCLING, MODE_PRIVATE).edit { clear() }
                    }
                    else -> getSharedPreferences(selection, MODE_PRIVATE).edit { clear() }
                }
                val snackbar = Snackbar.make(binding.frameContent, "Records cleared successfully!",Snackbar.LENGTH_LONG)
                snackbar.anchorView = binding.bottomNav
                snackbar.show()
            }
            .setNegativeButton("No", null)
            .show()

        dialog.setOnDismissListener {
            when (binding.bottomNav.selectedItemId) {
                R.id.nav_running -> {
                    val fragment = supportFragmentManager.findFragmentById(R.id.frame_content) as? RunningFragment
                    fragment?.displayRecords()
                }
                R.id.nav_cycling -> {
                    val fragment = supportFragmentManager.findFragmentById(R.id.frame_content) as? CyclingFragment
                    fragment?.displayRecords()
                }
            }
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

    private fun showExitDialog() {
        AlertDialog.Builder(this)
            .setTitle("Warning!")
            .setMessage("Are you sure you want to quit?")
            .setPositiveButton("Yes") { _, _ -> finish() }
            .setNegativeButton("No") { dialog, _ -> dialog.dismiss() }
            .setNeutralButton("More Info") { _, _ ->
                Toast.makeText(
                    this,
                    "Not much info here",
                    Toast.LENGTH_LONG
                ).show()
            }
            .show()
    }
}


