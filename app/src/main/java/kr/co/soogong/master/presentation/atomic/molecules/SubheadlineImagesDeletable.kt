package kr.co.soogong.master.presentation.atomic.molecules

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import kr.co.soogong.master.data.entity.common.AttachmentDto
import kr.co.soogong.master.databinding.ViewSubheadlineImagesDeletableBinding
import kr.co.soogong.master.presentation.ui.common.image.RectangleImageWithCloseAdapter

class SubheadlineImagesDeletable @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle) {
    private var binding =
        ViewSubheadlineImagesDeletableBinding.inflate(LayoutInflater.from(context), this, true)

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

    fun setImagesDeletableAdapter(onDeletedClickListener: (Int) -> Unit) {
        binding.rvImagesDeletable.adapter = RectangleImageWithCloseAdapter(
            closeClickListener = { position ->
                onDeletedClickListener(position)
            }
        )
    }
}