package kr.co.soogong.master.data.model.profile

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.dto.profile.PortfolioDto
import java.util.*

@Parcelize
data class PriceByProject(
    override val id: Int,
    override val title: String,
    override val description: String,
    override val project: String?,
    override val type: String,
    override val createdAt: Date?,
    override val updatedAt: Date?,
    val price: String,
) : Parcelable, IEditProfileWithCard {
    companion object {
        fun fromPortfolioDto(list: List<PortfolioDto>?): List<PriceByProject> {
            val ret = mutableListOf<PriceByProject>()
            list?.map { portfolioDto ->
                ret.add(
                    PriceByProject(
                        id = portfolioDto.id,
                        title = portfolioDto.title,
                        description = portfolioDto.description,
                        project = portfolioDto.project,
                        type = portfolioDto.type,
                        createdAt = portfolioDto.createdAt,
                        updatedAt = portfolioDto.updatedAt,
                        price = portfolioDto.price,
                    )
                )
            }

            return ret
        }

//        fun fromJson(jsonObject: JsonObject): PriceByProject {
//            val item = jsonObject.get("data").asJsonObject
//            val attributes = item.get("attributes").asJsonObject
//            return PriceByProject(
//                itemId = attributes.get("project_id").asInt,
//                title = attributes.get("project_title").asString,
//                projectPrice = attributes.get("project_price").asString,
//                description = attributes.get("project_description").asString,
//            )
//        }
//
//        val TEST_PRICE_BY_PROJECT = PriceByProject(1, "test data", "this is a test data", "1")
//        val NULL_PRICE_BY_PROJECT = PriceByProject(0, "", "", "")
    }
}