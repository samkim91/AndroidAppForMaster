package kr.co.soogong.master.atomic.molecules

import androidx.core.view.children
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.google.android.material.chip.Chip
import kr.co.soogong.master.data.common.CodeTable

object SubheadlineChipGroupReverseBinding {
    @JvmStatic
    @BindingAdapter("checkedChipCode")
    fun setSubheadlineChipGroupCheckedChip(
        view: SubheadlineChipGroup,
        checkedChipCode: CodeTable?,
    ) {
        val oldChip = view.container.findViewById<Chip>(view.container.checkedChipId)

        if (oldChip?.text != checkedChipCode?.inKorean) {
            view.container.children.find { chip ->
                (chip as Chip).text.toString() == checkedChipCode?.inKorean
            }?.let { chip ->
                (chip as Chip).isChecked = true
            }
        }
    }

    @JvmStatic
    @BindingAdapter("checkedChipCodeAttrChanged")
    fun setSubheadlineChipGroupInverseBindingListener(
        view: SubheadlineChipGroup,
        listener: InverseBindingListener?,
    ) {
        view.container.setOnCheckedChangeListener { _, _ ->
            listener?.onChange()
        }
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "checkedChipCode", event = "checkedChipCodeAttrChanged")
    fun getSubheadlineChipGroupCheckedChip(view: SubheadlineChipGroup): CodeTable? {
        return view.container.findViewById<Chip>(view.container.checkedChipId)?.run {
            CodeTable.getCodeTableByKorean(this.text.toString())
        }
    }


//    @JvmStatic
//    @BindingAdapter("checkedChipsCode")
//    fun setSubheadlineChipGroupCheckedChips(
//        view: SubheadlineChipGroup,
//        checkedChipsCode: List<CodeTable>?,
//    ) {
//        checkedChipsCode?.map { checkedChipCode ->
//            view.container.children.find { chip ->
//                (chip as Chip).text.toString() == checkedChipCode.inKorean
//            }?.run { (this as Chip).isChecked = true }
//        }
//    }
//
//    @JvmStatic
//    @BindingAdapter("checkedChipsCodeAttrChanged")
//    fun setSubheadlineChipGroupCheckedChipsInverseBindingListener(
//        view: SubheadlineChipGroup,
//        listener: InverseBindingListener?,
//    ) {
//        view.container.setOnCheckedChangeListener { _, _ ->
//            listener?.onChange()
//        }
//    }
//
//    @JvmStatic
//    @InverseBindingAdapter(attribute = "checkedChipsCode", event = "checkedChipsCodeAttrChanged")
//    fun getSubheadlineChipGroupCheckedChips(view: SubheadlineChipGroup): List<CodeTable?> {
//        return view.container.checkedChipIds.map { chipId ->
//            view.container.findViewById<Chip>(chipId).run {
//                CodeTable.getCodeTableByKorean(this.text.toString())
//            }
//        }
//    }
}