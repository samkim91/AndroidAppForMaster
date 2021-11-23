package kr.co.soogong.master.atomic.molecules

import android.content.Context
import android.text.InputFilter
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kr.co.soogong.master.databinding.ViewSubheadlineTextareaCounterBinding

class SubheadlineTextareaCounter @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle) {
    private var binding =
        ViewSubheadlineTextareaCounterBinding.inflate(LayoutInflater.from(context), this, true)

    var subheadline: String? = null
        set(value) {
            field = value
            value?.let { binding.tvSubheadline.text = value }
        }

    val editTextContainer: TextInputLayout = binding.tcContent.tilContainer
    val editText: TextInputEditText = binding.tcContent.tieEdittext

    // ReverseBinding 으로 인해 필요 없어짐
//    var content: String? = null
//        set(value) {
//            field = value
//            value?.let {
//                binding.tcContent.tieEdittext.setText(it)
//            }
//        }

    var hint: String? = null
        set(value) {
            field = value
            value?.let {
                binding.tvFootnote.text = it
                binding.tvFootnote.isVisible = true
            }
        }

    var maxCount: Int = 0
        set(value) {
            field = value
            binding.tcContent.tilContainer.counterMaxLength = value
            binding.tcContent.tieEdittext.filters += InputFilter.LengthFilter(value)      // Edittext 의 max 값 추가
        }

}