package com.example.locksettingconfiguration.ui.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.locksettingconfiguration.model.ApiState
import com.example.locksettingconfiguration.ui.theme.gray
import com.example.locksettingconfiguration.viewmodel.MainViewModel

@Composable
fun ProgressBar(mainViewModel: MainViewModel) {

    val showProgressBar = mainViewModel.getApiStatus().collectAsState().value == ApiState.Success

    if (showProgressBar) {
        return
    }

    Box(modifier = Modifier.fillMaxWidth().fillMaxWidth(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
           trackColor = gray
        )
    }
}