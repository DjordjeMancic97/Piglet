package com.djordjemancic.piglet.data.dto

data class PigletUser(
    val userData: UserData = UserData(),
    val transactions: List<Transaction> = emptyList(),
    val tags: List<Tag> = emptyList(),
)