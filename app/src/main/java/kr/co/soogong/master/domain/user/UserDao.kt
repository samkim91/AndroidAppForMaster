package kr.co.soogong.master.domain.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kr.co.soogong.master.data.user.User

@Dao
interface UserDao {
    @Query("SELECT * FROM User WHERE keycode = :id")
    fun getItem(id: String): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Query("DELETE FROM User WHERE keycode = :id")
    suspend fun remove(id: String)

    @Query("DELETE FROM User")
    suspend fun removeAll()
}