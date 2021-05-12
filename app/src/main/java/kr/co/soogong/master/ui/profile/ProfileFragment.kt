package kr.co.soogong.master.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import gun0912.tedimagepicker.builder.TedImagePicker
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentProfileBinding
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.utils.PermissionHelper
import kr.co.soogong.master.uiinterface.profile.EditProfileContainerActivityHelper
import kr.co.soogong.master.uiinterface.profile.EditProfileContainerFragmentHelper.EDIT_FLEXIBLE_COST
import kr.co.soogong.master.uiinterface.profile.EditProfileContainerFragmentHelper.EDIT_OTHER_FLEXIBLE_OPTIONS
import kr.co.soogong.master.uiinterface.profile.EditProfileWithCardActivityHelper
import kr.co.soogong.master.uiinterface.profile.EditProfileWithCardActivityHelper.PORTFOLIO
import kr.co.soogong.master.uiinterface.profile.EditProfileWithCardActivityHelper.PRICE_BY_PROJECTS
import kr.co.soogong.master.uiinterface.profile.EditRequiredInformationActivityHelper
import kr.co.soogong.master.uiinterface.profile.MyReviewsActivityHelper
import kr.co.soogong.master.util.extension.toast
import timber.log.Timber

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(
    R.layout.fragment_profile
) {
    private val viewModel: ProfileViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(TAG).d("onViewCreated: ")

        initLayout()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")


        bind {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner

            reviewBox.setOnClickListener {
                startActivity(
                    Intent(
                        MyReviewsActivityHelper.getIntent(
                            requireContext()
                        )
                    )
                )
            }

            editRequiredInfo.setOnClickListener {
                startActivity(
                    Intent(
                        EditRequiredInformationActivityHelper.getIntent(
                            requireContext()
                        )
                    )
                )
            }

            portfolio.addDefaultButtonClickListener {
                startActivity(
                    Intent(
                        EditProfileWithCardActivityHelper.getIntent(
                            requireContext(),
                            PORTFOLIO
                        )
                    )
                )
            }

            priceByProject.addDefaultButtonClickListener {
                startActivity(
                    Intent(
                        EditProfileWithCardActivityHelper.getIntent(
                            requireContext(),
                            PRICE_BY_PROJECTS
                        )
                    )
                )
            }

            profileImage.addDefaultButtonClickListener {
                PermissionHelper.checkImagePermission(context = requireContext(),
                    onGranted = {
                                TedImagePicker.with(requireContext())
                                    .buttonBackground(R.drawable.shape_fill_green_background)
                                    .start {
                                    // todo.. 업로드 하고 내려온 이미지를 보여주는 것으로 바꿔야함.
                                            uri -> viewModel.profileImage.value = uri
                                    }
                    },
                    onDenied = {
                        requireContext().toast(getString(R.string.permission_denied_message))
                    }
                )
            }

            flexibleCost.addDefaultButtonClickListener {
                startActivity(
                    Intent(
                        EditProfileContainerActivityHelper.getIntent(
                            requireContext(),
                            EDIT_FLEXIBLE_COST
                        )
                    )
                )
            }

            otherFlexibleOptions.addDefaultButtonClickListener {
                startActivity(
                    Intent(
                        EditProfileContainerActivityHelper.getIntent(
                            requireContext(),
                            EDIT_OTHER_FLEXIBLE_OPTIONS
                        )
                    )
                )
            }

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