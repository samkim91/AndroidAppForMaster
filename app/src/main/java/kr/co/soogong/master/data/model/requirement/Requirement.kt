package kr.co.soogong.master.data.model.requirement

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.utility.extension.getNullable
import java.util.*

@Parcelize
@Entity(tableName = "Requirement")
data class Requirement(
    @PrimaryKey
    @SerializedName("keycode")
    val keycode: String,

    @SerializedName("category")
    val category: String, // 카테고리

    @SerializedName("project")
    val project: String, // 프로젝트명

    @SerializedName("location")
    val location: String, // 위치

    @SerializedName("date")
    val date: Date, // 접수일시

    @SerializedName("userName")
    val userName: String, // 요청 고객명

    @SerializedName("content")
    val content: String, // 요청사항,

    @SerializedName("house_type")
    val houseType: String, // 집 유형

    @SerializedName("size")
    val size: String, // 평수

    @SerializedName("image_array")
    val image: String? = null, // 이미지

    @SerializedName("questions")
    val questions: String, // 질문들

    @SerializedName("status")
    val status: String,

    @SerializedName("tel")
    val tel: String,

    @SerializedName("estimate")
    val estimationMessage: EstimationMessage? = null
) : Parcelable {
    companion object {
        fun fromJson(jsonObject: JsonObject, status: String): Requirement {
            val data = jsonObject.get("attributes").asJsonObject

            return if (status == "received") {
                Requirement(
                    category = data.get("category").asString,
                    project = data.getNullable("project")?.asString ?: "",
                    location = "${data.get("area").asString} ${data.get("location").asString}",
                    date = Date(data.get("created_at").asLong),
                    userName = data.getNullable("name")?.asString ?: "고객",
                    content = data.get("description").asString,
                    houseType = data.get("location_type").asString,
                    size = data.get("location_width").asString,
                    status = status,
                    keycode = data.get("keycode").asString,
                    tel = data.get("tel").asString,
                    questions = data.getNullable("questions")?.asString ?: ""
                )
            } else {
                val item = data.get("transmissions_list").asJsonArray[0].asJsonObject
                    .get("data").asJsonObject
                    .get("attributes").asJsonObject
                    .get("transmissions").asJsonArray[0].asJsonObject
                    .get("message").asJsonObject

                Requirement(
                    category = data.get("category").asString,
                    project = data.get("project").asString,
                    location = "${data.get("area").asString} ${data.get("location").asString}",
                    date = Date(data.get("created_at").asLong),
                    userName = data.getNullable("name")?.asString ?: "고객",
                    content = data.get("description").asString,
                    houseType = data.get("location_type").asString,
                    size = data.get("location_width").asString,
                    status = status,
                    keycode = data.get("keycode").asString,
                    tel = data.get("tel").asString,
                    questions = data.get("questions").asString,
                    estimationMessage = EstimationMessage(
                        totalPrice = item.getNullable("price")?.asString,
                        personnel = item.getNullable("personnel")?.asString,
                        material = item.getNullable("material")?.asString,
                        trip = item.getNullable("trip")?.asString,
                        description = item.getNullable("contents")?.asString
                    )
                )
            }
        }
    }
}