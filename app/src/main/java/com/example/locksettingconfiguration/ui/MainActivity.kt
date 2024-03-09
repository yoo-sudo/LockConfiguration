package com.example.locksettingconfiguration.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.locksettingconfiguration.databinding.ActivityMainBinding
import com.example.locksettingconfiguration.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Fetch lock details from remote
        mainViewModel.fetchRemoteLockDetails()
    }
}
