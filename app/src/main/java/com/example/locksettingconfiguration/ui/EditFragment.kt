package com.example.locksettingconfiguration.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.example.locksettingconfiguration.R
import com.example.locksettingconfiguration.navigation.navigateBack
import com.example.locksettingconfiguration.ui.compose.EditScreen
import com.example.locksettingconfiguration.ui.theme.LockSettingConfigurationTheme
import com.example.locksettingconfiguration.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class EditFragment : Fragment() {

    private val mainViewModel: MainViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                LockSettingConfigurationTheme {
                    EditScreen(mainViewModel = mainViewModel) {
                        //If search is active, close the view on back press
                        if (mainViewModel.isSearching.value) {
                            mainViewModel.onToggleSearch()
                        }
                        //Navigate back to lock parameters fragment
                        navigateBack(R.id.editFragment, true)
                    }
                }
            }
        }
    }
}