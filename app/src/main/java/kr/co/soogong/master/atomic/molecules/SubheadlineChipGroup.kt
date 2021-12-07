package kr.co.soogong.master.atomic.molecules

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ViewSubheadlineChipGroupBinding

class SubheadlineChipGroup @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle) {
    val binding =
        ViewSubheadlineChipGroupBinding.inflate(LayoutInflater.from(context), this, true)

    var subheadline: String? = ""
        set(value) {
            field = value
            binding.tvSubheadline.text = value
        }

    val container: ChipGroup
        get() = binding.cgContainer

    companion object {

        fun initChips(
            context: Context,
            layoutInflater: LayoutInflater,
            subheadlineChipGroup: SubheadlineChipGroup,
            list: List<String>,
        ) {
            val halfOfScreen: Int = ((context.resources.displayMetrics).widthPixels / 2.25).toInt()

            subheadlineChipGroup.container.isSingleSelection = true
            subheadlineChipGroup.container.isSelectionRequired = true

            list.map { item ->
                val chip = layoutInflater.inflate(R.layout.chip_choice_rectangular,
                    subheadlineChipGroup,
                    false) as Chip
                chip.text = item
                chip.width = halfOfScreen

                subheadlineChipGroup.container.addView(chip)
            }
        }
    }
}