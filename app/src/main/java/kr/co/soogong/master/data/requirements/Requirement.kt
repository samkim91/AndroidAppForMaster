package kr.co.soogong.master.data.requirements

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kr.co.soogong.master.data.rawtype.RawRequirementItem
import java.util.*


@Parcelize
@Entity(tableName = "Requirement")
data class Requirement(
    @Expose
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    val id: Long = 0, // 관리용 키 값

    @SerializedName("category")
    val category: String, // 카테고리

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

    @SerializedName("status")
    val status: String,

    @SerializedName("keycode")
    val keycode: String,

    @SerializedName("tel")
    val tel: String
) : Parcelable {
    companion object {
        fun fromReceived(rawData: RawRequirementItem): Requirement = Requirement(
            id = rawData.id.toLong(),
            category = rawData.attributes.category,
            location = "${rawData.attributes.area} ${rawData.attributes.location}",
            date = Date(rawData.attributes.created_at),
            userName = rawData.attributes.name,
            content = rawData.attributes.description,
            houseType = rawData.attributes.location_type,
            size = rawData.attributes.location_width,
            status = "received",
            keycode = rawData.attributes.keycode,
            tel = rawData.attributes.tel
        )

        fun fromProgress(rawData: RawRequirementItem): Requirement = Requirement(
            id = rawData.id.toLong(),
            category = rawData.attributes.category,
            location = "${rawData.attributes.area} ${rawData.attributes.location}",
            date = Date(rawData.attributes.created_at),
            userName = rawData.attributes.name,
            content = rawData.attributes.description,
            houseType = rawData.attributes.location_type,
            size = rawData.attributes.location_width,
            status = "progress",
            keycode = rawData.attributes.keycode,
            tel = rawData.attributes.tel
        )

    }
}