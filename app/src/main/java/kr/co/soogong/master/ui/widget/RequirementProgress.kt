package kr.co.soogong.master.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.SeekBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import kr.co.soogong.master.data.dto.requirement.RequirementDto
import kr.co.soogong.master.data.model.requirement.*
import kr.co.soogong.master.databinding.ViewRequirementProgressBinding
import timber.log.Timber

class RequirementProgress @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attributeSet, defStyle) {
    private var binding =
        ViewRequirementProgressBinding.inflate(LayoutInflater.from(context), this, true)

    var startingText: String? = ""
        set(value) {
            field = value
            binding.startingText.isVisible = !value.isNullOrEmpty()
            binding.startingText.text = value
        }

    var endingText: String? = ""
        set(value) {
            field = value
            binding.endingText.isVisible = !value.isNullOrEmpty()
            binding.endingText.text = value
        }

    var progress: Int = Int.MIN_VALUE
        set(value) {
            field = value
            binding.seekBar.progress = value
        }

    var max: Int = Int.MIN_VALUE
        set(value) {
            field = value
            binding.seekBar.max = value
        }

    // 수공비서 퍼널 (1.실측 요청 -> 2.실측 예정 -> 3.실측 완료 -> 4.시공 예정)
    // 일반 퍼널 (1.견적 요청 -> 2.매칭 대기 -> 3.시공 예정 -> 4.고객 완료 요청)
    fun initLayout(requirementDto: RequirementDto) {
        max = 5
        progress = convertStatusToProgress(requirementDto)
    }

    private fun convertStatusToProgress(requirementDto: RequirementDto): Int =
        when (RequirementStatus.getStatusFromRequirement(requirementDto)) {
            Requested -> 1
            Estimated -> 2
            Repairing -> 3
            RequestFinish -> 4
            else -> max
        }

    private fun convertProgressToStatus(progress: Int): String =
        when (progress) {
            1 -> Requested.inKorean
            2 -> Estimated.inKorean
            3 -> Repairing.inKorean
            4 -> RequestFinish.inKorean
            else -> ""
        }

    fun setOnStatusChangeListener() {
        with(binding) {
            seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    if (progress == 0 || progress == max) {
                        indicator.isVisible = false
                        if (progress == max) endingPoint.isEnabled = true
                        return
                    } else {
                        indicator.isVisible = true
                        endingPoint.isEnabled = false
                    }

                    val pos = (seekBar!!.thumb.bounds.left - 22).toFloat()
                    Timber.tag(TAG).d("position: $pos")
                    indicatorText.translationX = pos
                    indicatorLine.translationX = pos

                    indicatorText.text = convertProgressToStatus(progress)
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {}

                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            })
        }
    }

    companion object {
        private const val TAG = "RequirementProgress"

        const val GENERAL_TYPE = 100
        const val SECRETARY_TYPE = 200
    }
}