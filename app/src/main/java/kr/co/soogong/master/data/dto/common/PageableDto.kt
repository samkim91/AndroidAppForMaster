package kr.co.soogong.master.data.dto.common

import com.google.gson.annotations.SerializedName


data class PageableDto(
    @SerializedName("offset")
    val offset: Int,

    @SerializedName("pageNumber")
    val pageNumber: Int,

    @SerializedName("pageSize")
    val pageSize: Int,
)
