package kr.co.soogong.master.ui.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.chip.ChipGroup
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ViewTitleChipGroupBinding
import kr.co.soogong.master.databinding.ViewTitleEdittextBinding
import kr.co.soogong.master.generated.callback.OnCheckedChangeListener

class TitleChipGroup @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle) {
    private val binding =
        ViewTitleChipGroupBinding.inflate(LayoutInflater.from(context), this, true)

    var title: String? = ""
        set(value) {
            field = value
            binding.title.text = value
        }

    var titleVisible: Boolean = false
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

    var setOption1Text: String? = ""
        set(value) {
            field = value
            binding.option1.text = value
        }

    var setOption2Text: String? = ""
        set(value) {
            field = value
            binding.option2.text = value
        }

    var setOption3Text: String? = ""
        set(value) {
            field = value
            binding.option3.text = value
        }

    var setOption3Visibility: Boolean = false
        set(value) {
            field = value
            binding.option3.visibility = if (value) View.VISIBLE else View.GONE
        }

    fun addCheckedChangeListener(
        onCheckedChange: (
            group: ChipGroup,
            position: Int,
        ) -> Unit
    ) {
        binding.chipGroup.setOnCheckedChangeListener { group, checkedId -> onCheckedChange.invoke(group, checkedId) }
    }
}