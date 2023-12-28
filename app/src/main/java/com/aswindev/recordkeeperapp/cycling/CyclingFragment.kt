package com.aswindev.recordkeeperapp.cycling

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aswindev.recordkeeperapp.databinding.FragmentCyclingBinding
import com.aswindev.recordkeeperapp.editrecord.EditRecordActivity
import com.aswindev.recordkeeperapp.editrecord.INTENT_EXTRA_SCREEN_DATA


class CyclingFragment : Fragment() {
    companion object {
        const val FILENAME = "cycling"
        const val RIDE_STR = "Longest Ride"
        const val CLIMB_STR = "Biggest Climb"
        const val AVG_SPEED_STR = "Best Average Speed"
    }

    private lateinit var binding: FragmentCyclingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCyclingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
    }

    override fun onResume() {
        super.onResume()
        displayRecords()
    }

    private fun setupClickListeners() {
        binding.containerLongestRide.setOnClickListener {
            launchCyclingRecordScreen(RIDE_STR, "Distance")
        }
        binding.containerBiggestClimb.setOnClickListener {
            launchCyclingRecordScreen(CLIMB_STR, "Height")
        }
        binding.containerBestAvgSpeed.setOnClickListener {
            launchCyclingRecordScreen(AVG_SPEED_STR, "Average Speed")
        }
    }

    fun displayRecords() {
        val cyclingPreferences =
            requireContext().getSharedPreferences(FILENAME, Context.MODE_PRIVATE)

        binding.textViewLongestRideValue.text =
            cyclingPreferences.getString("$RIDE_STR ${EditRecordActivity.RECORD_STR}", null)
        binding.textViewLongestRideDate.text =
            cyclingPreferences.getString("$RIDE_STR ${EditRecordActivity.DATE_STR}", null)
        binding.textViewBiggestClimbValue.text =
            cyclingPreferences.getString("$CLIMB_STR ${EditRecordActivity.RECORD_STR}", null)
        binding.textViewBiggestClimbDate.text =
            cyclingPreferences.getString("$CLIMB_STR ${EditRecordActivity.DATE_STR}", null)
        binding.textViewBestAvgSpeedValue.text =
            cyclingPreferences.getString("$AVG_SPEED_STR ${EditRecordActivity.RECORD_STR}", null)
        binding.textViewBestAvgSpeedDate.text =
            cyclingPreferences.getString("$AVG_SPEED_STR ${EditRecordActivity.DATE_STR}", null)
    }

    private fun launchCyclingRecordScreen(record: String, recordFieldHint: String) {
        val intent = Intent(context, EditRecordActivity::class.java)
        intent.putExtra(
            INTENT_EXTRA_SCREEN_DATA,
            EditRecordActivity.ScreenData(record, FILENAME, recordFieldHint)
        )
        startActivity(intent)
    }
}