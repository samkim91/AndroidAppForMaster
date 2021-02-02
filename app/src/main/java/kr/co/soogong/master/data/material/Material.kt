package kr.co.soogong.master.data.material

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "Material")
data class Material(
    @PrimaryKey
    @SerializedName("id")
    val id: String = UUID.randomUUID().toString(),
) : Parcelable