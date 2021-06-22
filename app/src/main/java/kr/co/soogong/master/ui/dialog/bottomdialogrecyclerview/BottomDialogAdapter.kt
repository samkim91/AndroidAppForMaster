package kr.co.soogong.master.ui.dialog.bottomdialogrecyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import kr.co.soogong.master.databinding.ViewHolderBottomSheetDialogItemBinding

class BottomDialogAdapter(
    private val itemClickListener: (String, Int) -> Unit,
) : ListAdapter<BottomDialogData, BottomDialogViewHolder>(BottomDialogItemDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        BottomDialogViewHolder(
            ViewHolderBottomSheetDialogItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: BottomDialogViewHolder, position: Int) =
        holder.binding(currentList[position], itemClickListener)
}