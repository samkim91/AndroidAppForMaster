package kr.co.soogong.master.ui.profile.edit

import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivityEditProfileContainerBinding
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.uihelper.profile.EditProfileContainerFragmentHelper
import kr.co.soogong.master.uihelper.profile.EditProfileContainerActivityHelper
import timber.log.Timber

@AndroidEntryPoint
class EditProfileContainerActivity : BaseActivity<ActivityEditProfileContainerBinding>(
    R.layout.activity_edit_profile_container
) {
    private val pageName: String by lazy {
        EditProfileContainerActivityHelper.getPageName(intent)
    }

    private val itemId: Int by lazy {
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
            with(actionBar) {
                title.text = pageName
                backButton.setOnClickListener {
                    super.onBackPressed()
                }
            }
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment, EditProfileContainerFragmentHelper.getFragment(pageName, itemId)).commit()
    }

    companion object {
        private const val TAG = "EditProfileDetailActivity"

    }
}