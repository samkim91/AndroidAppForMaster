package kr.co.soogong.master.atomic.molecules

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.google.android.material.chip.Chip
import kr.co.soogong.master.R
import kr.co.soogong.master.data.common.CodeTable
import kr.co.soogong.master.data.dto.profile.MasterConfigDto
import kr.co.soogong.master.data.dto.profile.ProjectDto
import kr.co.soogong.master.databinding.ViewHeadlineButtonChipGroupBinding

class HeadlineButtonChipGroup @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyle) {

    private val binding =
        ViewHeadlineButtonChipGroupBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        setButtonStatus()
    }

    var title: String? = null
        set(value) {
            field = value
            value?.let { binding.tvTitle.text = it }
        }

    var hint: String? = null
        set(value) {
            field = value
            binding.tvHint.isVisible = !value.isNullOrEmpty()
            value?.let { binding.tvHint.text = it }
        }

    var chips: List<String>? = null
        set(value) {
            field = value
            value?.let {
                hint = null
                generateChips()
                setButtonStatus()
            }
        }

    var onButtonClick: OnClickListener? = null
        set(value) {
            field = value
            value?.let { binding.tvButton.setOnClickListener(it) }
        }

    private fun generateChips() {
        binding.cgChips.isVisible = true
        binding.cgChips.removeAllViews()

        chips?.map { item ->
            (LayoutInflater.from(context).inflate(R.layout.chip_choice_rounded,
                binding.cgChips,
                false) as Chip).let { chip ->
                chip.text = item
                binding.cgChips.addView(chip)
            }
        }
    }

    private fun setButtonStatus() {
        with(binding.tvButton) {
            if (chips.isNullOrEmpty()) {
                text = resources.getString(R.string.register)
                setTextColor(ResourcesCompat.getColor(resources, R.color.brand_green, null))
            } else {
                text = resources.getString(R.string.editing)
                setTextColor(ResourcesCompat.getColor(resources, R.color.brand_red, null))
            }
        }
    }

    companion object {

        @JvmStatic
        @BindingAdapter("majorsToChips")
        fun HeadlineButtonChipGroup.convertMajorsToChips(majors: List<ProjectDto>?) {
            this.chips = majors?.map { it.name!! }
        }

        @JvmStatic
        @BindingAdapter("masterConfigsToChips")
        fun HeadlineButtonChipGroup.convertMasterConfigsToChips(masterConfigs: List<MasterConfigDto>?) {
            this.chips = masterConfigs?.map { config ->
                when (config.code) {
                    // 현장 가격 변동 요인은 name + value
                    CodeTable.TRAVEL_COST.code, CodeTable.CRANE_USAGE.code, CodeTable.PACKAGE_COST.code -> config.name!! + " " + config.value!!
                    // 기타 변동 사항은 name
                    else -> config.name!!
                }
            }
        }
    }
}