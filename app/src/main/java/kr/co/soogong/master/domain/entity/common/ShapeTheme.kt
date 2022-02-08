package kr.co.soogong.master.domain.entity.common

sealed class ShapeTheme {
    object Circle : ShapeTheme()
    object Rectangle : ShapeTheme()
}
