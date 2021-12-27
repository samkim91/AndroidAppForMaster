package kr.co.soogong.master.atomic.molecules

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import kr.co.soogong.master.data.dto.common.AttachmentDto
import kr.co.soogong.master.databinding.ViewTitleRecyclerImageBinding
import kr.co.soogong.master.ui.image.RectangleImageWithCloseAdapter
import kr.co.soogong.master.ui.image.setImagesDeletable

class TitleRecyclerImage @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle) {
    private val binding =
        ViewTitleRecyclerImageBinding.inflate(LayoutInflater.from(context), this, true)

    var title: String? = ""
        set(value) {
            field = value
            binding.title.text = value
            if (value.isNullOrEmpty()) binding.title.isVisible = false
        }


    var cameraIconVisible: Boolean = true
        set(value) {
            field = value
            binding.cameraIcon.isVisible = value
        }

    var images: MutableList<AttachmentDto> = arrayListOf()
        set(value) {
            field = value
            binding.photoList.setImagesDeletable(value)
        }

    fun setAdapter(
        closeClick: (Int) -> Unit,
    ) {
        binding.photoList.adapter =
            RectangleImageWithCloseAdapter(closeClickListener = { position ->
                closeClick(position)
            })
    }

    fun addIconClickListener(
        onClick: () -> Unit,
    ) {
        binding.cameraIcon.setOnClickListener { onClick() }
    }
}