package com.example.locksettingconfiguration.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.locksettingconfiguration.model.ApiState
import com.example.locksettingconfiguration.model.LockConfigDetail
import com.example.locksettingconfiguration.model.LockParams
import com.example.locksettingconfiguration.model.searchData
import com.example.locksettingconfiguration.repo.LockConfigRepo
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(private val lockConfigRepo: LockConfigRepo) : ViewModel() {

    private var _lockConfigDetail: LockConfigDetail? = null

    var displayedLockIndex : Int = -1

    private val _savedLockParams = MutableSharedFlow<List<LockParams>>()
    val listenToSavedParam: SharedFlow<List<LockParams>>
        get() = _savedLockParams

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    init {
        fetchDataFromDb()
    }

    private val _searchList = MutableStateFlow(searchData)
    val searchList = searchText
        .combine(_searchList) { text, lockConfigNames ->
            if (text.isBlank()) {
                lockConfigNames
            }
            lockConfigNames.filter { country ->
                country.uppercase().contains(text.trim().uppercase())
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = _searchList.value
        )


    //fun to get API status of remote response
    fun getApiStatus() = lockConfigRepo.response

    //function to get lock details
    fun getLockConfig() = lockConfigRepo.lockConfigDetails

    //function to fetch data from DB
    private fun fetchDataFromDb() = viewModelScope.launch {
        lockConfigRepo.listenChangesFromDb().collect {
            _savedLockParams.emit(it)
        }
    }

    //function to get saved params from DB
    fun getSavedLockParams() = lockConfigRepo.getSavedParams()

    //function to fetch remote lock details
    fun fetchRemoteLockDetails() = viewModelScope.launch {
        lockConfigRepo.fetchLockResponse()
    }

    //function to set data to be displayed in edit screen
    fun dataToEdit(lockConfigDetail: LockConfigDetail) {
        _lockConfigDetail = lockConfigDetail
    }

    //function to get data to be displayed in edit screen
    fun getDetailsToEdit() = _lockConfigDetail

    //function to update data to DB
    fun updateDataToDb(primarySelectedValue: String, secondarySelectedValue: String, commonValue: String) = viewModelScope.launch {
        val lockParams = LockParams(
            index = displayedLockIndex,
            primaryLockValue = primarySelectedValue,
            secondaryLockValue = secondarySelectedValue,
            commonValue = commonValue
        )
        lockConfigRepo.updateDataToDb(lockParams)
    }

    //function to toggle search view content
    fun onToggleSearch() {
        _isSearching.value = !_isSearching.value
        if (!_isSearching.value) {
            onSearchTextChange("")
        }
    }

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun setSelectedIndex(index: Int) {
        displayedLockIndex = index
    }
}