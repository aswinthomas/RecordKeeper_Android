package com.aswindev.recordkeeperapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import android.window.OnBackInvokedDispatcher
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import com.aswindev.recordkeeperapp.databinding.ActivityEditCyclingRecordBinding

class EditCyclingRecordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditCyclingRecordBinding
    private var backCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditCyclingRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupActionBar()
        onBackPressedDispatcher.addCallback(this, backCallback)
    }

    private fun setupActionBar() {
        val record = intent.getStringExtra("record")
        setSupportActionBar(binding.toolbarCycling)
        supportActionBar?.title = "Edit $record Record"
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            backCallback.handleOnBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}