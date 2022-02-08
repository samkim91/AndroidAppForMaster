package kr.co.soogong.master.data.entity.common

import com.google.gson.annotations.SerializedName


data class PageableDto(
    @SerializedName("offset")
    var offset: Int,

    @SerializedName("pageNumber")
    var pageNumber: Int,

    @SerializedName("pageSize")
    val pageSize: Int,
)
