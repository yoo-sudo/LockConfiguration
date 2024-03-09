package com.example.locksettingconfiguration.model

sealed class ApiState {
    object Success : ApiState()
    object Failure : ApiState()
    object Loading:ApiState()
    object Empty: ApiState()
}