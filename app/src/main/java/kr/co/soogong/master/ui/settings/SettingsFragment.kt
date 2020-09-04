package kr.co.soogong.master.ui.settings

import android.os.Bundle
import android.view.View
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentSettingsBinding
import kr.co.soogong.master.ui.base.BaseFragment
import timber.log.Timber

class SettingsFragment : BaseFragment<FragmentSettingsBinding>(
    R.layout.fragment_settings
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(TAG).d("onViewCreated: ")
    }

    companion object {
        private const val TAG = "SettingsFragment"

        fun newInstance(): SettingsFragment {
            return SettingsFragment()
        }
    }
}