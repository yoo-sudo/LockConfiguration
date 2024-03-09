package com.example.locksettingconfiguration.ui

import Search
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.Fragment
import com.example.locksettingconfiguration.R
import com.example.locksettingconfiguration.navigation.navigateTo
import com.example.locksettingconfiguration.ui.compose.LocksParams
import com.example.locksettingconfiguration.ui.compose.ProgressBar
import com.example.locksettingconfiguration.ui.theme.LockSettingConfigurationTheme
import com.example.locksettingconfiguration.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class LockParametersFragment : Fragment() {

    private val mainViewModel: MainViewModel by sharedViewModel()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ComposeView(requireContext()).apply {
            setContent {
                LockSettingConfigurationTheme {

                    //Header
                    Column {
                        TopAppBar(title = {
                            Row(
                                Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                            ) {
                                Text(
                                    text = stringResource(id = R.string.summaru_title), style = MaterialTheme.typography.titleLarge
                                )
                            }
                        })

                        //Search Bar
                        Search(viewModel = mainViewModel) {
                            navigateTo(R.id.lockParameterFragment, R.id.editFragment)
                        }

                        //Display lock parameters
                        LocksParams(mainViewModel) {
                            navigateTo(R.id.lockParameterFragment, R.id.editFragment)
                        }
                    }

                    ProgressBar(mainViewModel)
                }
            }
        }
    }
}