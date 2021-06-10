package kr.co.soogong.master.utility

import android.content.Context
import android.location.Geocoder
import timber.log.Timber

object LocationHelper {
    const val TAG = "LocationHelper"

    fun changeAddressToLatLng(context: Context, address: String): Map<String, Double> {
        val result = HashMap<String, Double>()

        Geocoder(context).getFromLocationName(address, 3)?.let { addresses ->
            result["latitude"] = addresses.first().latitude
            result["longitude"] = addresses.first().longitude
        }
        Timber.tag(TAG).d("changeAddressToLatLng: $result")

        return result
    }
}