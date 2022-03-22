package kr.co.soogong.master.presentation.atomic.molecules

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ViewSubheadlineContentArrowBinding

class SubheadlineContentArrow @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle) {
    private val binding =
        ViewSubheadlineContentArrowBinding.inflate(LayoutInflater.from(context), this, true)

    private val tvLabel: AppCompatTextView = binding.tvLabel
    private val tvContent: AppCompatTextView = binding.tvContent
    private val ivArrow: AppCompatImageView = binding.ivArrow

    var label: String? = null
        set(value) {
            field = value
            value?.let { tvLabel.text = it }
        }

    var content: String? = null
        set(value) {
            field = value
            tvContent.text = value

            when (value) {
                // 메모 남기기
                context.getString(R.string.writing_memo) -> {
                    tvContent.setTextColor(ResourcesCompat.getColor(resources,
                        R.color.brand_green,
                        null))
                    ivArrow.backgroundTintList =
                        ResourcesCompat.getColorStateList(resources, R.color.brand_green, null)
                }
                else -> {
                    tvContent.setTextColor(ResourcesCompat.getColor(resources,
                        R.color.black,
                        null))
                    ivArrow.backgroundTintList =
                        ResourcesCompat.getColorStateList(resources, R.color.grey_2, null)
                }
            }
        }

    fun setOnContainerClickListener(onClickListener: OnClickListener) {
        this.setOnClickListener(onClickListener)
    }
}