package kr.co.soogong.master.domain.entity.common

sealed class ButtonTheme {
    object Primary : ButtonTheme()
    object Secondary : ButtonTheme()
    object Tertiary : ButtonTheme()
    object OutlinedPrimary : ButtonTheme()
    object OutlinedSecondary : ButtonTheme()
    object Kakao : ButtonTheme()
}
