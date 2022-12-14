package kr.co.soogong.master.data.entity.common

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