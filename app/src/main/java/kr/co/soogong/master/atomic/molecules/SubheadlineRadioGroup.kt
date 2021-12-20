package kr.co.soogong.master.atomic.molecules

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RadioGroup
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ViewSubheadlineRadioGroupBinding

class SubheadlineRadioGroup @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle) {
    val binding =
        ViewSubheadlineRadioGroupBinding.inflate(LayoutInflater.from(context), this, true)

    val radioGroup: RadioGroup
        get() = binding.rgContainer

    var subheadline: String? = null
        set(value) {
            field = value
            value?.let { binding.tvSubheadline.text = it }
        }

    var error: String? = null
        set(value) {
            field = value
            if (value.isNullOrEmpty()) {
                binding.tvAlert.isVisible = false
            } else {
                binding.tvAlert.isVisible = true
                binding.tvAlert.text = value
            }
        }

    fun addCheckedChangeListener(
        onCheckedChange: (
            group: RadioGroup,
            checkedId: Int,
        ) -> Unit,
    ) {
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            onCheckedChange(
                group,
                checkedId
            )
        }
    }

    companion object {
        fun addRadioButtons(
            context: Context,
            radioGroup: RadioGroup,
            items: List<String>,
        ) {
            // 재바인딩 시에 남아있을 뷰 삭제
            radioGroup.removeAllViews()

            items.map { item ->
                radioGroup.addView(
                    (LayoutInflater.from(context).inflate(R.layout.radio_button, radioGroup, false)
                            as AppCompatRadioButton).apply { text = item }
                )
            }

            // 초기값 설정
//            (radioGroup.get(0) as AppCompatRadioButton).isChecked = true
        }
    }
}