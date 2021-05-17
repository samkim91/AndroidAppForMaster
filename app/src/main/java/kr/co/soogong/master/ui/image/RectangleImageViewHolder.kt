package kr.co.soogong.master.ui.image

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.databinding.ViewHolderImageBinding

class RectangleImageViewHolder(
    val binding: ViewHolderImageBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun binding(url: String) {
        with(binding) {
            imageUrl = url

            executePendingBindings()
        }
    }

    companion object {
        fun create(parent: ViewGroup, viewType: Int): RectangleImageViewHolder {
            val binding = ViewHolderImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return RectangleImageViewHolder(binding)
        }
    }
}