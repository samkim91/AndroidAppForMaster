package kr.co.soogong.master.domain

import kr.co.soogong.master.data.requirements.Requirement
import kr.co.soogong.master.data.user.User
import kr.co.soogong.master.domain.requirements.RequirementDao
import kr.co.soogong.master.domain.user.UserDao

class Repository private constructor(
//    private val materialDao: MaterialDao,
    private val requirementDao: RequirementDao,
    private val userDao: UserDao,
    private val sharedPreference: AppSharedPreference
) {
    //region Requirement
    fun getRequirementList() = requirementDao.getAllList()

    fun getRequirement(keycode: String) = requirementDao.getItem(keycode)

    suspend fun insertRequirement(requirement: Requirement) = requirementDao.insert(requirement)

    suspend fun insertRequirement(requirements: List<Requirement>) =
        requirementDao.insert(requirements)

    suspend fun removeRequirement(keycode: String) = requirementDao.remove(keycode)

    suspend fun removeAllRequirement() = requirementDao.removeAll()
    //endregion Requirement

    //region user
    fun getUserInfo(id: String) = userDao.getItem(id)

    suspend fun insertUserInfo(user: User) = userDao.insert(user)

    suspend fun removeUserInfo(id: String) = userDao.remove(id)

    suspend fun removeAllUserInfo() = userDao.removeAll()
    //endregion user

    //region AppSharedPreference
    fun setBoolean(key: String, value: Boolean) = sharedPreference.setBoolean(key, value)

    fun getBoolean(key: String, defaultValue: Boolean) =
        sharedPreference.getBoolean(key, defaultValue)

    fun setInteger(key: String, value: Int) = sharedPreference.setInteger(key, value)

    fun getInteger(key: String, defaultValue: Int) = sharedPreference.getInteger(key, defaultValue)

    fun setFloat(key: String, value: Float) = sharedPreference.setFloat(key, value)

    fun getFloat(key: String, defaultValue: Float) = sharedPreference.getFloat(key, defaultValue)

    fun setString(key: String, value: String) = sharedPreference.setString(key, value)

    fun getString(key: String, defaultValue: String) = sharedPreference.getString(key, defaultValue)
    //endregion AppSharedPreference

    companion object {
        @Volatile
        private var instance: Repository? = null

        fun getInstance(
            requirementDao: RequirementDao,
            userDao: UserDao,
            appSharedPreference: AppSharedPreference
        ) =
            instance ?: synchronized(this) {
                instance ?: Repository(
                    requirementDao,
                    userDao,
                    appSharedPreference
                ).also { instance = it }
            }
    }
}