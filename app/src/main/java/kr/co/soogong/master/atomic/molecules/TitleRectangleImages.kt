package kr.co.soogong.master.atomic.molecules

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import kr.co.soogong.master.data.dto.common.AttachmentDto
import kr.co.soogong.master.databinding.ViewRequirementImagesBinding
import kr.co.soogong.master.ui.image.RectangleImageAdapter
import kr.co.soogong.master.ui.image.setList
import kr.co.soogong.master.uihelper.image.ImageViewActivityHelper

class TitleRectangleImages @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle) {
    private var binding =
        ViewRequirementImagesBinding.inflate(LayoutInflater.from(context), this, true)

    var label: String = ""
        set(value) {
            field = value
            binding.textViewLabel.text = value
        }

    var images: MutableList<AttachmentDto>? = arrayListOf()
        set(value) {
            if (value.isNullOrEmpty()) return

            field = value
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