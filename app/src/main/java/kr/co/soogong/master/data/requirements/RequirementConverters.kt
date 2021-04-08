package kr.co.soogong.master.data.requirements

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class RequirementConverters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun estimateToString(estimationMessage: EstimationMessage?): String? {
        return Gson().toJson(estimationMessage)
    }

    @TypeConverter
    fun stringToEstimate(estimateString: String?): EstimationMessage? {
        return Gson().fromJson(estimateString, object : TypeToken<EstimationMessage>() {}.type)
    }
}