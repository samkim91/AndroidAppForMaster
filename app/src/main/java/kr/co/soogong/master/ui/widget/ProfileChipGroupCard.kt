package kr.co.soogong.master.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kr.co.soogong.master.R
import kr.co.soogong.master.data.model.major.BusinessType
import kr.co.soogong.master.data.model.major.Project
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

    var detail: String? = ""
        set(value) {
            field = value
            with(binding.detail) {
                visibility = if (value.isNullOrEmpty()) View.GONE else View.VISIBLE
                text = value
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

    var chipGroupWithTitle: List<BusinessType>? = emptyList()
        set(value) {
            field = value
            if (!value.isNullOrEmpty()) {
                binding.chipGroupContainer.removeAllViews()
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
                    titleTextView.setTextAppearance(R.style.medium_text_style_regular)

                    binding.chipGroupContainer.addView(titleTextView, params)
                    addChipGroup(item.projects)
                }
            }
        }

    var chipGroupWithoutTitle: List<String>? = emptyList()
        set(value) {
            field = value
            if (!value.isNullOrEmpty()) {
                newBadgeVisible = false
                binding.subTitle.visibility = View.GONE

                addChipGroup(value)
            }
        }

    private fun addChipGroup(items: List<Any>?) {
        binding.chipGroupContainer.removeAllViews()

        val chipGroup = ChipGroup(context)
        val params = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        items?.map { item ->
            val chip = Chip(context)
            chip.text = when (item) {
                is Project -> item.name
                is String -> item
                else -> item.toString()
            }
            chip.setTextColor(resources.getColor(R.color.text_basic_color, null))
            chip.setTextAppearance(R.style.medium_text_style_regular)

            chipGroup.addView(chip)
        }

        params.setMargins(0, 12, 0, 0)

        binding.chipGroupContainer.addView(chipGroup, params)
    }

    fun addDefaultButtonClickListener(listener: OnClickListener) {
        binding.defaultButton.setOnClickListener(listener)
    }
}