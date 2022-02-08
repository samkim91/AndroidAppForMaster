package kr.co.soogong.master.data.entity.common

import com.google.gson.annotations.SerializedName


data class PageableContentDto<T>(
    @SerializedName("content")
    val content: List<T>,

    @SerializedName("pageable")
    val pageable: PageableDto,

    @SerializedName("last")
    var last: Boolean,

    @SerializedName("numberOfElements")
    var numberOfElements: Int,
)

