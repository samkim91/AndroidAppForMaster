@file:JvmName("ChipGroupExt")

package kr.co.soogong.master.utility.extension

import android.view.LayoutInflater
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kr.co.soogong.master.R

fun ChipGroup.onCheckedChanged(checkedChanged: (Int) -> Unit) {
    setOnCheckedChangeListener { group, checkedId ->
        checkedChanged(group.indexOfChild(group.findViewById<Chip>(checkedId)))
    }
}

fun ChipGroup.addChipEntryRounded(
    chipText: String,
    closeIconClick: () -> Unit,
) {
    this.addView(
        (LayoutInflater.from(context)
            .inflate(R.layout.chip_entry_rounded, this, false) as Chip).also { chip ->
            chip.text = chipText
            chip.setOnCloseIconClickListener {
                closeIconClick()
            }
        }
    )
}

fun ChipGroup.addChipChoiceRounded(
    chipText: String,
    chipClick: () -> Unit,
) {
    this.addView(
        (LayoutInflater.from(context)
            .inflate(R.layout.chip_choice_rounded, this, false) as Chip).also { chip ->
            chip.text = chipText
            chip.setOnClickListener {
                chipClick()
            }
        }
    )
}
