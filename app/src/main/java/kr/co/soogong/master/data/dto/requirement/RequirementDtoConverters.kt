package kr.co.soogong.master.data.dto.requirement

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kr.co.soogong.master.data.dto.requirement.estimation.EstimationDto
import kr.co.soogong.master.data.model.requirement.ImagePath
import java.util.*

class RequirementDtoConverters {
    @TypeConverter
    fun timestampToDate(date: Date): Long {
        return date.let { date.time }
    }

    @TypeConverter
    fun dateToTimestamp(long: Long): Date {
        return Date(long)
    }

    @TypeConverter
    fun estimationToString(estimationDto: EstimationDto): String {
        return Gson().toJson(estimationDto)
    }

    @TypeConverter
    fun stringToEstimation(estimationString: String): EstimationDto {
        return Gson().fromJson(estimationString, object : TypeToken<EstimationDto>() {}.type)
    }

    @TypeConverter
    fun imagePathListToString(list: List<ImagePath>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun stringToImagePathList(imagePathString: String): List<ImagePath> {
        return Gson().fromJson(imagePathString, object : TypeToken<List<ImagePath>>() {}.type)
    }
}