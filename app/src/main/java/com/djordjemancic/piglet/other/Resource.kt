package com.djordjemancic.piglet.other

import com.djordjemancic.piglet.data.dto.PigletUser

data class Resource(
    val status: Status = Status.INITIAL,
    val data: PigletUser? = null,
    val errorMessage: String? = null,
) {
    enum class Status {
        SUCCESS, ERROR, LOADING, INITIAL
    }
}
