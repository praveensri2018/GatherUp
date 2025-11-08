package com.praveen.gatherup.data.model

data class LoginResult(val success:Boolean, val requireOtp:Boolean=false, val token:String?=null, val message:String?=null)
