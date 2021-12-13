package kr.co.soogong.master.data.dto.common

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CodeDto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("groupCode")
    val groupCode: String,

    @SerializedName("groupName")
    val groupName: String,

    @SerializedName("code")
    val code: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("description")
    val description: String,
) : Parcelable {
    companion object {

    }
}

//"id": 78,
//"groupCode": "CanceledReasonOfCommon",
//"groupName": "공통 취소사유",
//"code": "ImpossibleRepair",
//"name": "시공 불가",
//"description": "시공 불가로 인한 취소"

