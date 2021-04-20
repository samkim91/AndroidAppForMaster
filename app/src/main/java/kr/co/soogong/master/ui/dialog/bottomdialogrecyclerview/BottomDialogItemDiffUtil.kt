package kr.co.soogong.master.ui.dialog.bottomdialogrecyclerview

import androidx.recyclerview.widget.DiffUtil

class BottomDialogItemDiffUtil : DiffUtil.ItemCallback<BottomDialogData>() {
    override fun areItemsTheSame(oldItem: BottomDialogData, newItem: BottomDialogData): Boolean {
        return oldItem.text == newItem.text
    }

    override fun areContentsTheSame(oldItem: BottomDialogData, newItem: BottomDialogData): Boolean {
        return oldItem == newItem
    }
}