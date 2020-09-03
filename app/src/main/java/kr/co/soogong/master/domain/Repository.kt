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

    suspend fun insert(requirement: Requirement) = requirementDao.insert(requirement)
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