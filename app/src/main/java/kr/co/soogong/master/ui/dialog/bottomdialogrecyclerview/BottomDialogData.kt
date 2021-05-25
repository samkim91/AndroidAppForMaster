package kr.co.soogong.master.ui.dialog.bottomdialogrecyclerview

data class BottomDialogData(
    val text: String,
    val value: Int
) {
    companion object {
        fun getWorkExperienceList() =
            List(60) { i -> BottomDialogData(text = "${i + 1}년", value = i + 1) }

        fun getServiceAreaList() =
            listOf(
                BottomDialogData("2km", 2),
                BottomDialogData("5km", 5),
                BottomDialogData("10km", 10),
                BottomDialogData("25km", 25),
                BottomDialogData("50km", 50),
                BottomDialogData("100km", 100),
                BottomDialogData("전국구 가능", 1000)
            )

        fun getWarrantyPeriodList() =
            listOf(
                BottomDialogData("1년", 1),
                BottomDialogData("2년", 2),
                BottomDialogData("3년", 3),
                BottomDialogData("4년", 4),
                BottomDialogData("5년", 5),
                BottomDialogData("6년", 6),
                BottomDialogData("7년", 7),
                BottomDialogData("8년", 8),
                BottomDialogData("9년", 9),
                BottomDialogData("10년", 10),
            )
    }
}

