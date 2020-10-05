package kr.co.soogong.master.ui.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import kr.co.soogong.master.BR
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentProfileBinding
import kr.co.soogong.master.ext.createLabelToggle
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.getRepository
import timber.log.Timber

class ProfileFragment : BaseFragment<FragmentProfileBinding>(
    R.layout.fragment_profile
) {
    private val viewModel: ProfileViewModel by viewModels {
        ProfileViewModelFactory(getRepository(requireContext()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(TAG).d("onViewCreated: ")

        initLayout()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            setVariable(BR.vm, viewModel)
            lifecycleOwner = viewLifecycleOwner

            viewModel.categories.observe(viewLifecycleOwner, { list ->
                if (!list.isNullOrEmpty()) {
                    masterCategoryGroup.removeAllViews()

                    for (item in list) {
                        val view = createLabelToggle(
                            requireContext(),
                            item,
                            checked = true,
                            clickable = false
                        )
                        masterCategoryGroup.addView(view)
                    }
                }
            })
        }
    }

    override fun onStart() {
        super.onStart()
        Timber.tag(TAG).d("onStart: ")
        viewModel.requestUserProfile()
    }

    companion object {
        private const val TAG = "ProfileFragment"
        fun newInstance(): ProfileFragment {
            val args = Bundle()

            val fragment = ProfileFragment()
            fragment.arguments = args
            return fragment
        }
    }
}