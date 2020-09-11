package kr.co.soogong.master.data.rawtype.user

data class Attributes(
    val address: String,
    val average_star_count: Double,
    val career: Int,
    val categories: List<String>,
    val certificates: List<Any>,
    val description: String,
    val followers_count: Int,
    val hash_tags: List<Any>,
    val images_path: ImagesPath,
    val introduction: String,
    val keycode: String,
    val name: String,
    val positions: List<String>,
    val reviews_count: Int,
    val tel: String
)