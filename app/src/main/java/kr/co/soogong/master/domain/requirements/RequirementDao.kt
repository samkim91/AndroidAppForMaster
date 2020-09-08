package kr.co.soogong.master.domain.requirements

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kr.co.soogong.master.data.requirements.Requirement
import retrofit2.http.DELETE

@Dao
interface RequirementDao {
    @Query("SELECT * FROM Requirement")
    fun getAllList(): LiveData<List<Requirement>>

    @Query("SELECT * FROM Requirement WHERE id = :id")
    fun getItem(id: Long): LiveData<Requirement?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(requirement: Requirement)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(requirements: List<Requirement>)

    @Query("DELETE FROM Requirement WHERE id = :id")
    suspend fun remove(id: Long)
}