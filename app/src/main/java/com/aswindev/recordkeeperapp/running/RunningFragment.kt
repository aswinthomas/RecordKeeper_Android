package com.aswindev.recordkeeperapp.running

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aswindev.recordkeeperapp.databinding.FragmentRunningBinding
import com.aswindev.recordkeeperapp.editrecord.EditRecordActivity
import com.aswindev.recordkeeperapp.editrecord.INTENT_EXTRA_SCREEN_DATA


class RunningFragment : Fragment() {
    companion object {
        const val FILENAME = "running"
        const val DIST_5K_STR = "5km"
        const val DIST_10k_STR = "10km"
        const val DIST_HALF_MAR_STR = "Half Marathon"
        const val DIST_MAR_STR = "Marathon"
    }

    private lateinit var binding: FragmentRunningBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRunningBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
        displayRecords()
    }

    private fun setupClickListeners() {
        binding.container5km.setOnClickListener { launchRunningRecordScreen(DIST_5K_STR) }
        binding.container10km.setOnClickListener { launchRunningRecordScreen(DIST_10k_STR) }
        binding.containerHalfMarathon.setOnClickListener {
            launchRunningRecordScreen(
                DIST_HALF_MAR_STR
            )
        }
        binding.containerMarathon.setOnClickListener { launchRunningRecordScreen(DIST_MAR_STR) }
    }

    fun displayRecords() {
        val runningPreferences =
            requireContext().getSharedPreferences(FILENAME, Context.MODE_PRIVATE)

        binding.textView5kmValue.text =
            runningPreferences.getString("$DIST_5K_STR ${EditRecordActivity.RECORD_STR}", null)
        binding.textView5kmDate.text = runningPreferences.getString("$DIST_5K_STR ${EditRecordActivity.DATE_STR}", null)
        binding.textView10kmValue.text =
            runningPreferences.getString("$DIST_10k_STR ${EditRecordActivity.RECORD_STR}", null)
        binding.textView10kmDate.text =
            runningPreferences.getString("$DIST_10k_STR ${EditRecordActivity.DATE_STR}", null)
        binding.textViewHalfMarathonValue.text =
            runningPreferences.getString("$DIST_HALF_MAR_STR ${EditRecordActivity.RECORD_STR}", null)
        binding.textViewHalfMarathonDate.text =
            runningPreferences.getString("$DIST_HALF_MAR_STR ${EditRecordActivity.DATE_STR}", null)
        binding.textViewMarathonValue.text =
            runningPreferences.getString("$DIST_MAR_STR ${EditRecordActivity.RECORD_STR}", null)
        binding.textViewMarathonDate.text =
            runningPreferences.getString("$DIST_MAR_STR ${EditRecordActivity.DATE_STR}", null)
    }

    private fun launchRunningRecordScreen(distance: String) {
        val intent = Intent(context, EditRecordActivity::class.java)
        intent.putExtra(
            INTENT_EXTRA_SCREEN_DATA, EditRecordActivity.ScreenData(
                distance, FILENAME, "Time"
            )
        )
        startActivity(intent)
    }
}