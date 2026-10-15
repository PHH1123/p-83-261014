package com.back.p67260811.global.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import lombok.AllArgsConstructor
import lombok.Getter

class RsData<T>(
    private val resultCode: String,
    private val msg: String,
    private val data: T? = null
) {
    //    private Object data; 이렇게 해도 되지만 나중에 꺼내 쓸 때 불편
     // 제너릭을 사용하면 RsData 를 사용하는 시점에 T를 결정할 수 있음
    constructor(resultCode: String, msg: String) :
            this(resultCode, msg, data = null)

    @get:JsonIgnore
    val statusCode: Int
        get() = resultCode.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0].toInt() // "201-1"

    fun getResultCode(): String = resultCode
    fun getMsg(): String = msg
    fun getData(): T? = data
}
