package kr.co.soogong.master.ui.widget

import androidx.core.view.children
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.google.android.material.chip.Chip

object TitleChipGroupReverseBinding {
    @JvmStatic
    @BindingAdapter("checkedChip")
    fun setTitleChipGroupCheckedChip(view: TitleChipGroup, checkedChipText: String?) {
        val oldChip = view.chipGroup.checkedChipId
        val oldChipText = view.chipGroup.findViewById<Chip>(oldChip)?.text.toString()

        if (!checkedChipText.isNullOrEmpty() && oldChipText != checkedChipText) {
            view.chipGroup.children.forEach {
                val chip = it as? Chip
                if(chip?.text.toString() == checkedChipText) chip?.isChecked = true
            }
        }
    }

    @JvmStatic
    @BindingAdapter("checkedChipAttrChanged")
    fun setTitleChipGroupInverseBindingListener(
        view: TitleChipGroup,
        listener: InverseBindingListener?,
    ) {
        view.chipGroup.setOnCheckedChangeListener { _, _ ->
            listener?.onChange()
        }
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "checkedChip", event = "checkedChipAttrChanged")
    fun getTitleChipGroupCheckedChip(view: TitleChipGroup): String {
        val chip = view.chipGroup.findViewById<Chip>(view.chipGroup.checkedChipId)
        return chip?.text.toString()
    }
}