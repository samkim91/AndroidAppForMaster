package kr.co.soogong.master.atomic.atoms

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import kr.co.soogong.master.R
import kr.co.soogong.master.data.common.ShapeTheme
import kr.co.soogong.master.databinding.ViewLabelOutlinedBinding

class LabelOutlined @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle) {
    private val binding = ViewLabelOutlinedBinding.inflate(LayoutInflater.from(context), this, true)

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
                                R.drawable.bg_solid_white_stroke_light_grey2_radius20,
                                null)
                    }
                    is ShapeTheme.Rectangle -> {
                        binding.tvContent.background =
                            ResourcesCompat.getDrawable(resources,
                                R.drawable.bg_solid_white_stroke_light_grey2_radius4,
                                null)
                    }
                }
            }
        }
}