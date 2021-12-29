package kr.co.soogong.master.ui.dialog.bottomSheetDialogRecyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.databinding.ViewHolderBottomSheetDialogBinding
import kr.co.soogong.master.databinding.ViewHolderBottomSheetDialogIconBinding
import kr.co.soogong.master.utility.extension.dp

abstract class BottomSheetDialogViewHolder(
    open val binding: ViewDataBinding,
) : RecyclerView.ViewHolder(binding.root) {
    abstract fun binding(
        sheetDialogData: BottomSheetDialogItem,
        itemClickListener: (BottomSheetDialogItem) -> Unit,
    )

    companion object {
        fun create(parent: ViewGroup, viewType: Int) = when (viewType) {
            BottomSheetDialogItem.ICON -> BottomSheetDialogIconViewHolder(
                ViewHolderBottomSheetDialogIconBinding.inflate(LayoutInflater.from(parent.context),
                    parent,
                    false))
            else -> BottomSheetDialogDefaultViewHolder(ViewHolderBottomSheetDialogBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false))
        }
    }
}

class BottomSheetDialogDefaultViewHolder(
    override val binding: ViewHolderBottomSheetDialogBinding,
) : BottomSheetDialogViewHolder(binding) {
    override fun binding(
        sheetDialogData: BottomSheetDialogItem,
        itemClickListener: (BottomSheetDialogItem) -> Unit,
    ) {
        with(binding) {
            tvContent.text = sheetDialogData.key

            itemView.setOnClickListener { itemClickListener(sheetDialogData) }

            itemView.layoutParams =
                (itemView.layoutParams as ViewGroup.MarginLayoutParams).apply {
                    bottomMargin = 12.dp
                    topMargin = 12.dp
                }
        }
    }
}

class BottomSheetDialogIconViewHolder(
    override val binding: ViewHolderBottomSheetDialogIconBinding,
) : BottomSheetDialogViewHolder(binding) {
    override fun binding(
        sheetDialogData: BottomSheetDialogItem,
        itemClickListener: (BottomSheetDialogItem) -> Unit,
    ) {
        with(binding) {
            sheetDialogData.icon?.let { ivIcon.setBackgroundResource(it) }

            tvContent.text = sheetDialogData.key

            itemView.setOnClickListener { itemClickListener(sheetDialogData) }

            itemView.layoutParams =
                (itemView.layoutParams as ViewGroup.MarginLayoutParams).apply {
                    bottomMargin = 14.dp
                    topMargin = 14.dp
                }
        }
    }
}