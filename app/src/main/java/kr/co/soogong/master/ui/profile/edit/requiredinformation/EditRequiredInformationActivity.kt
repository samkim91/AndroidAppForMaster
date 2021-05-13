package kr.co.soogong.master.ui.profile.edit.requiredinformation

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivityEditRequiredInformationBinding
import kr.co.soogong.master.ui.base.BaseActivity
import timber.log.Timber

@AndroidEntryPoint
class EditRequiredInformationActivity : BaseActivity<ActivityEditRequiredInformationBinding>(
    R.layout.activity_edit_required_information
) {
    private val viewModel: EditRequiredInformationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initLayout()
        registerEventObserve()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            vm = viewModel
            lifecycleOwner = this@EditRequiredInformationActivity

            with(actionBar) {
                title.text = getString(R.string.title_edit_required_information_activity)
                backButton.setOnClickListener {
                    super.onBackPressed()
                }
            }

            briefIntroduction.addDefaultButtonClickListener{

            }
        }
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")

    }

    override fun onStart() {
        Timber.tag(TAG).d("onStart: ")
        super.onStart()
        viewModel.getRequiredInfo()

        if(viewModel.isApprovedMaster.value == true) setLayoutForApprovedMaster() else setPercentageText()
    }

    private fun setLayoutForApprovedMaster(){
        binding.requiredProfileCardPercentage.visibility = View.GONE
        binding.groupSawCheckForConfirmedMaster.visibility = View.VISIBLE
        binding.defaultButton.visibility = View.GONE
        binding.alertContainerToFillUpRequiredInformation.visibility = View.GONE
    }

    private fun setPercentageText(){
        val totalCount = 9
        var insertedCount = 0

        with(viewModel.requiredInformation){
            if(!value?.briefIntroduction.isNullOrEmpty()) insertedCount++
            if(value?.companyImages?.get(0)?.path.isNullOrEmpty()) insertedCount++
            if(!value?.businessUnitInformation?.businessUnit.isNullOrEmpty()) insertedCount++
            if(!value?.warrantyPeriod.isNullOrEmpty()) insertedCount++
            if(!value?.career.isNullOrEmpty()) insertedCount++
            if(!value?.businessRepresentativeName.isNullOrEmpty()) insertedCount++
            if(!value?.phoneNumber.isNullOrEmpty()) insertedCount++
            if(!value?.businessType.isNullOrEmpty()) insertedCount++
            if(!value?.address.isNullOrEmpty()) insertedCount++
        }

        val percentage = insertedCount / totalCount * 100

        if(percentage == 100) {
            binding.requiredProfileCardPercentage.setTextColor(resources.getColor(R.color.color_FF711D, null))
            binding.defaultButton.isEnabled = true
        } else {
            binding.requiredProfileCardPercentage.setTextColor(resources.getColor(R.color.color_1FC472, null))
            binding.defaultButton.isEnabled = false
        }
        binding.requiredProfileCardPercentage.text = "${percentage}% 등록"
    }

    companion object {
        private const val TAG = "EditRequiredInformationActivity"

    }
}