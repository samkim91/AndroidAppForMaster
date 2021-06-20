package kr.co.soogong.master.domain.usecase.auth

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Reusable
import io.reactivex.Single
import kr.co.soogong.master.data.dao.profile.MasterDao
import kr.co.soogong.master.data.dto.profile.MasterDto
import kr.co.soogong.master.network.auth.AuthService
import kr.co.soogong.master.utility.extension.getNullable
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import javax.inject.Inject

@Reusable
class SignInUseCase @Inject constructor(
    private val authService: AuthService,
    private val saveMasterIdInSharedUseCase: SaveMasterIdInSharedUseCase,
    private val saveMasterUidInSharedUseCase: SaveMasterUidInSharedUseCase,
    private val saveMasterApprovalUseCase: SaveMasterApprovalUseCase,
    private val masterDao: MasterDao,
) {
    operator fun invoke(uid: String): Single<MasterDto> {
        return authService.signIn(uid).doOnSuccess {
//            MasterDto(
//                masterId = json.getNullable("masterId")?.asInt,
//                uid = json.getNullable("uid")?.asString,
//                ownerName = json.getNullable("ownerName")?.asString,
//                tel = json.getNullable("tel")?.asString,
//                safetyNumber = json.getNullable("safetyNumber")?.asString,
//                email = json.getNullable("email")?.asString,
//                roadAddress = json.getNullable("roadAddress")?.asString,
//                detailAddress = json.getNullable("detailAddress")?.asString,
//                latitude = json.getNullable("latitude")?.asFloat,
//                longitude = json.getNullable("longitude")?.asFloat,
//                serviceArea = json.getNullable("serviceArea")?.asInt,
//                subscriptionPlan = json.getNullable("subscriptionPlan")?.asString,
//                isPublic = json.getNullable("isPublic")?.asBoolean,
//                profileImageId = json.getNullable("profileImageId")?.asInt,
//                introduction = json.getNullable("introduction")?.asString,
//                warrantyPeriod = json.getNullable("warrantyPeriod")?.asInt,
//                warrantyDescription = json.getNullable("warrantyDescription")?.asString,
//                openDate = json.getNullable("openDate")?.asString,
//                businessType = json.getNullable("businessType")?.asString,
//                businessName = json.getNullable("businessName")?.asString,
//                shopName = json.getNullable("shopName")?.asString,
//                businessNumber = json.getNullable("businessNumber")?.asString,
//                privatePolicy = json.getNullable("privatePolicy")?.asBoolean,
//                marketingPush = json.getNullable("marketingPush")?.asBoolean,
//                marketingPushAtNight = json.getNullable("marketingPushAtNight")?.asBoolean,
//            )

            saveMasterIdInSharedUseCase(it.masterId!!)
            saveMasterUidInSharedUseCase(it.uid!!)
            if (it.subscriptionPlan != "NotApproved") saveMasterApprovalUseCase(true)

            // TODO: 2021/06/18 null 값으로 인한 에러가 발생하는 것 같음... 추후 확인 ...
            masterDao.insert(it)
        }.doOnError {
            Firebase.auth.signOut()
        }
    }
}