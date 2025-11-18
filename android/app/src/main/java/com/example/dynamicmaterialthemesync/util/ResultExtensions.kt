package com.example.dynamicmaterialthemesync.util

inline fun <T, R> Result<T>.mapCatching(transform: (T) -> R): Result<R> =
    fold(
        onSuccess = { runCatching { transform(it) } },
        onFailure = { Result.failure(it) }
    )