package kr.co.soogong.master.data.model.profile

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.dto.AttachmentDto

@Parcelize
data class Review(
    val id: Int?,
    val recommendation: Int?,
    val kindness: Int,
    val quality: Int,
    val affordability: Int,
    val punctuality: Int,
    val projectType: String,
    val reviewContent: String,
    val imageList: MutableList<AttachmentDto>,
    val createdAt: String,
) : Parcelable {
    companion object {


//        val TEST_REVIEW = Review(
//            1,
//            4,
//            4,
//            3,
//            4,
//            3,
//            "욕실 위생도기 및 수전 설치/교체 시공",
//            "너무 꼼꼼히 잘 해주셨어요. 다음에 또 부탁드리고 싶네요! 감사합니다.",
//            arrayListOf(
//                ImagePath.TEST_IMAGE_PATH,
//                ImagePath.TEST_IMAGE_PATH,
//                ),
//            "2020.07.01"
//        )
    }
}