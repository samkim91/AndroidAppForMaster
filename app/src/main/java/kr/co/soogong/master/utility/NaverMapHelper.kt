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
    private lateinit var naverMap: NaverMap
    private lateinit var marker: Marker
    private var circleOverlay = CircleOverlay()

    init {
        val mapFragment = fragmentManager.findFragmentById(frameLayout.id) as MapFragment?
            ?: MapFragment.newInstance().also {
                fragmentManager.beginTransaction().add(frameLayout.id, it).commit()
            }

        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
        setLocation()
    }

    // 맵에 초기 위치를 잡아주는 메소드
    private fun setLocation() {
        Timber.tag(TAG).d("setLocation: ")

        coordinate?.let { coordinate ->
            radius?.let { radius ->
                // 경위도 위치로 포커스 이동
                naverMap.moveCamera(
                    CameraUpdate.scrollTo(
                        LatLng(
                            coordinate.latitude,
                            coordinate.longitude
                        )
                    )
                )
                // 줌, 마커와, 서클 등을 조절
                naverMap.moveCamera(CameraUpdate.zoomTo(ZoomHelper(radius))
                    .finishCallback {
                        setMarker()
                        setCircleOverlay(radius)
                    }
                )
            }
        }
    }

    // 마커를 그려주는 메소드
    private fun setMarker() {
        Timber.tag(TAG).d("setMarker: ")

        coordinate?.let { coordinate ->
            marker = Marker()
            marker.position = LatLng(coordinate.latitude, coordinate.longitude)
            marker.width = Marker.SIZE_AUTO
            marker.height = Marker.SIZE_AUTO
            marker.map = naverMap
        }
    }

    // 서클을 그려주는 메소드
    private fun setCircleOverlay(radius: Int) {
        Timber.tag(TAG).d("setCircleOverlay: ")

        if (radius == 0) return

        coordinate?.let { coordinate ->
            if(circleOverlay.isAdded) circleOverlay.map = null
            circleOverlay = CircleOverlay(
                LatLng(coordinate.latitude, coordinate.longitude),
                (radius * 1000.0)
            )
            circleOverlay.outlineColor = context.resources.getColor(R.color.color_22D47B, null)
            circleOverlay.color = context.resources.getColor(R.color.color_8022D47B, null)
            circleOverlay.map = naverMap
        }
    }

    fun changeServiceArea(radius: Int) {
        coordinate?.let { coordinate ->
            naverMap.moveCamera(
                CameraUpdate.scrollTo(
                    LatLng(
                        coordinate.latitude,
                        coordinate.longitude
                    )
                )
            )
            naverMap.moveCamera(CameraUpdate.zoomTo(ZoomHelper(radius))
                .finishCallback {
                    if (!marker.isAdded) setMarker()
                    setCircleOverlay(radius)
                }
            )
        }
    }

    companion object {
        private const val TAG = "NaverMapHelper"
    }
}