package com.aswindev.recordkeeperapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aswindev.recordkeeperapp.databinding.FragmentCyclingBinding

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

    private fun setupClickListeners() {
        binding.containerLongestRide.setOnClickListener { launchCyclingRecordScreen("Longest Ride") }
        binding.containerBiggestClimb.setOnClickListener { launchCyclingRecordScreen("Biggest Climb") }
        binding.containerBestAvgSpeed.setOnClickListener { launchCyclingRecordScreen("Best Average Speed") }
    }

    private fun launchCyclingRecordScreen(record: String) {
        val intent = Intent(context, EditCyclingRecordActivity::class.java)
        intent.putExtra("record", record)
        startActivity(intent)
    }
}