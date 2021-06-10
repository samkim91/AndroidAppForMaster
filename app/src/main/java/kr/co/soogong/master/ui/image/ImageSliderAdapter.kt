package kr.co.soogong.master.ui.image

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.co.soogong.master.data.model.requirement.ImagePath
import kr.co.soogong.master.databinding.ViewHolderImagePageBinding
import timber.log.Timber

class ImageSliderAdapter : RecyclerView.Adapter<ImageSliderAdapter.ImageViewHolder>() {

    private val imageList: ArrayList<ImagePath> = arrayListOf()

    class ImageViewHolder(
        val binding: ViewHolderImagePageBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(imagePath: ImagePath) {
            with(binding) {
                imageUrl = imagePath.path
                executePendingBindings()
            }
        }

        companion object {
            fun create(parent: ViewGroup): ImageViewHolder {
                val binding = ViewHolderImagePageBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return ImageViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ImageViewHolder.create(parent)

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(imageList[position])
    }

    override fun getItemCount(): Int = imageList.size

    fun setList(imageList: List<ImagePath>) {
        Timber.tag(TAG).d("setList: ${imageList.size}")
        this.imageList.clear()
        this.imageList.addAll(imageList)
        notifyDataSetChanged()
    }

    companion object {
        private const val TAG = "ImageSliderAdapter"
    }
}