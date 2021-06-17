package kr.co.soogong.master.di

import com.google.gson.*
import com.google.gson.internal.`$Gson$Types`
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.lang.reflect.Type
import java.util.*

class NonNullListDeserializer<T> : JsonDeserializer<ArrayList<T>> {
    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): ArrayList<T> {
        if (json is JsonArray) {
            val size = json.size()
            if (size == 0) {
                return arrayListOf()
            }
            val list: ArrayList<T> = ArrayList(size)
            for (i in 0 until size) {
                val elementType: Type =
                    `$Gson$Types`.getCollectionElementType(typeOfT, ArrayList::class.java)
                val value: T = context.deserialize(json[i], elementType)
                if (value != null) {
                    list.add(value)
                }
            }
            return list
        }
        return arrayListOf()
    }
}

class StringTypeAdapter : TypeAdapter<String>() {
    override fun write(out: JsonWriter, value: String?) {
    }

    override fun read(`in`: JsonReader): String {
        if (`in`.peek() === JsonToken.NULL) {
            `in`.nextNull()
            return ""
        }
        return `in`.nextString()
    }
}

class BooleanTypeAdapter : TypeAdapter<Boolean>() {
    override fun write(out: JsonWriter, value: Boolean?) {
    }

    override fun read(`in`: JsonReader): Boolean {
        if (`in`.peek() === JsonToken.NULL) {
            `in`.nextNull()
            return false
        }
        return `in`.nextBoolean()
    }
}

class IntTypeAdapter : TypeAdapter<Int>() {
    override fun write(out: JsonWriter, value: Int?) {
    }

    override fun read(`in`: JsonReader): Int {
        if (`in`.peek() === JsonToken.NULL) {
            `in`.nextNull()
            return 0
        }
        return `in`.nextInt()
    }
}

class DoubleTypeAdapter : TypeAdapter<Double>() {
    override fun write(out: JsonWriter, value: Double?) {
    }

    override fun read(`in`: JsonReader): Double {
        if (`in`.peek() === JsonToken.NULL) {
            `in`.nextNull()
            return 0.0
        }
        return `in`.nextDouble()
    }
}