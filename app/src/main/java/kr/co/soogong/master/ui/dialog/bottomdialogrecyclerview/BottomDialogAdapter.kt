package kr.co.soogong.master.ui.dialog.bottomdialogrecyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import kr.co.soogong.master.databinding.ViewHolderBottomSheetDialogItemBinding

class BottomDialogAdapter(
    private val itemClickListener: (String, Int) -> Unit,
) : ListAdapter<BottomDialogItem, BottomDialogViewHolder>(BottomDialogItemDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BottomDialogViewHolder {
        val binding = ViewHolderBottomSheetDialogItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return when(viewType) {
            BottomDialogItem.ICON_BOTTOM_DIALOG -> IconBottomDialogViewHolder(binding)
            else -> SimpleBottomDialogViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: BottomDialogViewHolder, position: Int) =
        holder.binding(currentList[position], itemClickListener)

    override fun getItemViewType(position: Int): Int =
        currentList[position].type

}