package kr.co.soogong.master.ui.utils

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
import kr.co.soogong.master.data.user.Coordinate
import timber.log.Timber

class NaverMapHelper(
    val context: Context,
    val fragmentManager: FragmentManager,
    val frameLayout: FrameLayout,
    val coordinate: Coordinate?,
    val diameter: Int?,
) : OnMapReadyCallback {
    private lateinit var naverMap: NaverMap
    private lateinit var marker: Marker
    var circleOverlay = CircleOverlay()

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

    private fun setLocation() {
        Timber.tag(TAG).d("setLocation: ")

        coordinate?.let { coordinate ->
            diameter?.let { diameter ->
                val radius : Double = diameter / 2.0
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
                        setMarker()
                        setCircleOverlay(radius)
                    }
                )
            }
        }
    }

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

    private fun setCircleOverlay(radius: Double) {
        Timber.tag(TAG).d("setCircleOverlay: ")

        coordinate?.let { coordinate ->
            circleOverlay.map = null
            circleOverlay = CircleOverlay(
                LatLng(coordinate.latitude, coordinate.longitude),
                (radius * 1000)
            )
            circleOverlay.outlineColor = context.resources.getColor(R.color.color_22D47B, null)
            circleOverlay.color = context.resources.getColor(R.color.color_8022D47B, null)
            circleOverlay.map = naverMap
        }
    }

    fun changeServiceArea(diameter: Int) {
        val radius : Double = diameter / 2.0
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