package kr.co.soogong.master.ui.image

import androidx.recyclerview.widget.DiffUtil
import kr.co.soogong.master.data.dto.AttachmentDto

class RectangleImageWithCloseDiffUtil : DiffUtil.ItemCallback<AttachmentDto>() {
    override fun areItemsTheSame(oldItem: AttachmentDto, newItem: AttachmentDto): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: AttachmentDto, newItem: AttachmentDto): Boolean {
        return newItem == oldItem
    }
}