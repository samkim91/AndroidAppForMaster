package kr.co.soogong.master.atomic.atoms

import android.content.Context
import android.text.InputFilter
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ViewTextInputBinding

class TextInput @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle), ITextInput {
    private var binding =
        ViewTextInputBinding.inflate(LayoutInflater.from(context), this, true)

    override val textInputLayout: TextInputLayout = binding.tilContainer
    override val textInputEditText: TextInputEditText = binding.tieEdittext

    override var enabled: Boolean? = null
        set(value) {
            field = value
            value?.let {
                with(binding.tieEdittext) {
                    isEnabled = it
                    setTextColor(ResourcesCompat.getColor(resources, R.color.grey_3, null))
                    setBackgroundColor(ResourcesCompat.getColor(resources,
                        R.color.light_grey_1,
                        null))
                }
            }
        }

    override var maxCount: Int? = null
        set(value) {
            field = value
            value?.let {
                binding.tilContainer.isCounterEnabled = true
                binding.tilContainer.counterMaxLength = value
                binding.tieEdittext.filters += InputFilter.LengthFilter(value)      // Edittext 의 max 값 추가
            }
        }
}