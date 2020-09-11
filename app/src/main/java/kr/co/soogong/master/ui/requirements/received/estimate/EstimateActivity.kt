package kr.co.soogong.master.ui.requirements.received.estimate

import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.viewModels
import kr.co.soogong.master.BR
import kr.co.soogong.master.R
import kr.co.soogong.master.data.requirements.Estimate
import kr.co.soogong.master.databinding.ActivityEstimateBinding
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.ui.getRepository
import kr.co.soogong.master.ui.main.MainActivity
import kr.co.soogong.master.uiinterface.requirments.received.estimate.EstimateActivityHelper
import kr.co.soogong.master.util.EventObserver
import timber.log.Timber

class EstimateActivity : BaseActivity<ActivityEstimateBinding>(
    R.layout.activity_estimate
) {
    private val keycode by lazy {
        intent.getBundleExtra(EstimateActivityHelper.EXTRA_KEY_BUNDLE)
            ?.getString(EstimateActivityHelper.BUNDLE_KEY_RECEIVED_KEY, "") ?: ""
    }

    private val viewModel: EstimateViewModel by viewModels {
        EstimateViewModelFactory(getRepository(this), keycode)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.tag(TAG).d("onCreate: ")
        super.onCreate(savedInstanceState)
        initLayout()
    }

    private fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")
        bind {
            setVariable(BR.vm, viewModel)
            lifecycleOwner = this@EstimateActivity
            actionBar.apply {
                title.text = "견적서"
                backButton.setOnClickListener {
                    super.onBackPressed()
                }
            }

            amount.addTextChangedListener(NumberTextWatcher(amount))

            setSendClick {
                val estimate = Estimate(
                    price = amount.text.toString(),
                    contents = request.text.toString(),
                    possibleDate = findViewById<RadioButton>(dateChoice.checkedRadioButtonId).text.toString()
                )
                viewModel.onClickedSend(estimate)
            }
        }

        registerEventObserve()
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")
        viewModel.event.observe(this, EventObserver { event ->
            when (event) {
                EstimateViewModel.SEND_EVENT -> {
                    Toast.makeText(this, "견적이 발송되었습니다.\n매칭을 기다려 주세요.", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this, MainActivity::class.java).apply {
                        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    })
                    finish()
                }
            }
        })
    }

    companion object {
        private const val TAG = "EstimateActivity"
    }
}