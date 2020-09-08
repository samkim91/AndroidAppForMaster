package kr.co.soogong.master.data.rawtype

data class AttributesX(
    val accept_count: Int,
    val completed_at: Any,
    val refusal_count: Int,
    val status: String,
    val transmissions: List<Transmission>,
    val transmissions_count: Int
)