package kr.co.soogong.master.domain.requirements

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kr.co.soogong.master.data.requirements.Requirement

@Dao
interface RequirementDao {
    @Query("SELECT * FROM REQUIREMENT")
    fun getAllList(): LiveData<List<Requirement>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(requirement: Requirement)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(requirements: List<Requirement>)
}