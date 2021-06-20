package kr.co.soogong.master.data.dao.requirement

import androidx.room.*
import io.reactivex.Maybe
import kr.co.soogong.master.data.dto.requirement.RequirementDto

@Dao
interface RequirementDao {
    @Query("SELECT * FROM Requirement WHERE status In (:status)")
    fun getListByStatus(status: List<String>): Maybe<List<RequirementDto>>

    @Query("SELECT * FROM Requirement WHERE requirementId = :id")
    fun getItem(id: Int): Maybe<RequirementDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(requirement: RequirementDto)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg requirements: RequirementDto)

    @Delete
    fun remove(requirement: RequirementDto)

    @Query("DELETE FROM Requirement")
    fun removeAll()
}