package kr.co.soogong.master.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.chip.ChipGroup
import kr.co.soogong.master.databinding.ViewTitleDropdownBinding
import kr.co.soogong.master.databinding.ViewTitleRecyclerImageBinding

class TitleDropdown @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attributeSet, defStyle) {
    private val binding =
        ViewTitleDropdownBinding.inflate(LayoutInflater.from(context), this, true)

    var title: String? = ""
        set(value) {
            field = value
            binding.title.text = value
        }

    var titleVisible: Boolean = true
        set(value) {
            field = value
            binding.title.visibility = if (value) View.VISIBLE else View.GONE
        }

    var subTitle: String? = ""
        set(value) {
            field = value
            binding.subTitle.text = value
        }

    var subTitleVisible: Boolean = false
        set(value) {
            field = value
            binding.subTitle.visibility = if (value) View.VISIBLE else View.GONE
        }

    val textView: TextView
        get() = binding.detail

    var hintText: String? = ""
        set(value) {
            field = value
            binding.detail.hint = value
        }

    var alertText: String? = ""
        set(value) {
            field = value
            binding.alert.text = value
        }

    var alertVisible: Boolean = false
        set(value) {
            field = value
            binding.alert.visibility = if (value) View.VISIBLE else View.GONE
        }

    fun addDropdownClickListener(
        onClick: () -> Unit
    ) {
        binding.detail.setOnClickListener { onClick() }
    }
}