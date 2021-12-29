package kr.co.soogong.master.data.common

object DropdownItems {
    val warrantyPeriods: List<Pair<String, Int>> =
        listOf(
            Pair("보증기간 없음", -1),
            Pair("직접 입력", 0),
            Pair("1년", 1),
            Pair("2년", 2),
            Pair("3년", 3),
            Pair("4년", 4),
            Pair("5년", 5),
            Pair("6년", 6),
            Pair("7년", 7),
            Pair("8년", 8),
            Pair("9년", 9),
            Pair("10년", 10),
        )

    val domains: List<Pair<String, Int>> =
        listOf(
            Pair("naver.com", 0),
            Pair("gmail.com", 1),
            Pair("kakao.com", 2),
            Pair("daum.com", 3),
            Pair("hanmail.com", 4),
            Pair("hotmail.com", 5),
            Pair("yahoo.com", 6),
            Pair("nate.com", 7),
            Pair("직접 입력", 8),
        )

    val searchingPeriods: List<Pair<String, Int>> =
        listOf(
            Pair("7일", 7),
            Pair("30일", 30),
            Pair("90일", 90),
            Pair("전체", 0),
        )
}