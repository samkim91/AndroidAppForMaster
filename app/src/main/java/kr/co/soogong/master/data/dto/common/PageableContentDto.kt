package kr.co.soogong.master.data.dto.common

import com.google.gson.annotations.SerializedName


data class PageableContentDto<T>(
    @SerializedName("content")
    val content: List<T>,

    @SerializedName("pageable")
    val pageable: PageableDto,

    @SerializedName("last")
    val last: Boolean,

    @SerializedName("first")
    val first: Boolean,

    @SerializedName("empty")
    val empty: Boolean,

    @SerializedName("totalElements")
    val totalElements: Int,

    @SerializedName("totalPages")
    val totalPages: Int,
)

