package kr.co.soogong.master.presentation.atomic.molecules

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import kr.co.soogong.master.databinding.ActionBarBinding

class ActionBar @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle), IActionBar {
    private val binding = ActionBarBinding.inflate(LayoutInflater.from(context), this, true)

    override var title: String? = null
        set(value) {
            field = value
            value?.let {
                binding.tvTitle.text = value
            }
        }

    override var backButtonVisibility: Boolean? = null
        set(value) {
            field = value
            value?.let {
                binding.ivBack.isVisible = it
            }
        }

    override var anyButtonText: String? = null
        set(value) {
            field = value
            value?.let {
                binding.tvAny.text = it
                binding.tvAny.isVisible = true
            }
        }

    override fun setButtonBackClickListener(listener: OnClickListener) {
        binding.ivBack.setOnClickListener(listener)
    }

    override fun setButtonAnyClickListener(listener: OnClickListener) {
        binding.tvAny.setOnClickListener(listener)
    }
}