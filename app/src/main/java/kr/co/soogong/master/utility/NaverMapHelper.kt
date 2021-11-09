package kr.co.soogong.master.utility

import android.content.Context
import android.widget.FrameLayout
import androidx.fragment.app.FragmentManager
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.CircleOverlay
import com.naver.maps.map.overlay.Marker
import kr.co.soogong.master.R
import kr.co.soogong.master.data.model.profile.Coordinate
import timber.log.Timber

class NaverMapHelper(
    val context: Context,
    val fragmentManager: FragmentManager,
    val frameLayout: FrameLayout,
    val coordinate: Coordinate?,
    val radius: Int?,
) : OnMapReadyCallback {
    lateinit var naverMap: NaverMap
    private var marker = Marker()
    private var circleOverlay = CircleOverlay()

    init {
        Timber.tag(TAG).d("init: $coordinate, $radius")
        PermissionHelper.checkLocationPermission(
            context = context,
            onGranted = {
                val mapFragment = fragmentManager.findFragmentById(frameLayout.id) as MapFragment?
                    ?: MapFragment.newInstance().also {
                        fragmentManager.beginTransaction().add(frameLayout.id, it).commit()
                    }

                mapFragment.getMapAsync(this)
            },
            onDenied = { }
        )
    }

    override fun onMapReady(naverMap: NaverMap) {
        Timber.tag(TAG).d("onMapReady: $coordinate, $radius")
        this.naverMap = naverMap
        setLocation(coordinate, radius)
    }

    // 카메라 위치를 잡아주는 메소드
    fun setLocation(coordinate: Coordinate?, radius: Int?) {
        Timber.tag(TAG).d("setLocation: $coordinate, $radius")
        coordinate?.let { c ->
            naverMap.moveCamera(
                CameraUpdate.scrollTo(
                    LatLng(
                        c.latitude!!,
                        c.longitude!!
                    )
                )
            )
            setMarker(c)

            radius?.let { r ->
                naverMap.moveCamera(CameraUpdate.zoomTo(ZoomHelper(r))
                    .finishCallback {
                        setCircleOverlay(c, r)
                    }
                )
            }
        }
    }

    // 마커를 그려주는 메소드
    private fun setMarker(coordinate: Coordinate) {
        Timber.tag(TAG).d("setMarker: $coordinate")
        coordinate.let { c ->
            if (marker.isAdded) marker.map = null
            marker = Marker(LatLng(c.latitude!!, c.longitude!!))
            marker.width = Marker.SIZE_AUTO
            marker.height = Marker.SIZE_AUTO
            marker.map = naverMap
        }
    }

    // 서클을 그려주는 메소드
    private fun setCircleOverlay(coordinate: Coordinate, radius: Int) {
        Timber.tag(TAG).d("setCircleOverlay: $coordinate, $radius")
        if (radius == 0) return

        coordinate.let { c ->
            if (circleOverlay.isAdded) circleOverlay.map = null
            circleOverlay = CircleOverlay(LatLng(c.latitude!!, c.longitude!!), (radius * 1000.0))
            circleOverlay.outlineColor = context.resources.getColor(R.color.c_22D47B, null)
            circleOverlay.color = context.resources.getColor(R.color.c_8022D47B, null)
            circleOverlay.map = naverMap
        }
    }

    companion object {
        private const val TAG = "NaverMapHelper"
    }
}