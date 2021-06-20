package kr.co.soogong.master.data.dto.requirement

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kr.co.soogong.master.data.dto.requirement.estimation.EstimationDto
import kr.co.soogong.master.data.model.requirement.ImagePath
import java.util.*

class RequirementDtoConverters {
    @TypeConverter
    fun timestampToDate(date: Date?): Long? {
        return date?.let { it.time }
    }

    @TypeConverter
    fun dateToTimestamp(long: Long?): Date? {
        return long?.let { Date(it) }
    }

    @TypeConverter
    fun estimationToString(estimationDto: EstimationDto?): String? {
        return estimationDto?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun stringToEstimation(estimationString: String?): EstimationDto? {
        return estimationString?.let {
            Gson().fromJson(it, object : TypeToken<EstimationDto>() {}.type)
        }
    }

    @TypeConverter
    fun imagePathListToString(list: List<ImagePath>?): String? {
        return list?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun stringToImagePathList(imagePathString: String?): List<ImagePath>? {
        return imagePathString?.let {
            Gson().fromJson(it, object : TypeToken<List<ImagePath>>() {}.type)
        }
    }
}