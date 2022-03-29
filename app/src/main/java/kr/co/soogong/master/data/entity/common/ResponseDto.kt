package kr.co.soogong.master.data.entity.common

import com.google.gson.annotations.SerializedName
import java.net.HttpURLConnection


data class ResponseDto<T>(
    @SerializedName("code")
    val code: String,

    @SerializedName("message")
    val message: String,

    @SerializedName("messageKo")
    val messageKo: String,

    @SerializedName("data")
    val data: T?,
) {
    companion object {
        val NULL_RESPONSE = ResponseDto("", "", "", "")
        val TEST_RESPONSE = ResponseDto("200", "Requested Successfully", "성공", "안뇽")
    }
}

fun <T> ResponseDto<T>.verify(): T? {
    return if (this.code.toInt() == HttpURLConnection.HTTP_OK) {
        this.data
    } else {
        throw Exception(this.messageKo)
    }
}