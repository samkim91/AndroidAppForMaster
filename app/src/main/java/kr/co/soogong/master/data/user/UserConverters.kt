package kr.co.soogong.master.data.user

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class UserConverters {
    @TypeConverter
    fun mapToString(list: List<String>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun stringToMap(listString: String): List<String> {
        return Gson().fromJson(listString, object : TypeToken<List<String>>() {}.type)
    }
}