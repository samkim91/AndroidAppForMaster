package kr.co.soogong.master.data.model.mypage

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import kr.co.soogong.master.data.dto.mypage.NoticeDto
import java.util.*

@Parcelize
@Entity(tableName = "Notice")
data class Notice(
    @PrimaryKey
    val id: Int,
    val title: String,
    val content: String,
    val isNew: Boolean,
    val date: Date,
) : Parcelable {
    companion object {
        fun fromNoticeDto(noticeDto: NoticeDto): Notice {
            return Notice(
                id = noticeDto.id,
                title = noticeDto.title,
                content = noticeDto.content,
                isNew = true,
                date = noticeDto.createdAt,
            )
        }
    }
}