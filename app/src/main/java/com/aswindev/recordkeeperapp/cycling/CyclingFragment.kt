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

class CyclingFragment : Fragment() {
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
            launchCyclingRecordScreen(
                "Longest Ride",
                "Distance"
            )
        }
        binding.containerBiggestClimb.setOnClickListener {
            launchCyclingRecordScreen(
                "Biggest Climb",
                "Height"
            )
        }
        binding.containerBestAvgSpeed.setOnClickListener {
            launchCyclingRecordScreen(
                "Best Average Speed",
                "Average Speed"
            )
        }
    }

    private fun displayRecords() {
        val runningPreferences =
            requireContext().getSharedPreferences("running", Context.MODE_PRIVATE)

        binding.textViewLongestRideValue.text =
            runningPreferences.getString("Longest Ride record", null)
        binding.textViewLongestRideDate.text =
            runningPreferences.getString("Longest Ride date", null)
        binding.textViewBiggestClimbValue.text =
            runningPreferences.getString("Biggest Climb record", null)
        binding.textViewBiggestClimbDate.text =
            runningPreferences.getString("Biggest Climb date", null)
        binding.textViewBestAvgSpeedValue.text =
            runningPreferences.getString("Best Average Speed record", null)
        binding.textViewBestAvgSpeedDate.text =
            runningPreferences.getString("Best Average Speed date", null)
    }

    private fun launchCyclingRecordScreen(record: String, recordFieldHint: String) {
        val intent = Intent(context, EditRecordActivity::class.java)
        intent.putExtra("data", EditRecordActivity.ScreenData(record, "cycling", recordFieldHint))
        startActivity(intent)
    }
}