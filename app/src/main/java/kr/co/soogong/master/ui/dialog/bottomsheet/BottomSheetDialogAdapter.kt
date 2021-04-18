package kr.co.soogong.master.ui.dialog.bottomsheet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import kr.co.soogong.master.databinding.ViewHolderBottomSheetDialogItemBinding

class BottomSheetDialogAdapter(
    private val itemClickListener: (String) -> Unit,
) : ListAdapter<BottomSheetDialogData, BottomSheetDialogViewHolder>(BottomSheetDialogItemDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        BottomSheetDialogViewHolder(ViewHolderBottomSheetDialogItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false))

    override fun onBindViewHolder(holder: BottomSheetDialogViewHolder, position: Int) =
        holder.binding(currentList[position], itemClickListener)
}