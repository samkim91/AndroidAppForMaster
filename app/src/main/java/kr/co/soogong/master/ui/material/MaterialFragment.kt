package kr.co.soogong.master.ui.material

import android.os.Bundle
import android.view.View
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentMaterialBinding
import kr.co.soogong.master.ui.base.BaseFragment
import timber.log.Timber

class MaterialFragment : BaseFragment<FragmentMaterialBinding>(
    R.layout.fragment_material
) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(TAG).d("onViewCreated: ")

        initLayout()
    }

    override fun initLayout() {
    }

    companion object {
        private const val TAG = "MaterialFragment"

        fun newInstance(): MaterialFragment {
            val args = Bundle()

            val fragment = MaterialFragment()
            fragment.arguments = args
            return fragment
        }
    }
}