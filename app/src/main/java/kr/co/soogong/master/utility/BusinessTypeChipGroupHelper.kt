package kr.co.soogong.master.utility

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isEmpty
import com.google.android.material.chip.Chip
import kr.co.soogong.master.R
import kr.co.soogong.master.data.model.major.BusinessType
import kr.co.soogong.master.ui.widget.TitleChipGroup
import kr.co.soogong.master.utility.extension.dp

// Todo.. 굉장히 코드가 더러운데.. 2 way binding을 사용하는 방법을 찾아봐야함.
object BusinessTypeChipGroupHelper {

    fun makeEntryChipGroupWithSubtitleForBusinessTypes(
        layoutInflater: LayoutInflater,
        container: LinearLayout,
        newBusinessType: BusinessType,
        viewModelBusinessTypes: ListLiveData<BusinessType>,
    ) {
        addNewBusinessType(newBusinessType, viewModelBusinessTypes)

        addBusinessTypesToContainer(layoutInflater, container, viewModelBusinessTypes)
    }

    private fun addNewBusinessType(
        newBusinessType: BusinessType,
        viewModelBusinessTypes: ListLiveData<BusinessType>
    ) {
        // 새로 선택된 업종을 viewModel에 갱신해준다.
        val tempList = viewModelBusinessTypes.value
        tempList?.removeIf { it.category?.id == newBusinessType.category?.id }
        tempList?.add(newBusinessType)
        viewModelBusinessTypes.value = tempList
    }

    fun addBusinessTypesToContainer(
        layoutInflater: LayoutInflater,
        container: LinearLayout,
        viewModelBusinessTypes: ListLiveData<BusinessType>
    ) {
        // 기존 View들을 삭제한다.
        container.removeAllViews()

        // BusinessType List만큼 View를 그려준다.
        var params = ConstraintLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(0.dp, 20.dp, 0.dp, 0.dp)

        viewModelBusinessTypes.value?.map { businessType ->
            container.addView(
                makeEntryChipGroupWithSubTitle(
                    layoutInflater = layoutInflater,
                    item = businessType,
                    closeClickListener = { titleChipGroup, chip, projectId ->
                        val chipGroup = titleChipGroup.binding.chipGroup
                        chipGroup.removeView(chip)

                        // view에서 삭제된 아이템을 code에서 찾아서 동기화
                        val tempList = viewModelBusinessTypes.value
                        tempList?.find { temp ->
                            temp.category == businessType.category
                        }.let {
                            it?.projects?.removeIf { project ->
                                project.id == projectId
                            }
                        }
                        viewModelBusinessTypes.value = tempList

                        // ChipGroup에 Chip이 하나도 없다면, 해당 ChipGroup의 Container에서도 삭제
                        if (chipGroup.isEmpty()) container.run {
                            removeView(titleChipGroup)

                            // 화면과 코드 동기화
                            val tempList1 = viewModelBusinessTypes.value
                            tempList1?.remove(businessType)
                            viewModelBusinessTypes.value = tempList1
                        }
                    }
                ), params)
        }
    }

    private fun makeEntryChipGroupWithSubTitle(
        layoutInflater: LayoutInflater,
        item: BusinessType,
        closeClickListener: (titleChipGroup: TitleChipGroup, chip: View, itemId: Int) -> Unit
    ): ViewGroup {
        val titleChipGroup = TitleChipGroup(layoutInflater.context)

        titleChipGroup.titleVisible = false
        titleChipGroup.subTitleVisible = true
        titleChipGroup.subTitle = item.category?.name
        titleChipGroup.blackSubTitle = true

        item.projects?.map { project ->
            val chip = layoutInflater.inflate(R.layout.single_chip_entry_layout, null) as Chip
            chip.setOnCloseIconClickListener {
                closeClickListener(titleChipGroup, it, project.id)
            }
            chip.text = project.name
            chip.setTextAppearanceResource(R.style.small_text_style_medium)

            titleChipGroup.addChip(chip)
        }

        return titleChipGroup
    }
}