package kr.co.soogong.master.presentation.ui.requirement.action.end

import android.os.Bundle
import android.widget.CalendarView
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import gun0912.tedimagepicker.builder.TedImagePicker
import kr.co.soogong.master.R
import kr.co.soogong.master.data.entity.common.AttachmentDto
import kr.co.soogong.master.databinding.ActivityEndRepairBinding
import kr.co.soogong.master.presentation.ui.base.BaseActivity
import kr.co.soogong.master.presentation.ui.base.BaseViewModel.Companion.REQUEST_FAILED
import kr.co.soogong.master.presentation.ui.requirement.action.end.EndRepairViewModel.Companion.END_REPAIR_SUCCESSFULLY
import kr.co.soogong.master.presentation.ui.requirement.action.end.EndRepairViewModel.Companion.START_IMAGE_PICKER
import kr.co.soogong.master.utility.EventObserver
import kr.co.soogong.master.utility.FileHelper
import kr.co.soogong.master.utility.PermissionHelper
import kr.co.soogong.master.utility.extension.isIntRange
import kr.co.soogong.master.utility.extension.toast
import timber.log.Timber

@AndroidEntryPoint
class EndRepairActivity : BaseActivity<ActivityEndRepairBinding>(
    R.layout.activity_end_repair
) {
    private val viewModel: EndRepairViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate: ")
        initLayout()
        registerEventObserve()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")
        bind {
            vm = viewModel
            lifecycleOwner = this@EndRepairActivity

            abHeader.setIvBackClickListener { onBackPressed() }

            saidAttachments.setImagesDeletableAdapter { viewModel.repairImages.removeAt(it) }

            cvCalender.setOnDateChangeListener { _: CalendarView, year: Int, month: Int, day: Int ->
                Timber.tag(TAG).d("setOnDateChangeListener: ${year - month - day}")
                viewModel.actualDate.value?.set(year, month, day)
            }

            bEndRepair.setOnClickListener {
                viewModel.actualPrice.value.let {
                    stiActualPrice.error = when {
                        it == null || it < 10000L -> getString(R.string.minimum_cost)
                        !it.isIntRange() -> getString(R.string.too_large_number)
                        else -> null
                    }

                    if (!stiActualPrice.error.isNullOrEmpty()) return@setOnClickListener
                }

                 viewModel.saveRepair()
            }
        }
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")

        viewModel.action.observe(this@EndRepairActivity, EventObserver { event ->
            when (event) {
                START_IMAGE_PICKER -> showImagePicker()
                END_REPAIR_SUCCESSFULLY -> {
                    toast(getString(R.string.end_estimate_succeeded))
                    onBackPressed()
                }
                REQUEST_FAILED -> toast(getString(R.string.error_message_of_request_failed))
            }
        })
    }

    private fun showImagePicker() {
        PermissionHelper.checkImagePermission(
            context = this,
            onGranted = {
                TedImagePicker.with(this)
                    .buttonBackground(R.drawable.shape_green_background_radius8)
                    .max(
                        (viewModel.maxPhoto - viewModel.repairImages.getItemCount()),
                        resources.getString(R.string.maximum_images_count, viewModel.maxPhoto)
                    )
                    .startMultiImage { uriList ->
                        if (FileHelper.isImageExtension(uriList, this) == false) {
                            toast(getString(R.string.invalid_image_extension))
                            return@startMultiImage
                        }

                        viewModel.repairImages.addAll(uriList.map {
                            AttachmentDto(
                                id = null,
                                partOf = null,
                                referenceId = null,
                                description = null,
                                s3Name = null,
                                fileName = null,
                                url = null,
                                uri = it,
                            )
                        })
                    }
            },
            onDenied = {}
        )
    }

    companion object {
        private const val TAG = "EndRepairActivity"
    }
}