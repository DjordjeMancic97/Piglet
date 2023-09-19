package com.djordjemancic.piglet.data.remote.models

data class TransactionModel(
    val ownerId: String = "",
    val tag: Int? = null,
    val title: String = "",
    val date: String = "",
    val amount: Double = 0.0,
)
