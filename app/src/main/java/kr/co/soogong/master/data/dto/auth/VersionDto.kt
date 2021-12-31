package kr.co.soogong.master.data.dto.auth

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class VersionDto(
    @SerializedName("version")
    val version: String,

    @SerializedName("mandatoryYn")
    val mandatoryYn: Boolean,

    ) : Parcelable, Serializable {
    companion object
}