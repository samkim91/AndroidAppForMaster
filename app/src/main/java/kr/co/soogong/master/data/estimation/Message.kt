package kr.co.soogong.master.data.estimation

data class Message(
    val contents: String,
    val priceDetail: List<PriceDetail>,
    val priceInNumber: Int,
    val status: String
)