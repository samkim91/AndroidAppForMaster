package kr.co.soogong.master.atomic.molecules

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import kr.co.soogong.master.data.dto.common.AttachmentDto
import kr.co.soogong.master.databinding.ViewSubheadlineAddingImagesDeletableBinding
import kr.co.soogong.master.ui.image.RectangleImageWithCloseAdapter

class SubheadlineAddingImagesDeletable @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle) {
    private var binding =
        ViewSubheadlineAddingImagesDeletableBinding.inflate(LayoutInflater.from(context),
            this,
            true)

    var subheadline: String? = null
        set(value) {
            field = value
            value?.let { binding.tvSubheadline.text = value }
        }

    var images: List<AttachmentDto>? = emptyList()
        set(value) {
            field = value
            value?.let { binding.images = it }
        }

    var error: String? = null
        set(value) {
            field = value
            if (value.isNullOrEmpty()) {
                binding.tvAlert.isVisible = false
            } else {
                binding.tvAlert.isVisible = true
                binding.tvAlert.text = value
            }
        }

    var onButtonClick: OnClickListener? = null
        set(value) {
            field = value
            value?.let { binding.ivAddingImage.setOnClickListener(it) }
        }

    fun setImagesDeletableAdapter(onDeletedClickListener: (Int) -> Unit) {
        binding.rvImages.adapter = RectangleImageWithCloseAdapter(
            closeClickListener = { position ->
                onDeletedClickListener(position)
            }
        )
    }
}