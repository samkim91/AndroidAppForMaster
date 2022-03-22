package kr.co.soogong.master.presentation.atomic.organisms

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ViewRequirementBasicBinding
import kr.co.soogong.master.domain.entity.requirement.RequirementStatus
import kr.co.soogong.master.utility.extension.formatDateWithDay
import kr.co.soogong.master.utility.extension.setCopyToClipboard
import kr.co.soogong.master.utility.extension.setLabelTheme
import kr.co.soogong.master.utility.extension.setUnderline
import java.util.*

class RequirementBasic @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle) {
    private var binding =
        ViewRequirementBasicBinding.inflate(LayoutInflater.from(context), this, true)

    var requirementToken: String? = null
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
                binding.tvCreatedAt.text = it.formatDateWithDay()
            }
        }

    var status: RequirementStatus? = null
        set(value) {
            field = value
            value?.let {
                binding.tvStatus.text = value.inKorean
                binding.tvStatus.setLabelTheme(value.theme)
            }
        }

    var address: String? = null
        set(value) {
            field = value
            value?.let {
                binding.tvAddress.text = it
                binding.tvAddress.setUnderline()
                binding.tvAddress.setCopyToClipboard()
            }
        }

    var oldAddress: String? = null
        set(value) {
            field = value
            value?.let {
                binding.groupOldAddress.isVisible = it.isNotBlank()
                binding.tvOldAddress.text = it
                binding.tvOldAddress.setUnderline()
                binding.tvOldAddress.setCopyToClipboard()
            }
        }

    var project: String? = null
        set(value) {
            field = value
            value?.let {
                binding.tvProject.text = value
            }
        }
}