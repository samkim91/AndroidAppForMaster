package kr.co.soogong.master.atomic.molecules

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.progressindicator.LinearProgressIndicator
import kr.co.soogong.master.databinding.ActionBarProgressBinding

class ActionBarProgress @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle) {
    private val binding = ActionBarProgressBinding.inflate(LayoutInflater.from(context), this, true)

    var title: String? = null
        set(value) {
            field = value
            value?.let {
                binding.tvTitle.text = value
            }
        }

    val backButton: AppCompatImageView = binding.ivBack
    val anyButton: AppCompatTextView = binding.tvAny
    val progressBar: LinearProgressIndicator = binding.lpiProgress

    fun setButtonBackClickListener(listener: OnClickListener) {
        binding.ivBack.setOnClickListener(listener)
    }

    fun setButtonAnyClickListener(listener: OnClickListener) {
        binding.tvAny.setOnClickListener(listener)
    }
}