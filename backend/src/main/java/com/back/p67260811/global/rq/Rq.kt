package com.back.p67260811.global.rq

import com.back.p67260811.domain.member.entity.Member
import com.back.p67260811.domain.member.service.MemberService
import com.back.p67260811.global.security.SecurityUser
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import lombok.RequiredArgsConstructor
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import java.util.*
import java.util.function.Function
import java.util.function.Predicate

@Component
class Rq(
    private val memberService: MemberService,
    private val request: HttpServletRequest,
    private val response: HttpServletResponse
) {

    val actor: Member
        get() {
            // 인증된 사용자 정보 꺼내서 제공
//            val authentication =
//                SecurityContextHolder.getContext().getAuthentication()
//            val user = authentication!!.getPrincipal() as SecurityUser?
            return SecurityContextHolder.getContext()
                .authentication
                ?.principal
                ?.let {
                    if (it is SecurityUser) {
                        Member(it.id, it.username, it.nickname)
                    } else {
                        null
                    }
                } ?: throw RuntimeException("user not found")

//            return Member(user!!.getId(), user.getUsername(), user.getNickname())
        }

    fun setHeader(name: String, value: String) {
        response.setHeader(name, value)
    }


    //    fun getCookieValue(name: String?, defaultValue: String?): String? {
//        return Optional
//            .ofNullable<Array<Cookie?>?>(request!!.getCookies())
//            .flatMap<String?>(
//                Function { cookies: Array<Cookie?>? ->
//                    Arrays.stream<Cookie?>(cookies)
//                        .filter { cookie: Cookie? -> cookie!!.getName() == name }
//                        .map<String?> { obj: Cookie? -> obj!!.getValue() }
//                        .filter { value: String? -> !value!!.isBlank() }
//                        .findFirst()
//                }
//            )
//            .orElse(defaultValue)
//    }
    fun getCookieValue(name: String, defaultValue: String): String = request
        .cookies
        ?.firstOrNull { it.name == name }
        ?.value
        ?.takeIf { it.isNotBlank() }
        ?: defaultValue

    fun addCookie(name: String, value: String) {
//        val cookie = Cookie(name, value)
//        cookie.setDomain("localhost")
//        cookie.setPath("/")
//        cookie.setHttpOnly(true)
//
//        response!!.addCookie(cookie)

        Cookie(name, value).apply {
            domain = "localhost"
            path = "/"
            isHttpOnly = true
        }.also {
            response.addCookie(it)
        }
    }

    fun deleteCookie(name: String) =
        Cookie(name, "").apply {
            domain = "localhost"
            path = "/"
            isHttpOnly = true
            maxAge = 0
        }.also {
            response.addCookie(it)
        }

    fun getHeader(name: String, defaultValue: String): String = request.getHeader(name) ?: defaultValue
}