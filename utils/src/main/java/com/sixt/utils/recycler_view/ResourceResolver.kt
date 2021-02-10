package com.sixt.utils.recycler_view

import com.sixt.domain.utils.CacheResolveStrategy
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ResourceResolver<Result>(
    private val fetch: suspend () -> Result?,
    private val persist: suspend (Result) -> Unit,
    private val getPersisted: suspend () -> Result?,
    private val strategy: CacheResolveStrategy = CacheResolveStrategy.CACHE_FIRST
) {

    fun get(): Flow<Result> = when (strategy) {
        CacheResolveStrategy.CACHE_ONLY -> cacheOnlyResolverFlow()
        CacheResolveStrategy.CACHE_FIRST -> cacheFirstResolverFlow()
        CacheResolveStrategy.NETWORK -> networkResolverFlow()
        CacheResolveStrategy.NETWORK_ONLY -> networkOnlyResolverFlow()
    }

    private fun cacheOnlyResolverFlow() = flow<Result> {
        getPersisted()?.let { emit(it) }
    }

    private fun cacheFirstResolverFlow() = flow<Result> {
        coroutineScope {
            val fetchAsync = async { fetch() }
            val getPersistedAsync = async { getPersisted() }

            val cached = getPersistedAsync.await()

            // if fetching completes before getting cached version emit it and finish
            fetchAsync.takeIf { it.isCompleted }?.await()?.let {
                persist(it)
                emit(it)
                return@coroutineScope
            }

            cached?.let { emit(it) }
            fetchAsync.await()?.let {
                persist(it)
                emit(it)
            }
        }
    }

    private fun networkResolverFlow() = flow<Result> {
        fetch()?.let {
            persist(it)
            emit(it)
        }
    }

    private fun networkOnlyResolverFlow() = flow<Result> {
        fetch()?.let { emit(it) }
    }
}