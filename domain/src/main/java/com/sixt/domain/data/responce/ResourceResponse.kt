package com.sixt.domain.data.responce

import java.lang.UnsupportedOperationException

typealias ResourceSuccess<T> = ResourceResponse.Success<T>
typealias ResourceHttpRestFail<T> = ResourceResponse.HttpRestFail<T>
typealias ResourceFailException<T> = ResourceResponse.FailException<T>

sealed class ResourceResponse<T> {

    class Success<T>(val data: T, val fromCache: Boolean) : ResourceResponse<T>()
    class HttpRestFail<T>(val status: Int, val message: String?) : ResourceResponse<T>()
    class FailException<T>(val t: Throwable) : ResourceResponse<T>()

    fun <K> map(block: (T) -> K): ResourceResponse<K> {
        return when (this) {
            is Success -> Success(block(data), fromCache)
            is HttpRestFail -> HttpRestFail(status, message)
            is FailException -> FailException(t)
        }
    }

    fun <K> mapFail(): ResourceResponse<K> {
        return when (this) {
            is Success -> throw UnsupportedOperationException("Response cannot be success")
            is HttpRestFail -> HttpRestFail(status, message)
            is FailException -> FailException(this.t)
        }
    }
}