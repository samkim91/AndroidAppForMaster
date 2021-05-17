package kr.co.soogong.master.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ViewTitleChipGroupBinding

class TitleChipGroup @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle) {
    val binding =
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

    var blackSubTitle: Boolean = false
        set(value) {
            // 기본적으로 회색이나, 필요에 따라서 검정색으로 변경
            field = value
            if (value) binding.subTitle.setTextColor(
                resources.getColor(
                    R.color.text_basic_color,
                    null
                )
            )
            else binding.subTitle.setTextColor(resources.getColor(R.color.text_primary_color, null))
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

    val chipGroup: ChipGroup
        get() = binding.chipGroup

    fun addChip(
        child: Chip
    ) {
        binding.chipGroup.addView(child)
    }

    fun addCheckedChangeListener(
        onCheckedChange: (
            group: ChipGroup,
            position: Int,
        ) -> Unit
    ) {
        binding.chipGroup.setOnCheckedChangeListener { group, checkedId ->
            onCheckedChange(
                group,
                checkedId
            )
        }
    }
}