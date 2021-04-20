package kr.co.soogong.master.ui.widget.titlerecyclerimage

import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.databinding.ViewHolderImageDeleteButtonBinding

class TitleRecyclerViewHolder(
    private val binding: ViewHolderImageDeleteButtonBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun binding(
        imageInfo: Uri,
        closeClickListener: (Int) -> Unit,
    ) {
        with(binding) {
            imageUri = imageInfo

            setCloseClickListener {
                closeClickListener(adapterPosition)
            }

            executePendingBindings()
        }
    }
}