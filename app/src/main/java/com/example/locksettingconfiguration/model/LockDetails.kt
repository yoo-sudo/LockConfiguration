package com.example.locksettingconfiguration.model

data class LockConfigDetail(
    val lockName: String,
    val primaryLockValue: String,
    val secondaryLockValue: String,
    val primaryLockValues: List<String>,
    val secondaryLockValues: List<String>,
    val lockRange: LockRange? = null,
    val common: Boolean = false
)

data class LockRange(
    val min: Double,
    val max: Double,
)

val searchData = listOf("Lock Voltage", "Lock Type", "Lock Kick", "Lock Release", "Lock Release Time", "Lock Angle")