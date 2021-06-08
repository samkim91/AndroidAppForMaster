package kr.co.soogong.master.data.profile

import android.os.Parcelable
import com.google.gson.JsonObject
import kotlinx.parcelize.Parcelize

@Parcelize
data class Coordinate(
    val latitude: Double,
    val longitude: Double,
) : Parcelable {
    companion object {
        fun fromJson(jsonObject: JsonObject): Coordinate {
            val item = jsonObject.get("data").asJsonObject
            val attributes = item.get("attributes").asJsonObject
            return Coordinate(
                latitude = attributes.get("latitude").asDouble,
                longitude = attributes.get("longitude").asDouble,
            )
        }

        val TEST_COORDINATE = Coordinate(37.481151, 127.041727)
        val NULL_COORDINATE = Coordinate(0.0, 0.0)
    }
}