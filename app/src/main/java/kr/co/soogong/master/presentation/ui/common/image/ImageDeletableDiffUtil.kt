package kr.co.soogong.master.presentation.ui.common.image

import androidx.recyclerview.widget.DiffUtil
import kr.co.soogong.master.data.entity.common.AttachmentDto

class ImageDeletableDiffUtil : DiffUtil.ItemCallback<AttachmentDto>() {
    override fun areItemsTheSame(oldItem: AttachmentDto, newItem: AttachmentDto): Boolean {
        return oldItem.uri == newItem.uri
    }

    override fun areContentsTheSame(oldItem: AttachmentDto, newItem: AttachmentDto): Boolean {
        return newItem == oldItem
    }
}