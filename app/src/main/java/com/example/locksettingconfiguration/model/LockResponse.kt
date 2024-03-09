package com.example.locksettingconfiguration.model

import com.google.gson.annotations.SerializedName


data class LockConfig(
    @SerializedName("lockVoltage") val lockVoltage: LockSetting,
    @SerializedName("lockType") val lockType: LockSetting,
    @SerializedName("lockKick") val lockKick: LockSetting,
    @SerializedName("lockRelease") val lockRelease: LockSetting,
    @SerializedName("lockReleaseTime") val lockReleaseTime: LockSetting,
    @SerializedName("lockAngle") val lockAngle: LockSetting,
)

data class LockSetting(
    @SerializedName("values") val values: List<String>,
    @SerializedName("default") val default: String,
    @SerializedName("range") val range: Range,
    @SerializedName("common") val common: Boolean = false,
    )

data class Range(
    @SerializedName("min") val min: Double,
    @SerializedName("max") val max: Double,
)
