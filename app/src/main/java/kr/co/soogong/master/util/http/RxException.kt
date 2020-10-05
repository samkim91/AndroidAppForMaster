package kr.co.soogong.master.util.http

class RxException(
    override val message: String,
    throwable: Exception? = null
) : RuntimeException(throwable) {
    override fun toString(): String {
        return message
    }
}