package kr.co.soogong.master.ui.requirement.action.write

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivityMeasureBinding
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.ui.dialog.popup.CustomDialog
import kr.co.soogong.master.ui.dialog.popup.DialogData
import kr.co.soogong.master.utility.validation.ValidationHelper
import timber.log.Timber

@AndroidEntryPoint
class MeasureActivity : BaseActivity<ActivityMeasureBinding>(
    R.layout.activity_measure
) {
    private val viewModel: WriteEstimationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initLayout()
        registerEventObserve()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            lifecycleOwner = this@MeasureActivity
            vm = viewModel

            with(actionBar) {
                title.text = getString(R.string.write_estimate_title)
                backButton.setOnClickListener {
                    onBackPressed()
                }

                // TODO: 2021/09/03 사진 추가하는 로직 필요

                button.text = getString(R.string.send_estimation)
                button.setOnClickListener {
                    if (!simpleCost.alertVisible && ValidationHelper.isIntRange(viewModel.simpleCost.value!!)) viewModel.sendEstimation()
                }
            }
        }
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")
        bind {
            viewModel.simpleCost.observe(this@MeasureActivity, {
                simpleCost.alertVisible =
                    simpleCost.text.isNullOrEmpty() || simpleCost.text.toString().replace(",", "")
                        .toLong() < 10000
            })
        }
    }

    override fun onBackPressed() {
        customBackPressed()
    }

    private fun customBackPressed() {
        val dialog = CustomDialog.newInstance(
            DialogData.getCancelSendingEstimationDialogData(this),
            yesClick = {
                finish()
            },
            noClick = { }
        )

        dialog.show(supportFragmentManager, dialog.tag)
    }

    companion object {
        private const val TAG = "MeasureActivity"

    }
}