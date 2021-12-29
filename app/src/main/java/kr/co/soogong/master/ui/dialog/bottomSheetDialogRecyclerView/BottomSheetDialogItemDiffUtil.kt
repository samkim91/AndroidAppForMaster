package kr.co.soogong.master.ui.dialog.bottomSheetDialogRecyclerView

import androidx.recyclerview.widget.DiffUtil

class BottomSheetDialogItemDiffUtil : DiffUtil.ItemCallback<BottomSheetDialogItem>() {
    override fun areItemsTheSame(
        oldItemSheet: BottomSheetDialogItem,
        newItemSheet: BottomSheetDialogItem,
    ): Boolean {
        return oldItemSheet.key == newItemSheet.key
    }

    override fun areContentsTheSame(
        oldItemSheet: BottomSheetDialogItem,
        newItemSheet: BottomSheetDialogItem,
    ): Boolean {
        return oldItemSheet == newItemSheet
    }
}