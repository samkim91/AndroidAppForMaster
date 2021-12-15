package kr.co.soogong.master.atomic.molecules

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import kr.co.soogong.master.data.dto.AttachmentDto
import kr.co.soogong.master.databinding.ViewHeadlineImagesEditableBinding
import kr.co.soogong.master.utility.extension.setImageUrl

class HeadlineImagesEditable @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle) {
    private val binding =
        ViewHeadlineImagesEditableBinding.inflate(LayoutInflater.from(context), this, true)

    var title: String? = null
        set(value) {
            field = value
            value?.let {
                binding.tvTitle.text = it
            }
        }

    var images: MutableList<AttachmentDto>? = arrayListOf()
        set(value) {
            field = value
            value?.let {
                for (i in 0 until value.size) {
                    when (i) {
                        0 -> {
                            binding.ivFirstImage.ivContainer.setImageUrl(value[i].url)
                            binding.ivFirstImage.ivIcon.isVisible = false
                        }
                        1 -> {
                            binding.ivLastImage.ivContainer.setImageUrl(value[i].url)
                            binding.ivLastImage.ivIcon.isVisible = false
                        }
                        else -> return
                    }
                }
            }
        }

    var onButtonClick: OnClickListener? = null
        set(value) {
            field = value
            value?.let { binding.ivAddingImages.ivContainer.setOnClickListener(it) }
        }

//    fun setAddingImagesClickListener(
//        onClick: () -> Unit,
//    ) {
//        binding.cameraIcon.setOnClickListener { onClick() }
//    }
}