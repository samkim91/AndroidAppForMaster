package kr.co.soogong.master.data.profile

import android.os.Parcelable
import com.google.gson.JsonObject
import kotlinx.parcelize.Parcelize

@Parcelize
data class Portfolio(
    override var itemId: Int?,
    override var title: String,
    override var description: String,
    var imageBeforeJob: String,
    var imageAfterJob: String,
) : Parcelable, IEditProfileWithCard {
    companion object {
        fun fromJson(jsonObject: JsonObject): Portfolio {
            val item = jsonObject.get("data").asJsonObject
            val attributes = item.get("attributes").asJsonObject
            return Portfolio(
                itemId = attributes.get("portfolio_id").asInt,
                title = attributes.get("job_title").asString,
                imageBeforeJob = attributes.get("image_before_job").asString,
                imageAfterJob = attributes.get("image_after_job").asString,
                description = attributes.get("job_description").asString
            )
        }

        val TEST_PORTFOLIO = Portfolio(1, "test data", "this is a test data", "", "")
        val NULL_PORTFOLIO = Portfolio(0, "", "", "", "")
    }
}