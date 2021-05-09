package kr.co.soogong.master.data.profile

import android.os.Parcelable
import com.google.gson.JsonObject
import kotlinx.parcelize.Parcelize

@Parcelize
data class PriceByProject(
    override var masterId: String,
    override var itemId: Int?,
    override var title: String,
    override var description: String,
    var projectPrice: String,
) : Parcelable, IEditProfileWithCard {
    companion object {
        fun fromJson(jsonObject: JsonObject): PriceByProject {
            val item = jsonObject.get("data").asJsonObject
            val attributes = item.get("attributes").asJsonObject
            return PriceByProject(
                masterId = attributes.get("master_id").asString,
                itemId = attributes.get("project_id").asInt,
                title = attributes.get("project_title").asString,
                projectPrice = attributes.get("project_price").asString,
                description = attributes.get("project_description").asString,
            )
        }

        val NULL_PRICE_BY_PROJECT = PriceByProject("test", 1, "test data", "this is a test data", "1")
    }
}