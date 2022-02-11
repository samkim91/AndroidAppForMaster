package kr.co.soogong.master.presentation.atomic.molecules

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import kr.co.soogong.master.databinding.ActionBarBinding

class ActionBar @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle), IActionBar {
    private val binding = ActionBarBinding.inflate(LayoutInflater.from(context), this, true)

    override val tvTitle: AppCompatTextView = binding.tvTitle
    override val tvRight: AppCompatTextView = binding.tvRight
    override val ivBack: AppCompatImageView = binding.ivBack
    override val ivIcon: AppCompatImageView = binding.ivIcon

    override var title: String? = null
        set(value) {
            field = value
            value?.let {
                tvTitle.text = value
            }
        }

    override var backButtonVisibility: Boolean? = null
        set(value) {
            field = value
            value?.let {
                ivBack.isVisible = it
            }
        }

    override var rightText: String? = null
        set(value) {
            field = value
            value?.let {
                tvRight.text = it
                tvRight.isVisible = true
            }
        }

    override var icon: Drawable? = null
        set(value) {
            field = value
            value?.let {
                ivIcon.setImageDrawable(it)
            }
        }

    override fun setIvBackClickListener(listener: OnClickListener) {
        ivBack.setOnClickListener(listener)
    }

    override fun setTvRightClickListener(listener: OnClickListener) {
        tvRight.setOnClickListener(listener)
    }

    override fun setIvIconClickListener(listener: OnClickListener) {
        ivIcon.setOnClickListener(listener)
    }
}