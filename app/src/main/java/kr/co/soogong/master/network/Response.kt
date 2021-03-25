package kr.co.soogong.master.network

import com.google.gson.JsonObject

data class Response(
    val message: String,
    val code: String,
    val status: Int,
    val body: JsonObject
)