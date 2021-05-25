package kr.co.soogong.master.ui.utils

object ZoomHelper {
    operator fun invoke(radius : Double): Double {
        // 서비스 지역이 지름으로 2, 5, 10, 25, 50, 100, 500 인데 파라미터가 반지름으로 들어오기 때문에
        // 대충 값을 잡아준 것... 범위는 아무런 의미가 없다.
        return when(radius) {
            1.0 -> 12.0
            2.5 -> 11.0
            5.0 -> 10.0
            12.5 -> 8.0
            25.0 -> 7.0
            50.0 -> 6.0
            500.0 -> 4.0
            else -> 3.0
        }
    }
}