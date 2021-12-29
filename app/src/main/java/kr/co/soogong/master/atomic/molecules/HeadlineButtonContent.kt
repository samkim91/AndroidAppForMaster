package kr.co.soogong.master.atomic.molecules

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ViewHeadlineButtonContentBinding

class HeadlineButtonContent @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle) {

    private val binding =
        ViewHeadlineButtonContentBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        setButtonStatus()
    }

    var title: String? = null
        set(value) {
            field = value
            value?.let { binding.tvTitle.text = it }
        }

    val tvContent = binding.tvContent

    var content: String? = null
        set(value) {
            field = value
            value?.let {
                binding.tvContent.setText(it)
                binding.tvContent.isVisible = true
                setButtonStatus()
            }
        }

    var hint: String? = null
        set(value) {
            field = value
            value?.let {
                binding.tvContent.hint = it
                binding.tvContent.isVisible = true
            }
        }

    var onButtonClick: OnClickListener? = null
        set(value) {
            field = value
            value?.let { binding.tvButton.setOnClickListener(it) }
        }

    private fun setButtonStatus() {
        with(binding.tvButton) {
            if (content.isNullOrEmpty()) {
                text = resources.getString(R.string.register)
                setTextColor(ResourcesCompat.getColor(resources, R.color.brand_green, null))
            } else {
                text = resources.getString(R.string.editing)
                setTextColor(ResourcesCompat.getColor(resources, R.color.brand_red, null))
            }
        }
    }
}