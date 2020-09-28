package kr.co.soogong.master.ui.settings.notice

data class Notice(
    val id: String,
    val date: String,
    val title: String,
    val content: String,
    val isNew: Boolean
)