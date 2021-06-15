package kr.co.soogong.master.data.model.profile

import android.os.Parcelable
import com.google.gson.JsonObject
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.dto.profile.MasterConfigDto

@Parcelize
data class OtherFlexibleOptions(
    val options: List<String>,
) : Parcelable {
    companion object {
        fun fromJson(jsonObject: JsonObject): OtherFlexibleOptions {
            val item = jsonObject.get("data").asJsonObject

            return OtherFlexibleOptions(
                options = item.get("otherFlexibleOptions").asJsonArray.map { it.asString }
            )
        }

        fun fromMasterConfigList(list: List<MasterConfigDto>?): OtherFlexibleOptions {
            val ret = mutableListOf<String>()
            list?.map { masterConfigDto ->
                masterConfigDto.value?.let {
                    ret.add(it)
                }
            }
            return OtherFlexibleOptions(
                options = ret
            )
        }

        val TEST_OTHER_FLEXIBLE_OPTIONS =
            OtherFlexibleOptions(listOf("마스크 착용", "엘리베이터 보양작업", "약속시간 준수"))
        val NULL_OTHER_FLEXIBLE_OPTIONS = OtherFlexibleOptions(emptyList())
    }
}