package kr.co.soogong.master.ui.dialog.bottomdialogrecyclerview

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.core.view.updateMargins
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ViewHolderBottomSheetDialogItemBinding
import kr.co.soogong.master.utility.extension.dp

abstract class BottomDialogViewHolder(
    open val binding: ViewHolderBottomSheetDialogItemBinding,
) : RecyclerView.ViewHolder(binding.root) {
    open fun binding(
        dialogData: BottomDialogItem,
        itemClickListener: (String, Int) -> Unit,
    ) {
        with(binding) {
            data = dialogData

            setItemClickLister {
                itemClickListener(dialogData.key, dialogData.value)
            }

            executePendingBindings()
        }
    }
}

class SimpleBottomDialogViewHolder(
    override val binding: ViewHolderBottomSheetDialogItemBinding,
) : BottomDialogViewHolder(binding) {

}

class IconBottomDialogViewHolder(
    override val binding: ViewHolderBottomSheetDialogItemBinding,
) : BottomDialogViewHolder(binding) {
    override fun binding(
        dialogData: BottomDialogItem,
        itemClickListener: (String, Int) -> Unit
    ) {
        with(binding) {
            data = dialogData

            iconGroup.isVisible = true
            content.setTextAppearance(R.style.text_style_16sp_medium)
            content.layoutParams.run {
                this as ConstraintLayout.LayoutParams
                this.updateMargins(top = 26.dp, bottom = 26.dp)
                this.horizontalBias = 0.05f
            }

            when (dialogData.key) {
                BottomDialogItem.getRequestingReviewList()[0].key -> icon.setImageResource(R.drawable.ic_message)
                BottomDialogItem.getRequestingReviewList()[1].key -> icon.setImageResource(R.drawable.ic_kakaotalk)
                BottomDialogItem.getRequestingReviewList()[2].key -> icon.setImageResource(R.drawable.ic_other_ways)
                BottomDialogItem.getRequestingReviewList()[3].key -> icon.setImageResource(R.drawable.ic_copy_link)
            }

            setItemClickLister {
                itemClickListener(dialogData.key, dialogData.value)
            }

            executePendingBindings()
        }
    }
}