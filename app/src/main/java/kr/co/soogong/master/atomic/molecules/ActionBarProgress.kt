package kr.co.soogong.master.atomic.molecules

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.google.android.material.progressindicator.LinearProgressIndicator
import kr.co.soogong.master.databinding.ActionBarProgressBinding

class ActionBarProgress @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle), IActionBar {
    private val binding = ActionBarProgressBinding.inflate(LayoutInflater.from(context), this, true)

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

    var maxProgress: Int? = null
        set(value) {
            field = value
            value?.let { binding.lpiProgress.max = it }
        }

    var currentProgress: Int? = null
        set(value) {
            field = value
            value?.let { binding.lpiProgress.progress = it }
        }

    val backButton: AppCompatImageView = binding.ivBack
    val anyButton: AppCompatTextView = binding.tvAny
    val progressBar: LinearProgressIndicator = binding.lpiProgress

    override fun setButtonBackClickListener(listener: OnClickListener) {
        binding.ivBack.setOnClickListener(listener)
    }

    override fun setButtonAnyClickListener(listener: OnClickListener) {
        binding.tvAny.setOnClickListener(listener)
    }
}