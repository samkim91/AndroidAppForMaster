package kr.co.soogong.master.ui.atomic.molecules

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import kr.co.soogong.master.databinding.ViewTitleRadioGroupBinding

class TitleRadioGroup @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle) {
    val binding =
        ViewTitleRadioGroupBinding.inflate(LayoutInflater.from(context), this, true)

    val radioGroup: RadioGroup
        get() = binding.radioGroup

    var title: String? = ""
        set(value) {
            field = value
            binding.title.isVisible = !value.isNullOrEmpty()
            binding.title.text = value
        }

    var subTitle: String? = ""
        set(value) {
            field = value
            binding.subTitle.isVisible = !value.isNullOrEmpty()
            binding.subTitle.text = value
        }

    var alertText: String? = ""
        set(value) {
            field = value
            binding.alert.text = value
        }

    var alertVisible: Boolean = false
        set(value) {
            field = value
            binding.alert.isVisible = value
        }

    fun addRadioButton(
        radioButton: RadioButton,
    ) {
        binding.radioGroup.addView(radioButton)
    }

    fun addCheckedChangeListener(
        onCheckedChange: (
            group: RadioGroup,
            checkedId: Int,
        ) -> Unit,
    ) {
        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            onCheckedChange(
                group,
                checkedId
            )
        }
    }
}