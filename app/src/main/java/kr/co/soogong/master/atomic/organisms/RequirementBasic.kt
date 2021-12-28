package kr.co.soogong.master.atomic.organisms

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import kr.co.soogong.master.R
import kr.co.soogong.master.data.model.requirement.RequirementStatus
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

    var requirementToken: String? = ""
        set(value) {
            field = value
            value?.let {
                binding.tvRequirementToken.text =
                    context.getString(R.string.requirement_basic_requirement_token, value)
            }
        }

    var createdAt: Date? = null
        set(value) {
            field = value
            value?.let {
                binding.tvCreatedAt.text = it.formatDateWithoutDay()
            }
        }

    var status: RequirementStatus? = null
        set(value) {
            field = value
            value?.let {
                binding.ftStatus.content = value.inKorean
                binding.ftStatus.colorTheme = value.theme
            }
        }

    var address: String? = ""
        set(value) {
            field = value
            value?.let {
                binding.tvAddress.text = value
            }
        }

    var project: String? = ""
        set(value) {
            field = value
            value?.let {
                binding.tvProject.text = value
            }
        }

    var onButtonClick: OnClickListener? = null
        set(value) {
            field = value
            value?.let {
                binding.buttonCallToCustomer.setOnClickListener(value)
            }
        }

    var buttonCallToCustomerVisibility: Boolean = false
        set(value) {
            field = value
            value.let {
                binding.buttonCallToCustomer.isVisible = value
            }
        }
}