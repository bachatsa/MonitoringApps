package com.ydzmobile.employee.core.data

import com.ydzmobile.employee.core.domain.enum.State

sealed class ResourceState<T>(val state: State, val data: T? = null, val message: String? = null) {
    class SUCCESS<T>(data: T) : ResourceState<T>(State.SUCCES, data)
    class ERROR<T>(message: String, data: T? = null) : ResourceState<T>(State.ERROR, data, message)
    class LOADING<T>(data: T? = null) : ResourceState<T>(State.LOADING, data)
}