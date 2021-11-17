package kr.co.soogong.master.atomic.atoms

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import kr.co.soogong.master.R
import kr.co.soogong.master.data.common.ShapeTheme
import kr.co.soogong.master.databinding.ViewOutlinedLabelBinding

class OutlinedLabel @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle) {
    private val binding = ViewOutlinedLabelBinding.inflate(LayoutInflater.from(context), this, true)

    var content: String? = null
        set(value) {
            field = value
            value?.let {
                binding.tvContent.text = value
            }
        }

    var shapeTheme: ShapeTheme? = null
        set(value) {
            field = value
            value?.let {
                when (value) {
                    is ShapeTheme.Circle -> {
                        binding.tvContent.background =
                            ResourcesCompat.getDrawable(resources,
                                R.drawable.background_white_solid_light_grey2_stroke_radius20,
                                null)
                    }
                    is ShapeTheme.Rectangle -> {
                        binding.tvContent.background =
                            ResourcesCompat.getDrawable(resources,
                                R.drawable.background_white_solid_light_grey2_stroke_radius4,
                                null)
                    }
                }
            }
        }
}