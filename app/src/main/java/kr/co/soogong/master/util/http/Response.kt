package kr.co.soogong.master.util.http

import java.io.IOException

data class Response(
    val message: String,
    val code: String,
    val status: Int
)