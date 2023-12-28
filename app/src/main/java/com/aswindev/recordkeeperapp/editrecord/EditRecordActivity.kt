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
    companion object {
        const val RECORD_STR = "record"
        const val DATE_STR = "date"
    }
    private lateinit var binding: ActivityEditRecordBinding
    private val screenData: ScreenData by lazy {
        intent.intentSerializable(INTENT_EXTRA_SCREEN_DATA, ScreenData::class.java) as ScreenData
    }
    private val recordPreferences: SharedPreferences by lazy {
        getSharedPreferences(screenData.sharedPreferencesName, Context.MODE_PRIVATE)
    }

//    private var backCallback = object : OnBackPressedCallback(true) {
//        override fun handleOnBackPressed() {
//            finish()
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupActionBar()
        //setupBackButtonCallback()
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
        //onBackPressedDispatcher.addCallback(this, backCallback)
    }

    private fun displayRecord() {
        binding.textInputRecord.hint = screenData.recordFieldHint
        binding.editTextRecord.setText(recordPreferences.getString("${screenData.record} $RECORD_STR", null))
        binding.editTextDate.setText(recordPreferences.getString("${screenData.record} $DATE_STR", null))
    }

    private fun setupButtonListeners() {
        binding.buttonSave.setOnClickListener {
            saveRecord()
            finish()
            //backCallback.handleOnBackPressed()
        }
        binding.buttonDelete.setOnClickListener {
            clearRecord()
            finish()
            //backCallback.handleOnBackPressed()
        }
    }

    private fun saveRecord() {
        val record = binding.editTextRecord.text.toString()
        val date = binding.editTextDate.text.toString()
        val runningPreferences = getSharedPreferences(screenData.sharedPreferencesName, Context.MODE_PRIVATE)
        runningPreferences.edit {
            putString("${screenData.record} $RECORD_STR", record)
            putString("${screenData.record} $DATE_STR", date)
        }
    }

    private fun clearRecord() {
        recordPreferences.edit {
            remove("${screenData.record} $RECORD_STR")
            remove("${screenData.record} $DATE_STR")
        }
    }

    // Action bar Back button pressed callback. Not needed once 'SingleTop' is set for Main.Activity
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        if (item.itemId == android.R.id.home) {
//            backCallback.handleOnBackPressed()
//            return true
//        }
//        return super.onOptionsItemSelected(item)
//    }

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