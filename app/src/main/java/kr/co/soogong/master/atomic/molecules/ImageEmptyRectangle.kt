package kr.co.soogong.master.atomic.molecules

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import kr.co.soogong.master.data.dto.common.AttachmentDto
import kr.co.soogong.master.databinding.ViewImageEmptyRectangleBinding
import kr.co.soogong.master.utility.extension.setImageUrl

class ImageEmptyRectangle @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle) {
    private val binding =
        ViewImageEmptyRectangleBinding.inflate(LayoutInflater.from(context), this, true)

    var image: AttachmentDto? = null
        set(value) {
            field = value
            value?.let {
                binding.ivContainer.setImageUrl(value.url)
                binding.ivIcon.isVisible = false
            }
        }
}