package kr.co.soogong.master.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.forEachIndexed
import androidx.core.view.isVisible
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ViewRequirementBasicBinding
import kr.co.soogong.master.databinding.ViewRequirementIntroBinding

class RequirementBasic @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attributeSet, defStyle) {
    private var binding =
        ViewRequirementBasicBinding.inflate(LayoutInflater.from(context), this, true)

    var address: String? = ""
        set(value) {
            field = value
            binding.address.text = value
        }

    var project: String? = ""
        set(value) {
            field = value
            binding.project.text = value
        }

    var createdAt: String? = ""
        set(value) {
            field = value
            binding.createdAt.text = value
        }

    var requirementToken: String? = ""
        set(value) {
            field = value
            binding.requirementToken.text = value
        }

    var measurementBadgeVisibility: Boolean? = false
        set(value) {
            field = value
            binding.measurementBadgeVisibility = value
        }
}