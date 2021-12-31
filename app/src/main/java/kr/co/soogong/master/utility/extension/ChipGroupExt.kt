@file:JvmName("ChipGroupExt")

package kr.co.soogong.master.utility.extension

import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

fun ChipGroup.onCheckedChanged(checkedChanged: (Int) -> Unit) {
    setOnCheckedChangeListener { group, checkedId ->
        checkedChanged(group.indexOfChild(group.findViewById<Chip>(checkedId)))
    }
}
