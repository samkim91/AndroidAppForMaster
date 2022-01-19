package kr.co.soogong.master.ui.requirement.list.search

val periods = listOf("7일", "30일", "90일", "전체")

fun getSearchingPeriod(selectedItem: String): Int {
    return when(selectedItem) {
        periods[0] -> 7
        periods[1] -> 30
        periods[2] -> 90
        else -> 0
    }
}