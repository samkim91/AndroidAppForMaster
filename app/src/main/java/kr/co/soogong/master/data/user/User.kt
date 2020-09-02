package kr.co.soogong.master.data.user

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "User")
data class User(
    @PrimaryKey
    @SerializedName("id")
    val id: String = UUID.randomUUID().toString()
) : Parcelable