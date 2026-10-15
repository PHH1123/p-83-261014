package com.back.p67260811.global.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User

class SecurityUser(
    // val이 붙으면 멤버 변수, 없으면 일반 매개 변수
    username: String,
    password: String?,
    authorities: Collection<GrantedAuthority>,
    val id: Int?,
    val nickname: String?
): User(username, password, authorities) {

}
