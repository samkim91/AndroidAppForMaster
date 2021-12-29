package kr.co.soogong.master.ui.dialog.bottomSheetDialogRecyclerView

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter

class BottomSheetDialogAdapter(
    private val itemClickListener: (BottomSheetDialogItem) -> Unit,
) : ListAdapter<BottomSheetDialogItem, BottomSheetDialogViewHolder>(BottomSheetDialogItemDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        BottomSheetDialogViewHolder.create(parent, viewType)

    override fun onBindViewHolder(holder: BottomSheetDialogViewHolder, position: Int) =
        holder.binding(currentList[position], itemClickListener)

    override fun getItemViewType(position: Int): Int =
        currentList[position].type
}