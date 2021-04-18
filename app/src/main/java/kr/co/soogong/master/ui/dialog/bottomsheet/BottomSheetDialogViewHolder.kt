package kr.co.soogong.master.ui.dialog.bottomsheet

import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.databinding.ViewHolderBottomSheetDialogItemBinding

class BottomSheetDialogViewHolder(
    private val binding: ViewHolderBottomSheetDialogItemBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun binding(
        dialogData: BottomSheetDialogData,
        itemClickListener: (String) -> Unit,
    ) {
        with(binding) {
            data = dialogData

            setItemClickLister {
                itemClickListener(dialogData.text)
            }

            executePendingBindings()
        }
    }
}