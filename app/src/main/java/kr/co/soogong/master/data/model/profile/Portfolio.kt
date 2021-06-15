package kr.co.soogong.master.data.model.profile

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Portfolio(
    override val id: Int,
    override val title: String,
    override val description: String,
    override val project: String?,
    override val type: String,
    override val createdAt: Date?,
    override val updatedAt: Date?,
    val imageBeforeJob: String,
    val imageAfterJob: String,
) : Parcelable, IEditProfileWithCard {
    companion object {
//        fun fromJson(jsonObject: JsonObject): Portfolio {
//            val item = jsonObject.get("data").asJsonObject
//            val attributes = item.get("attributes").asJsonObject
//            return Portfolio(
//                id = attributes.get("portfolio_id").asInt,
//                title = attributes.get("job_title").asString,
//                imageBeforeJob = attributes.get("image_before_job").asString,
//                imageAfterJob = attributes.get("image_after_job").asString,
//                description = attributes.get("job_description").asString
//            )
//        }
//
//        val TEST_PORTFOLIO = Portfolio(1, "test data", "this is a test data", "", "")
//        val NULL_PORTFOLIO = Portfolio(0, "", "", "", "")
    }
}