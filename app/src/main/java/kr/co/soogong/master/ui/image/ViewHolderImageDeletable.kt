package kr.co.soogong.master.ui.image

import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.data.dto.common.AttachmentDto
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
                closeClickListener(adapterPosition)
            }

            executePendingBindings()
        }
    }
}