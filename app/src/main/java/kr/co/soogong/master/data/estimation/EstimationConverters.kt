package kr.co.soogong.master.data.estimation

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
    fun imagePathListToString(list: List<ImagePath>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun stringToImagePathList(imagePathString: String): List<ImagePath> {
        return Gson().fromJson(imagePathString, object : TypeToken<List<ImagePath>>() {}.type)
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