package kr.co.soogong.master.ui.dialog.bottomdialogrecyclerview

data class BottomDialogData(
    val text: String,
) {
    companion object {
        fun getWorkExperienceList() =
            List(60) { i -> BottomDialogData((i + 1).toString() + "년") }

    }
}
