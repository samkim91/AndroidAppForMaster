package kr.co.soogong.master.data.model.requirement

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class EstimationConverters {
    @TypeConverter
    fun transmissionsToString(transmissions: Transmissions): String {
        return Gson().toJson(transmissions)
    }

    @TypeConverter
    fun stringToTransmissions(transmissionsString: String): Transmissions {
        return Gson().fromJson(transmissionsString, object : TypeToken<Transmissions>() {}.type)
    }

    @TypeConverter
    fun imagePathListToString(list: ArrayList<ImagePath>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun stringToImagePathList(imagePathString: String): ArrayList<ImagePath> {
        return Gson().fromJson(imagePathString, object : TypeToken<ArrayList<ImagePath>>() {}.type)
    }

    @TypeConverter
    fun additionalInfoToString(additionalInfoList: List<AdditionalInfo>): String {
        return Gson().toJson(additionalInfoList)
    }

    @TypeConverter
    fun stringToAdditionalInfoList(imagePathString: String): List<AdditionalInfo> {
        return Gson().fromJson(imagePathString, object : TypeToken<List<AdditionalInfo>>() {}.type)
    }
}