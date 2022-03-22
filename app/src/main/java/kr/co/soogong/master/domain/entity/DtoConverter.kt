package kr.co.soogong.master.domain.entity

interface DtoConverter<in T, out F> {
    fun fromDto(dto: T): F
}