package kr.co.soogong.master.data.estimation

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "Estimation")
data class Estimation(
    @PrimaryKey
    @SerializedName("keycode")
    val keycode: String,

    @SerializedName("address")
    val address: String,

    @SerializedName("area")
    val area: String,

    @SerializedName("category")
    val category: String,

    @SerializedName("created_at")
    val createdAt: Long,

    @SerializedName("description")
    val description: String,

    @SerializedName("detail_address_for_app")
    val detailAddress: String,

    @SerializedName("expect_date_for_app")
    val expectDate: String,

    @SerializedName("location")
    val location: String,

    @SerializedName("project")
    val project: String,

    @SerializedName("status")
    val status: String,

    @SerializedName("status_for_app")
    val statusForApp: String,

    @SerializedName("user_assigned")
    val userAssigned: Boolean,

    @SerializedName("images")
    val images: List<ImagePath>,

    @SerializedName("transmissions")
    val transmissions: Transmissions,

    @SerializedName("additional_info")
    val additionalInfo: List<AdditionalInfo>
) : Parcelable {
    companion object {
        fun fromJson(jsonObject: JsonObject): Estimation {
            val additionalInfoJson = jsonObject.get("additional_info").asJsonArray
            val additionalInfo = mutableListOf<AdditionalInfo>()
            for (item in additionalInfoJson) {
                additionalInfo.add(AdditionalInfo.fromJson(item.asJsonObject))
            }

            val imagesJson = jsonObject.get("images").asJsonArray
            val images = mutableListOf<ImagePath>()
            for (item in imagesJson) {
                images.add(ImagePath.fromJson(item.asJsonObject))
            }

            return Estimation(
                additionalInfo = additionalInfo,
                address = jsonObject.get("address_for_app").asString,
                area = jsonObject.get("area").asString,
                category = jsonObject.get("category").asString,
                createdAt = jsonObject.get("created_at").asLong,
                description = jsonObject.get("description_for_app").asString,
                detailAddress = jsonObject.get("detail_address_for_app").asString,
                expectDate = jsonObject.get("expect_date_for_app").asString,
                images = images,
                keycode = jsonObject.get("keycode").asString,
                location = jsonObject.get("location").asString,
                project = jsonObject.get("project").asString,
                userAssigned = jsonObject.get("user_assigned").asBoolean,
                status = jsonObject.get("status").asString,
                transmissions = Transmissions.fromJson(jsonObject.get("transmissions").asJsonObject),
                statusForApp = jsonObject.get("status_for_app").asString,
            )
        }
    }
}