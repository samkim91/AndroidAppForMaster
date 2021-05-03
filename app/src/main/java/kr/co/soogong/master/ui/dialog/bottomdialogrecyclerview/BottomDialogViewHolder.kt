package kr.co.soogong.master.ui.dialog.bottomdialogrecyclerview

import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.databinding.ViewHolderBottomSheetDialogItemBinding

class BottomDialogViewHolder(
    private val binding: ViewHolderBottomSheetDialogItemBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun binding(
        dialogData: BottomDialogData,
        itemClickListener: (String, Int) -> Unit,
    ) {
        with(binding) {
            data = dialogData

            setItemClickLister {
                itemClickListener(dialogData.text, dialogData.value)
            }

            executePendingBindings()
        }
    }
}