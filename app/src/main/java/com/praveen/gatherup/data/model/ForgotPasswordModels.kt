package com.praveen.gatherup.data.model
import com.google.gson.annotations.SerializedName

data class SendOtpRequest(
    @SerializedName("mobile_or_email")
    val mobile: String
)


data class SendOtpResponse(
    val success: Boolean,
    val error: String? = null
)

data class VerifyOtpRequest(
    @SerializedName("mobile_or_email")
    val mobile: String,
    val otp: String
)

data class VerifyOtpResponse(
    val success: Boolean,
    val error: String? = null
)

data class ResetPasswordRequest(
    @SerializedName("mobile_or_email")
    val mobile: String,
    @SerializedName("new_password")
    val newPassword: String
)

data class SimpleApiResponse(
    val success: Boolean,
    val error: String? = null
)