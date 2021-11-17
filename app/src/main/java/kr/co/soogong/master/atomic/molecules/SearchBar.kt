package kr.co.soogong.master.atomic.molecules

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.AppCompatEditText
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import kr.co.soogong.master.databinding.ViewSearchBarBinding

class SearchBar @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle) {
    private val binding =
        ViewSearchBarBinding.inflate(LayoutInflater.from(context), this, true)

    val searchEditText: AppCompatEditText
        get() = binding.searchText

    var searchTextDisable: Boolean = false
        set(value) {
            field = value
            binding.searchText.isClickable = value
            binding.searchText.isCursorVisible = !value
            binding.searchText.isFocusable = !value
        }

    var searchText: String? = ""
        set(value) {
            field = value
            binding.searchText.setText(value)
        }

    var searchTextHint: String? = ""
        set(value) {
            field = value
            binding.searchText.hint = value
        }

    var cancelIconVisibility: Boolean = false
        set(value) {
            field = value
            binding.cancelIcon.isVisible = value
        }

    var cancelTextVisibility: Boolean = false
        set(value) {
            field = value
            binding.cancelText.isVisible = value
        }

    fun setSearchEditTextClickListener(listener: OnClickListener) {
        binding.searchText.setOnClickListener(listener)
    }

    fun setCancelIconClickListener(listener: OnClickListener) {
        binding.cancelIcon.setOnClickListener(listener)
    }

    fun setCancelTextClickListener(listener: OnClickListener) {
        binding.cancelText.setOnClickListener(listener)
    }
}