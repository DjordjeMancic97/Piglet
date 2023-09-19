package com.djordjemancic.piglet.data.dto

data class Transaction(
    val tag: Int? = null,
    val title: String = "",
    val date: String = "",
    val amount: Double = 0.0,
)
