package kr.co.soogong.master.presentation.ui.common.image

import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.data.entity.common.AttachmentDto
import kr.co.soogong.master.databinding.ViewHolderImageDeletableBinding

class ViewHolderImageDeletable(
    private val binding: ViewHolderImageDeletableBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun binding(
        imageInfo: AttachmentDto,
        closeClickListener: (Int) -> Unit,
    ) {
        with(binding) {
            imageUri = imageInfo.url?.toUri() ?: imageInfo.uri

            setCloseClickListener {
                closeClickListener(absoluteAdapterPosition)
            }

            executePendingBindings()
        }
    }
}