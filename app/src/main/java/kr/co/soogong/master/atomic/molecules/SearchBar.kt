package kr.co.soogong.master.atomic.molecules

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kr.co.soogong.master.databinding.ViewSearchBarBinding

class SearchBar @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle) {
    private val binding =
        ViewSearchBarBinding.inflate(LayoutInflater.from(context), this, true)

    val searchTextContainer: TextInputLayout
        get() = binding.tilContainer

    val searchEditText: TextInputEditText
        get() = binding.tieEdittext

    var searchHint: String? = null
        set(value) {
            field = value
            searchTextContainer.isHintEnabled = true
            searchTextContainer.hint = value
        }

    var onCancelClick: OnClickListener? = null
        set(value) {
            field = value
            binding.tvCancel.isVisible = true
            binding.tvCancel.setOnClickListener(field)
        }
}