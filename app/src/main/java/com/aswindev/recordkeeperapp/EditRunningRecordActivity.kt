package com.aswindev.recordkeeperapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aswindev.recordkeeperapp.databinding.ActivityEditRunningRecordBinding

class EditRunningRecordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditRunningRecordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditRunningRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupActionBar()
    }

    private fun setupActionBar() {
        val distance = intent.getStringExtra("distance")
        setSupportActionBar(binding.toolbarRunning)
        supportActionBar?.title = "Edit $distance Record"
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }
}