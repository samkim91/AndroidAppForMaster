package kr.co.soogong.master.ui.requirements.done

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentRequirementsDoneBinding
import kr.co.soogong.master.ui.base.BaseFragment
import timber.log.Timber

@AndroidEntryPoint
class DoneFragment : BaseFragment<FragmentRequirementsDoneBinding>(
    R.layout.fragment_requirements_done
) {
    private val viewModel: DoneViewModel by viewModels()

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

            doneList.adapter = DoneAdapter(cardClickListener = {})

            val dividerItemDecoration = DividerItemDecoration(
                context,
                LinearLayoutManager(context).orientation
            )
            doneList.addItemDecoration(dividerItemDecoration)
        }
    }

    companion object {
        private const val TAG = "DoneFragment"

        fun newInstance(): DoneFragment {
            return DoneFragment()
        }
    }
}