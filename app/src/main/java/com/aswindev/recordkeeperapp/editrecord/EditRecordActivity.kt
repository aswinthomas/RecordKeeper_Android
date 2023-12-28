package com.aswindev.recordkeeperapp.editrecord

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.aswindev.recordkeeperapp.databinding.ActivityEditRecordBinding
import java.io.Serializable

const val INTENT_EXTRA_SCREEN_DATA = "screen_data"

fun <T : Serializable> Intent.intentSerializable(key: String, clazz: Class<T>): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        this.getSerializableExtra(key, clazz)
    } else {
        this.getSerializableExtra(key) as T?
    }
}

class EditRecordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditRecordBinding
    private val screenData: ScreenData by lazy {
        intent.intentSerializable(INTENT_EXTRA_SCREEN_DATA, ScreenData::class.java) as ScreenData
    }
    private val recordPreferences: SharedPreferences by lazy {
        getSharedPreferences(screenData.sharedPreferencesName, Context.MODE_PRIVATE)
    }

    private var backCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupActionBar()
        setupBackButtonCallback()
        displayRecord()
        setupButtonListeners()
        // binding.textview.text = savedInstanceState?.getString("savedMessage")
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Edit ${screenData.record} Record"
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    private fun setupBackButtonCallback() {
        onBackPressedDispatcher.addCallback(this, backCallback)
    }

    private fun displayRecord() {
        binding.textInputRecord.hint = screenData.recordFieldHint
        binding.editTextRecord.setText(recordPreferences.getString("${screenData.record} record", null))
        binding.editTextDate.setText(recordPreferences.getString("${screenData.record} date", null))
    }

    private fun setupButtonListeners() {
        binding.buttonSave.setOnClickListener {
            saveRecord()
            backCallback.handleOnBackPressed()
        }
        binding.buttonDelete.setOnClickListener {
            clearRecord()
            backCallback.handleOnBackPressed()
        }
    }

    private fun saveRecord() {
        val record = binding.editTextRecord.text.toString()
        val date = binding.editTextDate.text.toString()
        val runningPreferences = getSharedPreferences(screenData.sharedPreferencesName, Context.MODE_PRIVATE)
        runningPreferences.edit {
            putString("${screenData.record} record", record)
            putString("${screenData.record} date", date)
        }
    }

    private fun clearRecord() {
        recordPreferences.edit {
            remove("${screenData.record} record")
            remove("${screenData.record} date")
        }
    }

    // Action bar Back button pressed callback
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            backCallback.handleOnBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        //outState.putString("savedMessage", binding.textview.text.toString)
//    }

    data class ScreenData(
        val record: String,
        val sharedPreferencesName: String,
        val recordFieldHint: String
    ) : Serializable
}