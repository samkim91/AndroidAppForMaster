package kr.co.soogong.master.atomic.molecules

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import kr.co.soogong.master.R
import kr.co.soogong.master.data.common.ColorTheme
import kr.co.soogong.master.databinding.ViewBoxIconContentBinding

class BoxIconContent @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle) {
    private var binding =
        ViewBoxIconContentBinding.inflate(LayoutInflater.from(context), this, true)

    var icon: Drawable? = null
        set(value) {
            field = value
            value?.let {
                binding.ivIcon.background = it
            }
        }

    var content: String? = null
        set(value) {
            field = value
            value?.let {
                binding.tvContent.text = it
            }
        }

    var colorTheme: ColorTheme? = null
        set(value) {
            field = value
            value?.let {
                when (it) {
                    is ColorTheme.Grey -> {
                        this.background = ResourcesCompat.getDrawable(resources,
                            R.drawable.bg_solid_background_radius16,
                            null)
                        binding.tvContent.setTextColor(ResourcesCompat.getColor(resources,
                            R.color.grey_3,
                            null))
                        // TODO: 2021/11/18 진짜 사용된다면, iv_container theme 도 설정해줘야할 듯
                    }
                    is ColorTheme.Blue -> {
                    }
                    is ColorTheme.Red -> {
                    }
                    is ColorTheme.Green -> { }
                }
            }
        }
}