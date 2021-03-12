package kr.co.soogong.master.ui.requirements.action.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.databinding.ViewHolderImageBinding

class ViewEstimateImageViewHolder(
    val binding: ViewHolderImageBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun binding(url: String) {
        with(binding) {
            imageUrl = url

            executePendingBindings()
        }
    }

    companion object {
        fun create(parent: ViewGroup): ViewEstimateImageViewHolder {
            val binding = ViewHolderImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return ViewEstimateImageViewHolder(binding)
        }
    }
}