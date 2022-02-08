package kr.co.soogong.master.presentation.atomic.molecules

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import kr.co.soogong.master.data.entity.common.AttachmentDto
import kr.co.soogong.master.databinding.ViewHeadlineImagesEditableBinding

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
                            binding.ivFirstImage.image = value[i]
                        }
                        1 -> {
                            binding.ivLastImage.image = value[i]
                        }
                        else -> return
                    }
                }
            }
        }

    var onButtonClick: OnClickListener? = null
        set(value) {
            field = value
            value?.let {
                binding.ivAddingImages.setOnClickListener(it)
                binding.ivFirstImage.setOnClickListener(it)
                binding.ivLastImage.setOnClickListener(it)
            }
        }
}