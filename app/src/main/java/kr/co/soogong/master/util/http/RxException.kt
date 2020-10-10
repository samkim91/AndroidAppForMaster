package kr.co.soogong.master.util.http

import com.google.gson.JsonObject

class RxException(
    override val message: String,
    val data: JsonObject? = null,
    throwable: Exception? = null
) : RuntimeException(throwable) {
    override fun toString(): String {
        return message
    }
}