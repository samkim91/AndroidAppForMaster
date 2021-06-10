package kr.co.soogong.master.utility

object ZoomHelper {
    // 서비스 범위에 따른 줌 수준을 반환
    operator fun invoke(radius : Int): Double {
        return when(radius) {
            0, 2 -> 11.0
            5 -> 10.0
            10 -> 9.0
            25 -> 7.0
            50 -> 6.0
            100 -> 5.0
            500 -> 3.0
            else -> 2.0
        }
    }
}