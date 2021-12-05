package kr.co.soogong.master.ui.profile.detail

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.data.common.ButtonTheme
import kr.co.soogong.master.databinding.ActivityEditProfileContainerBinding
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.uihelper.profile.EditProfileContainerActivityHelper
import kr.co.soogong.master.uihelper.profile.EditProfileContainerFragmentHelper
import timber.log.Timber

@AndroidEntryPoint
class EditProfileContainerActivity : BaseActivity<ActivityEditProfileContainerBinding>(
    R.layout.activity_edit_profile_container
) {
    private val pageName: String by lazy {
        EditProfileContainerActivityHelper.getPageName(intent)
    }

    private val itemId: Int? by lazy {
        EditProfileContainerActivityHelper.getItemId(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate: ")
        initLayout()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: $pageName / $itemId")

        bind {
            buttonThemeSave = ButtonTheme.Primary

            with(abHeader) {
                title = pageName
                setButtonBackClickListener { super.onBackPressed() }
            }
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.fcv_container,
                EditProfileContainerFragmentHelper.getFragment(pageName, itemId)).commit()
    }

    fun setSaveButtonClickListener(onClick: () -> Unit) {
        binding.bfSave.onButtonClick = View.OnClickListener {
            onClick()
        }
    }

    fun setSaveButtonEnabled(enabled: Boolean) {
        binding.bfSave.isEnabled = enabled
    }

    companion object {
        private const val TAG = "EditProfileDetailActivity"

    }
}