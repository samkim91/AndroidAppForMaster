package kr.co.soogong.master.ui.atomic.organisms

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ViewRequirementBasicBinding
import kr.co.soogong.master.utility.extension.formatDateWithoutDay
import java.util.*

class RequirementBasic @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle) {
    private var binding =
        ViewRequirementBasicBinding.inflate(LayoutInflater.from(context), this, true)

    var createdAt: Date? = null
        set(value) {
            field = value
            value?.let {
                binding.textViewCreatedAt.text =
                    context.getString(R.string.requirement_basic_created_at,
                        it.formatDateWithoutDay())
            }
        }

    var requirementToken: String? = ""
        set(value) {
            field = value
            value?.let {
                binding.textViewRequirementToken.text =
                    context.getString(R.string.requirement_basic_requirement_token, value)
            }
        }

    var address: String? = ""
        set(value) {
            field = value
            value?.let {
                binding.textViewAddress.text = value
            }
        }

    var project: String? = ""
        set(value) {
            field = value
            value?.let {
                binding.textViewProject.text = value
            }
        }

    var onButtonClick: OnClickListener? = null
        set(value) {
            field = value
            value?.let {
                binding.buttonCallToCustomer.setOnClickListener(value)
            }
        }

    var buttonCallToCustomerVisibility: Boolean = true
        set(value) {
            field = value
            value.let {
                binding.buttonCallToCustomer.isVisible = value
            }
        }
}