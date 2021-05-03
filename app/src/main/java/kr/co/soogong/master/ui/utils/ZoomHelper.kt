package kr.co.soogong.master.ui.utils

object ZoomHelper {
    operator fun invoke(radius : Int): Double {
        return when(radius) {
            2 -> 11.0
            5 -> 10.0
            10 -> 9.0
            25 -> 7.0
            50 -> 6.0
            100 -> 5.0
            else -> 4.0
        }
    }
}