package kr.co.soogong.master.data.profile

import android.os.Parcelable
import com.google.gson.JsonObject
import kotlinx.parcelize.Parcelize

@Parcelize
data class Portfolio(
    var masterId: String,
    var portfolioId: Int?,
    var jobTitle: String,
    var imageBeforeJob: String,
    var imageAfterJob: String,
    var jobDescription: String
) : Parcelable {
    companion object {
        fun fromJson(jsonObject: JsonObject): Portfolio {
            val item = jsonObject.get("data").asJsonObject
            val attributes = item.get("attributes").asJsonObject
            return Portfolio(
                masterId = attributes.get("master_id").asString,
                portfolioId = attributes.get("portfolio_id").asInt,
                jobTitle = attributes.get("job_title").asString,
                imageBeforeJob = attributes.get("image_before_job").asString,
                imageAfterJob = attributes.get("image_after_job").asString,
                jobDescription = attributes.get("job_description").asString
            )
        }
    }
}