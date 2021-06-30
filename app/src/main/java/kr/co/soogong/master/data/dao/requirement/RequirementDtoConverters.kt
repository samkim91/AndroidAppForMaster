package kr.co.soogong.master.data.dao.requirement

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kr.co.soogong.master.data.dto.AttachmentDto
import kr.co.soogong.master.data.dto.requirement.estimation.EstimationDto
import kr.co.soogong.master.data.dto.requirement.qna.RequirementQnaDto
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
    fun attachmentListToString(list: List<AttachmentDto>?): String? {
        return list?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun stringToAttachmentList(attachmentListString: String?): List<AttachmentDto>? {
        return attachmentListString?.let {
            Gson().fromJson(it, object : TypeToken<List<AttachmentDto>>() {}.type)
        }
    }

    @TypeConverter
    fun attachmentToString(attachment: AttachmentDto?): String? {
        return attachment?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun stringToAttachment(attachmentString: String?): AttachmentDto? {
        return attachmentString?.let {
            Gson().fromJson(it, object : TypeToken<AttachmentDto>() {}.type)
        }
    }

    @TypeConverter
    fun requirementQnasToString(list: List<RequirementQnaDto>?): String? {
        return list?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun stringToRequirementQnas(requirementQnasString: String?): List<RequirementQnaDto>? {
        return requirementQnasString?.let {
            Gson().fromJson(it, object : TypeToken<List<RequirementQnaDto>>() {}.type)
        }
    }
}