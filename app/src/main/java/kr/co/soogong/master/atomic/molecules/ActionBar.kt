package kr.co.soogong.master.atomic.molecules

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import kr.co.soogong.master.databinding.ActionBar2Binding

class ActionBar @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle) {
    private val binding = ActionBar2Binding.inflate(LayoutInflater.from(context), this, true)

    var title: String? = null
        set(value) {
            field = value
            value?.let {
                binding.tvTitle.text = value
            }
        }

    var backButtonVisibility: Boolean? = null
        set(value) {
            field = value
            value?.let {
                binding.ivBack.isVisible = it
            }
        }

    var anyButtonVisibility: Boolean? = null
        set(value) {
            field = value
            value?.let {
                binding.tvAny.isVisible = it
            }
        }

    fun setButtonBackClickListener(listener: OnClickListener) {
        binding.ivBack.setOnClickListener(listener)
    }

    fun setButtonAnyClickListener(listener: OnClickListener) {
        binding.tvAny.setOnClickListener(listener)
    }
}