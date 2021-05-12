package kr.co.soogong.master.ui.profile.edit.requiredinformation

import android.os.Bundle
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
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            with(actionBar){
                title.text = getString(R.string.title_edit_required_information_activity)
                backButton.setOnClickListener {
                    super.onBackPressed()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.getRequiredInformation()
    }

    companion object {
        private const val TAG = "EditRequiredInformationActivity"

    }
}