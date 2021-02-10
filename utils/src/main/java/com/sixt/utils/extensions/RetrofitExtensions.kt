package com.sixt.utils.extensions

import com.sixt.domain.data.responce.ResourceFailException
import com.sixt.domain.data.responce.ResourceHttpRestFail
import com.sixt.domain.data.responce.ResourceResponse
import com.sixt.domain.data.responce.ResourceSuccess
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Invocation
import retrofit2.Response
import kotlin.coroutines.resume

suspend fun <T> Call<T>.awaitApiResponse(): ResourceResponse<T> {
    return suspendCancellableCoroutine { continuation ->
        continuation.invokeOnCancellation {
            cancel()
        }
        enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body == null) {
                        val invocation = call.request().tag(Invocation::class.java)!!
                        val method = invocation.method()
                        val e = KotlinNullPointerException(
                            "Response from " +
                                    method.declaringClass.name +
                                    '.' +
                                    method.name +
                                    " was null but response body type was declared as non-null"
                        )
                        continuation.resume(ResourceFailException(e))
                    } else {
                        continuation.resume(ResourceSuccess(body, false))
                    }
                } else {
                    continuation.resume(ResourceHttpRestFail(response.code(), response.message()))
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                continuation.resume(ResourceFailException(t))
            }
        })
    }
}