package kr.co.soogong.master.ui.dialog.bottomdialogrecyclerview

import androidx.recyclerview.widget.DiffUtil

class BottomDialogItemDiffUtil : DiffUtil.ItemCallback<BottomDialogItem>() {
    override fun areItemsTheSame(oldItem: BottomDialogItem, newItem: BottomDialogItem): Boolean {
        return oldItem.key == newItem.key
    }

    override fun areContentsTheSame(oldItem: BottomDialogItem, newItem: BottomDialogItem): Boolean {
        return oldItem == newItem
    }
}