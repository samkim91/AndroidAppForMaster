package kr.co.soogong.master.ui.dialog.bottomdialogrecyclerview

data class BottomDialogData(
    val text: String,
    val value: Int
) {
    companion object {
        fun getWorkExperienceList() =
            List(60) { i -> BottomDialogData(text = (i + 1).toString() + "년", value = i + 1) }

        fun getServiceAreaList() =
            listOf(BottomDialogData("2km", 2),
                BottomDialogData("5km", 5),
                BottomDialogData("10km", 10),
                BottomDialogData("25km", 25),
                BottomDialogData("50km", 50),
                BottomDialogData("100km", 100),
                BottomDialogData("전국구 가능", 500))
    }
}

