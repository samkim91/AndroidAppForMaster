package kr.co.soogong.master.ui.widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.SeekBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import kr.co.soogong.master.data.dto.requirement.RequirementDto
import kr.co.soogong.master.data.model.profile.CompareCodeTable
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

    var requirementDto: RequirementDto? = null
        set(value) {
            value?.let {
                field = value
                setOnStatusChangeListener()
                max = 5
                progress = convertStatusToProgress()
            }
        }

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
    private fun convertStatusToProgress(): Int {
        Timber.tag(TAG).d("convertStatusToProgress: ${requirementDto?.status}")
        return if (requirementDto?.typeCode == CompareCodeTable.code) {
            when (RequirementStatus.getStatusFromRequirement(requirementDto)) {
                Requested -> 1
                Estimated -> 2
                Repairing -> 3
                RequestFinish -> 4
                else -> max
            }
        } else {
            when (RequirementStatus.getStatusFromRequirement(requirementDto)) {
                RequestMeasure -> 1
                Measuring -> 2
                Measured -> 3
                Repairing -> 4
                else -> max
            }
        }
    }

    private fun convertProgressToStatus(progress: Int): String {
        Timber.tag(TAG).d("convertStatusToProgress: $progress")
        return if (requirementDto?.typeCode == CompareCodeTable.code) {
            when (progress) {
                1 -> Requested.inKorean
                2 -> Estimated.inKorean
                3 -> Repairing.inKorean
                4 -> RequestFinish.inKorean
                else -> ""
            }
        } else {
            when (progress) {
                1 -> RequestMeasure.inKorean
                2 -> Measuring.inKorean
                3 -> Measured.inKorean
                4 -> Repairing.inKorean
                else -> ""
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    fun setOnStatusChangeListener() {
        Timber.tag(TAG).d("setOnStatusChangeListener: ")
        with(binding) {
            seekBar.setOnTouchListener { _, _ -> return@setOnTouchListener true }

            seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    Timber.tag(TAG).d("onProgressChanged: $progress")

                    if (progress == 0 || progress == max) {
                        indicator.isVisible = false
                        if (progress == max) endingPoint.isEnabled = true
                        return
                    } else {
                        indicator.isVisible = true
                        endingPoint.isEnabled = false
                    }

                    val pos = (seekBar!!.thumb.bounds.left - 25).toFloat()
                    Timber.tag(TAG).d("${indicatorText.translationX} will be $pos")
                    indicatorText.translationX = pos
                    indicatorLine.translationX = pos
                    // TODO: 2021/08/31 progress 가 4 이상으로 가면, 레이아웃이 깨지는데 확인 필요
//                    Timber.tag(TAG).d("position: $pos")
//                    val width = seekBar!!.width - seekBar.paddingLeft - seekBar.paddingRight
//                    val thumbsPos = (seekBar.paddingLeft + width * seekBar.progress / seekBar.max - 72).toFloat()


                    indicatorText.text = convertProgressToStatus(progress)
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {}

                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            })
        }
    }

    companion object {
        private const val TAG = "RequirementProgress"
    }
}