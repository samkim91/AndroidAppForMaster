package kr.co.soogong.master.domain.estimation

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kr.co.soogong.master.data.estimation.Estimation

@Dao
interface EstimationDao {
    @Query("SELECT * FROM Estimation")
    fun getAllList(): LiveData<List<Estimation>>

    @Query("SELECT * FROM Estimation WHERE keycode = :keycode")
    fun getItem(keycode: String): LiveData<Estimation?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(requirement: Estimation)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(requirements: List<Estimation>)

    @Query("DELETE FROM Estimation WHERE keycode = :keycode")
    suspend fun remove(keycode: String)

    @Query("DELETE FROM Estimation")
    suspend fun removeAll()
}