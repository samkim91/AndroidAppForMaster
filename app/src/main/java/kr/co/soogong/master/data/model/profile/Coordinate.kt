package kr.co.soogong.master.data.model.profile

import android.os.Parcelable
import com.google.gson.JsonObject
import kotlinx.parcelize.Parcelize

@Parcelize
data class Coordinate(
    val latitude: Double?,
    val longitude: Double?,
) : Parcelable {
    companion object {
        val TEST_COORDINATE = Coordinate(37.481151, 127.041727)
        val NULL_COORDINATE = Coordinate(0.0, 0.0)
    }
}