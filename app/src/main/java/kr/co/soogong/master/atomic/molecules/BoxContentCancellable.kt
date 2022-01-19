package kr.co.soogong.master.atomic.molecules

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import kr.co.soogong.master.R
import kr.co.soogong.master.data.global.CodeTable
import kr.co.soogong.master.databinding.ViewBoxContentCancellableBinding

class BoxContentCancellable @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle) {
    private var binding =
        ViewBoxContentCancellableBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        binding.ivIcon.setOnClickListener {
            this.isVisible = false
        }
    }

    var content: String? = null
        set(value) {
            field = value
            value?.let {
                binding.tvContent.text = it
            }
        }

    var containerBackground: Drawable? = null
        set(value) {
            field = value
            value?.let {
                binding.ivLayout.background = it
            }
        }

    companion object {
        @JvmStatic
        @BindingAdapter(value = ["approvedStatus"])
        fun BoxContentCancellable.setByApprovedStatus(approvedStatus: String?) {
            if (approvedStatus.isNullOrEmpty()) return

            when (approvedStatus) {
                CodeTable.NOT_APPROVED.code -> {
                    this.isVisible = true
                    this.content =
                        context.getString(R.string.explanation_of_required_information_not_approved)
                    this.containerBackground = ResourcesCompat.getDrawable(resources,
                        R.drawable.bg_solid_grey3_radius12,
                        null)
                }
                CodeTable.REQUEST_APPROVE.code -> {
                    this.isVisible = true
                    this.content =
                        context.getString(R.string.explanation_of_required_information_request_approve)
                    this.containerBackground = ResourcesCompat.getDrawable(resources,
                        R.drawable.bg_solid_green_radius12,
                        null)
                }
                CodeTable.APPROVED.code -> {
                    this.isVisible = false
                }
            }
        }

        @JvmStatic
        @BindingAdapter(value = ["percentage"])
        fun BoxContentCancellable.setByPercentage(percentage: Double?) {
            if (percentage == null) return

            when {
                percentage < 100.0 -> {
                    this.isVisible = true
                    this.content =
                        context.getString(R.string.explanation_of_optional_information_not_filled)
                    this.containerBackground = ResourcesCompat.getDrawable(resources,
                        R.drawable.bg_solid_grey3_radius12,
                        null)
                }
                else -> {
                    this.isVisible = false
                }
            }
        }
    }
}