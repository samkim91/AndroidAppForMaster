package kr.co.soogong.master.ui.requirements.action.write

import android.icu.text.DecimalFormat
import android.os.Bundle
import android.text.InputType
import android.view.View
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivityWriteEstimateBinding
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.uiinterface.requirments.action.view.ViewEstimateActivityHelper
import kr.co.soogong.master.util.extension.toast
import timber.log.Timber

@AndroidEntryPoint
class WriteEstimateActivity : BaseActivity<ActivityWriteEstimateBinding>(
    R.layout.activity_write_estimate
) {
    private val estimationId: String by lazy {
        ViewEstimateActivityHelper.getEstimationId(intent)
    }

    private val viewModel: WriteEstimateViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate: ")
        initLayout()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")
        bind {
            vm = viewModel

            with(actionBar) {
                title.text = getString(R.string.write_estimate_title)
                backButton.setOnClickListener {
                    super.onBackPressed()
                }
                button.setOnClickListener {
                    viewModel.sendEstimation()
                }
            }

            requestTypeGroup.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    filterOption1.id -> {
                        amount.visibility = View.VISIBLE
                        requestDetail.visibility = View.GONE
                    }

                    filterOption2.id -> {
                        amount.visibility = View.GONE
                        requestDetail.visibility = View.VISIBLE
                        totalAmount.setEditTextBackground(getDrawable(R.drawable.shape_fill_gray_background_gray_border))
                    }
                }
            }

            amount.addTextChangedListener(afterTextChanged = {
                amount.alertVisible = amount.text.isNullOrEmpty() || amount.text.toString().toInt() < 10000
            })

            laborCost.addTextChangedListener(afterTextChanged = {
                laborCost.alertVisible = laborCost.text.isNullOrEmpty()
                setTotalAmount()
            })

            materialCost.addTextChangedListener(afterTextChanged = {
                materialCost.alertVisible = materialCost.text.isNullOrEmpty()
                setTotalAmount()
            })

            travelCost.addTextChangedListener(afterTextChanged = {
                travelCost.alertVisible = travelCost.text.isNullOrEmpty()
                setTotalAmount()
            })

        }
    }

    private fun setTotalAmount() {
        bind {
            val laborCostInt = if (!laborCost.text.isNullOrEmpty()) laborCost.text.toString().toInt() else 0
            val materialCostInt = if (!materialCost.text.isNullOrEmpty()) materialCost.text.toString().toInt() else 0
            val travelCostInt = if (!travelCost.text.isNullOrEmpty()) travelCost.text.toString().toInt() else 0

            totalAmount.text = (laborCostInt + materialCostInt + travelCostInt).toString()
        }
    }

    companion object {
        private const val TAG = "WriteEstimateActivity"
    }
}