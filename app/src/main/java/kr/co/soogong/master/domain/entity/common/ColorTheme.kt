package kr.co.soogong.master.domain.entity.common

sealed class ColorTheme {
    object Blue : ColorTheme()
    object Grey : ColorTheme()
    object Red : ColorTheme()
    object Green : ColorTheme()
}
