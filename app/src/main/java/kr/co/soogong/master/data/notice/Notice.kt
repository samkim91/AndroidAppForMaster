package kr.co.soogong.master.data.notice

import android.os.Parcelable
import com.google.gson.JsonObject
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Notice(
    val id: String,
    val date: Date,
    val title: String,
    val content: String,
    val isNew: Boolean
) : Parcelable {
    companion object {
        fun from(jsonObject: JsonObject): Notice {
            val item = jsonObject.get("attributes").asJsonObject
            return Notice(
                id = jsonObject.get("id").asString,
                date = Date(item.get("created_at").asLong),
                content = item.get("contents").asString,
                title = item.get("title").asString,
                isNew = item.get("is_new").asBoolean
            )
        }

        val NULL_OBJECT = Notice(
            id = "",
            date = Date(),
            title = "",
            content = "",
            isNew = true
        )
    }
}