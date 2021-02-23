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

    /*
        @TypeConverter
    fun messageToString(message: Message?): String? {
        return Gson().toJson(message)
    }

    @TypeConverter
    fun stringToMessage(messageString: String?): Message? {
        return Gson().fromJson(messageString, object : TypeToken<Message>() {}.type)
    }

    @TypeConverter
    fun priceDetailToString(priceDetail: PriceDetail): String {
        return Gson().toJson(priceDetail)
    }

    @TypeConverter
    fun stringToPriceDetail(priceDetailString: String): PriceDetail {
        return Gson().fromJson(priceDetailString, object : TypeToken<PriceDetail>() {}.type)
    }

    @TypeConverter
    fun imagePathToString(imagePath: ImagePath): String {
        return Gson().toJson(imagePath)
    }

    @TypeConverter
    fun stringToImagePath(imagePathString: String): ImagePath {
        return Gson().fromJson(imagePathString, object : TypeToken<ImagePath>() {}.type)
    }
     */
}