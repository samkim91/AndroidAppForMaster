package kr.co.soogong.master.network

import com.google.gson.JsonObject

data class Response(
    val message: String,
    val code: String,
    val status: Int,
    val body: JsonObject
    ){
    companion object {
        val NULL_RESPONSE = Response("", "", 0, JsonObject())
    }
}