package kr.co.soogong.master.domain

import kr.co.soogong.master.data.requirements.Requirement
import kr.co.soogong.master.data.user.User
import kr.co.soogong.master.domain.requirements.RequirementDao
import kr.co.soogong.master.domain.user.UserDao

class Repository private constructor(
//    private val materialDao: MaterialDao,
    private val requirementDao: RequirementDao,
    private val userDao: UserDao
) {
    //region Requirement
    fun getRequirementList() = requirementDao.getAllList()

    fun getRequirement(keycode: String) = requirementDao.getItem(keycode)

    suspend fun insertRequirement(requirement: Requirement) = requirementDao.insert(requirement)

    suspend fun insertRequirement(requirements: List<Requirement>) =
        requirementDao.insert(requirements)

    suspend fun removeRequirement(keycode: String) = requirementDao.remove(keycode)
    //endregion Requirement

    //region user
    fun getUserInfo(id: String) = userDao.getItem(id)

    suspend fun insertUserInfo(user: User) = userDao.insert(user)

    suspend fun removeUserInfo(id: String) = userDao.remove(id)
    //endregion user

    companion object {
        @Volatile
        private var instance: Repository? = null

        fun getInstance(requirementDao: RequirementDao, userDao: UserDao) =
            instance ?: synchronized(this) {
                instance ?: Repository(requirementDao, userDao).also { instance = it }
            }
    }
}