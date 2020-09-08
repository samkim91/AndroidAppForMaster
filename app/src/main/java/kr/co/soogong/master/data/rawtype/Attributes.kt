package kr.co.soogong.master.data.rawtype

data class Attributes(
    val address: String,
    val area: String,
    val category: String,
    val contact: Any,
    val created_at: Long,
    val description: String,
    val detail_address: String,
    val expect_date: String,
    val expect_transmissions_count: Int,
    val keycode: String,
    val location: String,
    val location_type: String,
    val location_width: String,
    val name: String,
    val status: String,
    val tel: String,
    val transmissions_count: Int,
    val transmissions_list: List<Transmissions>
)