package kr.co.soogong.master.data.dto

import com.google.gson.JsonObject

data class Response(
    val message: String,
    val code: String,
    val status: Int,
    val body: JsonObject
    ){
    companion object {
        val NULL_RESPONSE = Response("", "", 0, JsonObject())
        val TEST_RESPONSE = Response("Requested Successfully", "200", 200, JsonObject())
    }
}