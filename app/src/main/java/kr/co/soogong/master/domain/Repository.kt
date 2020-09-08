package kr.co.soogong.master.domain

import kr.co.soogong.master.data.requirements.Requirement
import kr.co.soogong.master.domain.requirements.RequirementDao

class Repository private constructor(
//    private val materialDao: MaterialDao,
    private val requirementDao: RequirementDao,
//    private val userDao: UserDao
) {
    //region Requirement
    fun getRequirementList() = requirementDao.getAllList()

    fun getRequirement(id: Long) = requirementDao.getItem(id)

    suspend fun insertRequirement(requirement: Requirement) = requirementDao.insert(requirement)

    suspend fun insertRequirement(requirements: List<Requirement>) = requirementDao.insert(requirements)

    suspend fun removeRequirement(requirementId: Long) = requirementDao.remove(requirementId)
    //endregion Requirement

    companion object {
        @Volatile
        private var instance: Repository? = null

        fun getInstance(requirementDao: RequirementDao) =
            instance ?: synchronized(this) {
                instance ?: Repository(requirementDao).also { instance = it }
            }
    }
}