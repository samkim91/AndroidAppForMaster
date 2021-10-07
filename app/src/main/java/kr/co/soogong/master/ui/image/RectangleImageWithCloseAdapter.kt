package kr.co.soogong.master.ui.image

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import kr.co.soogong.master.data.dto.AttachmentDto
import kr.co.soogong.master.databinding.ViewHolderImageDeleteButtonBinding
import kr.co.soogong.master.utility.extension.dp

class RectangleImageWithCloseAdapter(
    private val closeClickListener: (Int) -> Unit,
) : ListAdapter<AttachmentDto, RectangleImageWithCloseHolder>(RectangleImageWithCloseDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        RectangleImageWithCloseHolder(
            ViewHolderImageDeleteButtonBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: RectangleImageWithCloseHolder, position: Int) {
        holder.itemView.layoutParams =
            (holder.itemView.layoutParams as ViewGroup.MarginLayoutParams).apply {
                marginStart = 5.dp
                marginEnd = 5.dp
                bottomMargin = 1.dp
                topMargin = 1.dp
            }

        holder.binding(currentList[position], closeClickListener)
    }

}