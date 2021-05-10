package kr.co.soogong.master.data.profile

import android.os.Parcelable
import com.google.gson.JsonObject
import kotlinx.parcelize.Parcelize

@Parcelize
data class OtherFlexibleOptions(
    val options: List<String>,
) : Parcelable {
    companion object {
        fun fromJson(jsonObject: JsonObject): OtherFlexibleOptions {
            val item = jsonObject.get("data").asJsonObject
            val attributes = item.get("attributes").asJsonArray

            val temp = mutableListOf<String>()

            attributes.map {
                temp.add(it.asString)
            }

            return OtherFlexibleOptions(
                options = temp
            )
        }

        val NULL_OTHER_FLEXIBLE_OPTIONS = OtherFlexibleOptions(listOf("마스크 착용", "엘리베이터 보양작업", "약속시간 준수"))
    }
}