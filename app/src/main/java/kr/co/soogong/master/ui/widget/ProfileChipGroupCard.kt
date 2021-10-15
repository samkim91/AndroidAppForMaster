package kr.co.soogong.master.ui.widget

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kr.co.soogong.master.R
import kr.co.soogong.master.data.dto.profile.MasterConfigDto
import kr.co.soogong.master.data.model.major.Major
import kr.co.soogong.master.data.model.major.Project
import kr.co.soogong.master.data.model.profile.FlexibleCostCodeTable
import kr.co.soogong.master.data.model.profile.OtherInfoCodeTable
import kr.co.soogong.master.databinding.ViewProfileChipGroupCardBinding
import kr.co.soogong.master.utility.ButtonHelper

class ProfileChipGroupCard @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle) {

    private val binding =
        ViewProfileChipGroupCardBinding.inflate(LayoutInflater.from(context), this, true)

    var title: String? = ""
        set(value) {
            field = value
            binding.title.text = value
        }

    var titleVisible: Boolean = true
        set(value) {
            field = value
            binding.title.visibility = if (value) VISIBLE else GONE
        }

    var newBadgeVisible: Boolean = true
        set(value) {
            field = value
            binding.newBadge.visibility = if (value) VISIBLE else GONE
        }

    var subTitle: String? = ""
        set(value) {
            field = value
            with(binding.subTitle) {
                if (!value.isNullOrEmpty() && (chipGroupWithTitle.isNullOrEmpty())) {
                    visibility = View.VISIBLE
                    text = value
                } else {
                    visibility = View.GONE
                }
            }
        }

    var subTitleVisible: List<Any>? = emptyList()
        set(value) {
            field = value
            if (!value.isNullOrEmpty()) binding.subTitle.visibility =
                View.GONE else binding.subTitle.visibility = View.VISIBLE
        }

    var detail: List<MasterConfigDto>? = emptyList()
        set(value) {
            field = value
            binding.detail.isVisible = false
            value?.find { masterConfigDto ->
                masterConfigDto.code == OtherInfoCodeTable.code
            }?.let {
                binding.detail.isVisible = true
                binding.detail.text = it.value
            }
        }

    // 등록하기 <-> 수정하기 버튼 셋
    var defaultButtonByList: List<Any>? = emptyList()
        set(value) {
            field = value
            if (value.isNullOrEmpty()) {
                ButtonHelper.setRegisteringButton(binding.defaultButton)
            } else {
                ButtonHelper.setModifyingButton(binding.defaultButton)
            }
        }

    // 등록하기 <-> 수정하기 버튼 셋
    var defaultButtonByFlag: Boolean? = null
        set(value) {
            field = value
            if (value == null) {
                ButtonHelper.setRegisteringButton(binding.defaultButton)
            } else {
                ButtonHelper.setModifyingButton(binding.defaultButton)
            }
        }

    var chipGroupWithTitle: List<Major>? = emptyList()
        set(value) {
            field = value
            binding.chipGroupContainer.removeAllViews()

            if (!value.isNullOrEmpty()) {
                newBadgeVisible = false
                binding.subTitle.visibility = View.GONE

                value.map { item ->
                    val titleTextView = AppCompatTextView(context)
                    val params = LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    params.setMargins(0, 16, 0, 0)

                    titleTextView.text = item.category?.name
                    titleTextView.setTextColor(resources.getColor(R.color.text_basic_color, null))
                    titleTextView.setTextAppearance(R.style.text_style_16sp_regular)

                    binding.chipGroupContainer.addView(titleTextView, params)
                    addChipGroup(item.projects)
                }
            }
        }

    var chipGroupWithoutTitle: List<MasterConfigDto>? = emptyList()
        set(value) {
            field = value
            binding.chipGroupContainer.removeAllViews()

            if (!value.isNullOrEmpty()) {
                newBadgeVisible = false
                binding.subTitle.visibility = View.GONE

                addChipGroup(value)
            }
        }

    var chipGroupWithoutTitleByFlag: Boolean? = null
        set(value) {
            field = value
            binding.chipGroupContainer.removeAllViews()

            if (value != null) {
                newBadgeVisible = false
                binding.subTitle.visibility = View.GONE

                if (value == true) addChipGroup(listOf("가능")) else addChipGroup(listOf("불가능"))
            }
        }

    private fun addChipGroup(items: List<Any?>?) {
        val chipGroup = ChipGroup(context)
        val params = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        items?.map { item ->
            item?.let {
                val chip = Chip(context)
                chip.text = when (it) {
                    is Project -> it.name
                    is MasterConfigDto -> if (it.groupCode == FlexibleCostCodeTable.code) {
                        if (it.code == OtherInfoCodeTable.code) return@let
                        "${it.name} ${it.value}"
                    } else {
                        "${it.name}"
                    }
                    else -> it.toString()
                }
                chip.setTextColor(resources.getColor(R.color.text_basic_color, null))
                chip.setTextAppearance(R.style.text_style_16sp_regular)
                chip.chipBackgroundColor =
                    ColorStateList.valueOf(context.getColor(R.color.color_E3E3E3))

                chipGroup.addView(chip)
            }
        }

        params.setMargins(0, 12, 0, 0)

        binding.chipGroupContainer.addView(chipGroup, params)
    }

    fun addDefaultButtonClickListener(listener: OnClickListener) {
        binding.defaultButton.setOnClickListener(listener)
    }
}