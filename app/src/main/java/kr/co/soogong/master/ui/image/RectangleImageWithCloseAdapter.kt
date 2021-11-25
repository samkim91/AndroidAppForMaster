package kr.co.soogong.master.ui.image

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import kr.co.soogong.master.data.dto.AttachmentDto
import kr.co.soogong.master.databinding.ViewHolderImageDeletableBinding

class RectangleImageWithCloseAdapter(
    private val closeClickListener: (Int) -> Unit,
) : ListAdapter<AttachmentDto, ViewHolderImageDeletable>(ImageDeletableDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolderImageDeletable(
            ViewHolderImageDeletableBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolderImageDeletable, position: Int) =
        holder.binding(currentList[position], closeClickListener)

}