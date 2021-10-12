package kr.co.soogong.master.ui.widget

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import kr.co.soogong.master.R
import kr.co.soogong.master.data.model.profile.SecretaryCodeTable
import kr.co.soogong.master.databinding.ViewRequirementBasicBinding
import java.util.*

class RequirementBasic @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle) {
    private var binding =
        ViewRequirementBasicBinding.inflate(LayoutInflater.from(context), this, true)

    var address: String? = ""
        set(value) {
            field = value
            binding.address.text = value
        }

    var oldAddress: String? = ""
        set(value) {
            field = value
            binding.oldAddress.text = value
        }

    var project: String? = ""
        set(value) {
            field = value
            binding.project.text = value
        }

    var createdAt: Date? = Date()
        set(value) {
            field = value
            value?.let {
                binding.createdAt.text = context.getString(
                    R.string.requirements_card_start_time,
                    SimpleDateFormat("yyyy.MM.dd - HH:mm", Locale.KOREA).format(it)
                )
            }
        }

    var requirementToken: String? = ""
        set(value) {
            field = value
            binding.requirementToken.text =
                context.getString(R.string.requirement_token_label, value)
        }

    var measurementBadgeVisibility: String? = ""
        set(value) {
            field = value
            binding.measurementBadge.root.isVisible = value == SecretaryCodeTable.code
        }
}