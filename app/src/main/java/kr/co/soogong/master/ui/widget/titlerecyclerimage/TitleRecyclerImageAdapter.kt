package kr.co.soogong.master.ui.widget.titlerecyclerimage

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import kr.co.soogong.master.databinding.ViewHolderImageDeleteButtonBinding

class TitleRecyclerImageAdapter(
    private val closeClickListener: (Int) -> Unit,
) : ListAdapter<Uri, TitleRecyclerViewHolder>(TitleRecyclerImageItemDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        TitleRecyclerViewHolder(ViewHolderImageDeleteButtonBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false))

    override fun onBindViewHolder(holder: TitleRecyclerViewHolder, position: Int) =
        holder.binding(currentList[position], closeClickListener)
}