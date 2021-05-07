package kr.co.soogong.master.ui.profile.edit.pricebyproject

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentEditPriceByProjectBinding
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.utils.EditProfileContainerFragmentHelper.ADD_PORTFOLIO
import kr.co.soogong.master.ui.utils.EditProfileContainerFragmentHelper.ADD_PRICE_BY_PROJECTS
import kr.co.soogong.master.ui.utils.EditProfileContainerFragmentHelper.EDIT_PRICE_BY_PROJECTS
import timber.log.Timber

@AndroidEntryPoint
class EditPriceByProjectFragment : BaseFragment<FragmentEditPriceByProjectBinding>(
    R.layout.fragment_edit_price_by_project
) {
    private val pageName: String by lazy {
        arguments?.getString(PAGE_NAME) ?: ADD_PORTFOLIO
    }
    private val priceByProjectId: Int by lazy {
        arguments?.getInt(PRICE_BY_PROJECT_ID) ?: -1
    }

    private val viewModel: EditPriceByProjectViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(TAG).d("onViewCreated: ")
        initLayout()
        registerEventObserve()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            vm =  viewModel
            lifecycleOwner = viewLifecycleOwner

            when (pageName) {
                ADD_PRICE_BY_PROJECTS -> {
                    with(defaultButton) {
                        text = getString(R.string.writing_done)
                        setOnClickListener {
                            viewModel.savePriceByProject(-1)
                        }
                    }
                }
                EDIT_PRICE_BY_PROJECTS -> {
                    viewModel.getPriceByProject(priceByProjectId)
                    with(defaultButton) {
                        text = getString(R.string.modifying_done)
                        setOnClickListener {
                            viewModel.savePriceByProject(priceByProjectId)
                        }
                    }
                }
            }
        }
    }

    private fun registerEventObserve() {
        Timber.tag(TAG).d("registerEventObserve: ")

    }

    companion object {
        private const val TAG = "EditPriceByProjectFragment"
        private const val PAGE_NAME = "PAGE_NAME"
        private const val PRICE_BY_PROJECT_ID = "PRICE_BY_PROJECT_ID"

        fun newInstance(pageName: String, priceByProjectId: Int?): EditPriceByProjectFragment {
            val fragment = EditPriceByProjectFragment()
            val args = Bundle()
            args.putString(PAGE_NAME, pageName)
            args.putInt(PRICE_BY_PROJECT_ID, priceByProjectId ?: -1)
            fragment.arguments = args
            return fragment
        }
    }
}