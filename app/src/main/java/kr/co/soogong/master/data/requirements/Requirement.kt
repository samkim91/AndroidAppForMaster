package kr.co.soogong.master.data.requirements

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import java.util.*


@Parcelize
@Entity(tableName = "Requirement")
data class Requirement(
    @SerializedName("id")
    val id: String = UUID.randomUUID().toString(), // 관리용 키 값

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
    val status: String
) : Parcelable {
    @IgnoredOnParcel
    @Expose
    @PrimaryKey(autoGenerate = true)
    var _id: Long = 0
}