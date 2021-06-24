package kr.co.soogong.master.data.model.profile

import android.os.Parcelable
import com.google.gson.JsonObject
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.dto.profile.MasterDto

@Parcelize
data class  MyReview(
    val averageRecommendation: Double,
    val averageKindness: Double,
    val averageQuality: Double,
    val averageAffordability: Double,
    val averagePunctuality: Double,
    val reviews: List<Review> = emptyList(),
) : Parcelable {
    companion object {
//        fun fromMasterDto(masterDto: MasterDto): MyReview {
//
//        }


//        val TEST_MY_REVIEW = MyReview(
//            4.8,
//            4.9,
//            4.6,
//            4.8,
//            4.8,
//            listOf(Review.TEST_REVIEW, Review.TEST_REVIEW, Review.TEST_REVIEW)
//        )
//
//        val NULL_MY_REVIEW = MyReview(
//            0.0, 0.0, 0.0, 0.0, 0.0, emptyList()
//        )
    }
}