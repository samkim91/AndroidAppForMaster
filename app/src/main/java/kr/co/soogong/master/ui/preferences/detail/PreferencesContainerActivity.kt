package kr.co.soogong.master.ui.preferences.detail

import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.ActivityPreferencesContainerBinding
import kr.co.soogong.master.ui.base.BaseActivity
import kr.co.soogong.master.uihelper.preferences.PreferencesContainerActivityHelper
import kr.co.soogong.master.uihelper.preferences.PreferencesDetailFragmentHelper
import timber.log.Timber

@AndroidEntryPoint
class PreferencesContainerActivity : BaseActivity<ActivityPreferencesContainerBinding>(
    R.layout.activity_preferences_container
) {
    private val pageName: String by lazy {
        PreferencesContainerActivityHelper.getPageName(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate: ")
        initLayout()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            lifecycleOwner = this@PreferencesContainerActivity

            with(abHeader) {
                title = pageName

                setButtonBackClickListener {
                    onBackPressed()
                }
            }
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.fcv_container,
                PreferencesDetailFragmentHelper.getFragment(pageName)).commit()
    }

    companion object {
        private const val TAG = "PreferencesContainerActivity"
    }
}