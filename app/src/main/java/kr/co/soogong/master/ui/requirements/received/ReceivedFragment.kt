package kr.co.soogong.master.ui.requirements.received

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kr.co.soogong.master.BR
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentRequirementsReceivedBinding
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.util.getRepository
import kr.co.soogong.master.util.http.HttpClient
import timber.log.Timber

class ReceivedFragment : BaseFragment<FragmentRequirementsReceivedBinding>(
    R.layout.fragment_requirements_received
) {
    private val viewModel: ReceivedViewModel by viewModels {
        ReceivedViewModelFactory(getRepository(requireContext()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Timber.tag(TAG).d("onViewCreated: ")
        bind {
            receivedList.adapter = ReceivedAdapter()
            val dividerItemDecoration = DividerItemDecoration(
                context,
                LinearLayoutManager(context).orientation
            )
            receivedList.addItemDecoration(dividerItemDecoration)
            setVariable(BR.vm, viewModel)
            lifecycleOwner = this@ReceivedFragment.viewLifecycleOwner
        }

        binding.testButton.setOnClickListener {
            HttpClient.getRequirementList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy {
                    Timber.tag(TAG).d("testButton: $it")
                }
        }
    }

    companion object {
        private const val TAG = "ReceivedFragment"
        fun newInstance(): ReceivedFragment {
            return ReceivedFragment()
        }
    }
}