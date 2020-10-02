package kr.co.soogong.master.data.notice

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Notice(
    val id: String,
    val date: String,
    val title: String,
    val content: String,
    val isNew: Boolean
) : Parcelable {
    companion object {
        val NULL_OBJECT = Notice(
            id = "",
            date = "",
            title = "",
            content = "",
            isNew = true
        )
    }
}