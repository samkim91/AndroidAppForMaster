package kr.co.soogong.master.data.global

sealed class ButtonTheme {
    object Primary : ButtonTheme()
    object Secondary : ButtonTheme()
    object Tertiary : ButtonTheme()
    object OutlinedPrimary : ButtonTheme()
    object OutlinedSecondary : ButtonTheme()
    object Kakao : ButtonTheme()
}
