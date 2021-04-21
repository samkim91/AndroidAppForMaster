package kr.co.soogong.master.ui.dialog.bottomdialogrecyclerview

data class BottomDialogData(
    val text: String,
) {
    companion object {
        fun getWorkExperienceList() =
            List(60) { i -> BottomDialogData((i + 1).toString() + "년") }

        fun getServiceAreaList() =
            listOf(BottomDialogData("2km"),
                BottomDialogData("5km"),
                BottomDialogData("10km"),
                BottomDialogData("25km"),
                BottomDialogData("50km"),
                BottomDialogData("100km"),
                BottomDialogData("전국구 가능"))
    }

}

