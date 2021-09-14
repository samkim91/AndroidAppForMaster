package kr.co.soogong.master.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import kr.co.soogong.master.R
import kr.co.soogong.master.data.dto.AttachmentDto
import kr.co.soogong.master.databinding.ViewRequirementImagesBinding
import kr.co.soogong.master.ui.image.RectangleImageAdapter
import kr.co.soogong.master.ui.image.setList
import kr.co.soogong.master.uihelper.image.ImageViewActivityHelper

class RequirementImages @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attributeSet, defStyle) {
    private var binding =
        ViewRequirementImagesBinding.inflate(LayoutInflater.from(context), this, true)

    var question: String = ""
        set(value) {
            field = value
            binding.question.text = value
        }

    var images: MutableList<AttachmentDto> = arrayListOf()
        set(value) {
            field = value
            if(value.isEmpty()) {
                binding.question.isVisible = false
                return
            }

            binding.imagesRecyclerView.adapter = RectangleImageAdapter(
                cardClickListener = { position ->
                    context.startActivity(
                        ImageViewActivityHelper.getIntent(
                            context,
                            this.images,
                            position
                        )
                    )
                }
            )
            binding.imagesRecyclerView.setList(images)
        }
}