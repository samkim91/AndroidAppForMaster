package kr.co.soogong.master.ui.auth.signup.steps

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.CircleOverlay
import com.naver.maps.map.overlay.Marker
import dagger.hilt.android.AndroidEntryPoint
import kr.co.soogong.master.R
import kr.co.soogong.master.databinding.FragmentSignUpStep7Binding
import kr.co.soogong.master.ui.auth.signup.SignUpActivity
import kr.co.soogong.master.ui.auth.signup.SignUpViewModel
import kr.co.soogong.master.ui.base.BaseFragment
import kr.co.soogong.master.ui.dialog.bottomdialogrecyclerview.BottomDialogData
import kr.co.soogong.master.ui.dialog.bottomdialogrecyclerview.BottomDialogRecyclerView
import kr.co.soogong.master.ui.utils.ZoomHelper
import timber.log.Timber

@AndroidEntryPoint
class Step7Fragment : BaseFragment<FragmentSignUpStep7Binding>(
    R.layout.fragment_sign_up_step7
), OnMapReadyCallback {
    private val viewModel: SignUpViewModel by activityViewModels()
    lateinit var naverMap: NaverMap
    lateinit var marker: Marker
    var circleOverlay = CircleOverlay()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(TAG).d("onViewCreated: ")
        initLayout()
        getMapFragment()
    }

    override fun initLayout() {
        Timber.tag(TAG).d("initLayout: ")

        bind {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner

            serviceArea.addDropdownClickListener {
                Timber.tag(TAG).w("Dropdown Clicked")
                val bottomDialog =
                    BottomDialogRecyclerView("범위 선택", BottomDialogData.getServiceAreaList(),
                        itemClick = { text, value ->
                            viewModel.serviceArea.value = text
                            viewModel.serviceAreaToInt.value = value
                            setCircle(value)
                        }
                    )

                bottomDialog.show(parentFragmentManager, bottomDialog.tag)
            }

            defaultButton.setOnClickListener {
                viewModel.address.observe(viewLifecycleOwner, {
                    alertServiceAreaRequired.isVisible = it.isNullOrEmpty()
                })
                viewModel.subAddress.observe(viewLifecycleOwner, {
                    alertServiceAreaRequired.isVisible = it.isNullOrEmpty()
                })

                if (!alertServiceAreaRequired.isVisible) {
                    (activity as? SignUpActivity)?.moveToNext()
                }
            }
        }
    }

    private fun getMapFragment() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map_view) as MapFragment?
            ?: MapFragment.newInstance().also {
                childFragmentManager.beginTransaction().add(R.id.map_view, it).commit()
            }

        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
        setLocation()
    }

    private fun setLocation() {
        viewModel.latitude.value?.let { lat ->
            viewModel.longitude.value?.let { lng ->
                naverMap.moveCamera(CameraUpdate.scrollTo(LatLng(lat, lng))
                    .finishCallback {
                        setMarker(lat, lng)
                    }
                )
            }
        }
    }

    private fun setMarker(lat: Double, lng: Double) {
        marker = Marker()
        marker.position = LatLng(lat, lng)
        marker.width = Marker.SIZE_AUTO
        marker.height = Marker.SIZE_AUTO
        marker.map = naverMap
    }

    private fun setCircle(radius: Int) {
        viewModel.latitude.value?.let { lat ->
            viewModel.longitude.value?.let { lng ->
                naverMap.moveCamera(CameraUpdate.scrollTo(LatLng(lat, lng)))
                naverMap.moveCamera(CameraUpdate.zoomTo(ZoomHelper(radius))
                    .finishCallback {
                        if (!marker.isAdded) setMarker(lat, lng)
                        if (circleOverlay.isAdded) circleOverlay.map = null
                        circleOverlay = CircleOverlay(LatLng(lat, lng), (radius * 1000).toDouble())
                        circleOverlay.outlineColor = resources.getColor(R.color.color_22D47B, null)
                        circleOverlay.color = resources.getColor(R.color.color_8022D47B, null)
                        circleOverlay.map = naverMap
                    }
                )
            }
        }

    }


    companion object {
        private const val TAG = "Step7Fragment"

        fun newInstance(): Step7Fragment {
            return Step7Fragment()
        }
    }
}