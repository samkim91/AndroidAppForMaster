package kr.co.soogong.master.domain.requirements

import androidx.room.TypeConverter
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
}