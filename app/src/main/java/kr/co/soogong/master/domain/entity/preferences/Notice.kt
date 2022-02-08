package kr.co.soogong.master.domain.entity.preferences

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.entity.preferences.NoticeDto
import java.util.*

@Parcelize
data class Notice(
    val id: Int,
    val section: String,
    val title: String,
    val content: String,
    val hitCount: Int,
    val isNew: Boolean,
    val createdBy: String,
    val createdAt: Date,
    val updatedBy: String,
    val updatedAt: Date,
) : Parcelable {
    companion object {
        fun fromNoticeDto(noticeDto: NoticeDto): Notice = Notice(
            id = noticeDto.id,
            section = noticeDto.section,
            title = noticeDto.title,
            content = noticeDto.content,
            hitCount = noticeDto.hitCount,
            isNew = noticeDto.isNew ?: true,
            createdBy = noticeDto.createdBy,
            createdAt = noticeDto.createdAt,
            updatedBy = noticeDto.updatedBy,
            updatedAt = noticeDto.updatedAt,
        )
    }
}
