package kr.co.soogong.master.ui.dialog.bottomsheet

import androidx.recyclerview.widget.DiffUtil

class BottomSheetDialogItemDiffUtil : DiffUtil.ItemCallback<BottomSheetDialogData>() {
    override fun areItemsTheSame(oldItem: BottomSheetDialogData, newItem: BottomSheetDialogData): Boolean {
        return oldItem.text == newItem.text
    }

    override fun areContentsTheSame(oldItem: BottomSheetDialogData, newItem: BottomSheetDialogData): Boolean {
        return oldItem == newItem
    }
}