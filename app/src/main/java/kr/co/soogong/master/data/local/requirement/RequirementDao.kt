package kr.co.soogong.master.data.local.requirement

import androidx.room.*
import io.reactivex.Single
import kr.co.soogong.master.data.entity.requirement.RequirementDto
import kr.co.soogong.master.data.entity.requirement.estimation.EstimationDto

@Dao
interface RequirementDao {
    @Query("SELECT * FROM Requirement WHERE status IN (:status) ORDER BY id DESC")
    fun getListByStatus(status: List<String>): Single<List<RequirementDto>>

    @Query("SELECT * FROM Requirement WHERE id = :id")
    fun getItem(id: Int): Single<RequirementDto>

//    @Query("SELECT * FROM Requirement WHERE address LIKE :searchingText AND createdAt > :searchingPeriod ORDER BY id DESC")
//    fun searchList(searchingText: String, searchingPeriod: Date): Single<List<RequirementDto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(requirement: RequirementDto)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg requirements: RequirementDto)

    @Query("UPDATE Requirement SET estimationDto = :estimationDto WHERE id = :requirementId")
    fun updateEstimation(requirementId: Int?, estimationDto: EstimationDto)

    @Delete
    fun remove(requirement: RequirementDto)

    @Query("DELETE FROM Requirement WHERE status In (:status)")
    fun removeByStatus(status: List<String>)

    @Query("DELETE FROM Requirement")
    fun removeAll()
}