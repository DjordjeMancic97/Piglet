package com.djordjemancic.piglet.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.djordjemancic.piglet.data.dto.Transaction
import com.djordjemancic.piglet.data.remote.models.TransactionModel


@Entity
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val ownerId: String = "",
    val tag: Int? = null,
    val title: String = "",
    val date: String = "",
    val amount: Double = 0.0,
)

fun TransactionEntity.fromTransaction(transaction: Transaction, userId: String) = TransactionEntity(
    ownerId = userId,
    tag = transaction.tag,
    title = transaction.title,
    date = transaction.date,
    amount = transaction.amount
)

fun TransactionEntity.toTransaction() = Transaction(
    tag = tag,
    title = title,
    date = date,
    amount = amount,
)

fun TransactionEntity.fromModel(model: TransactionModel) = TransactionEntity(
    ownerId = model.ownerId,
    tag = model.tag,
    title = model.title,
    date = model.date,
    amount = model.amount
)

fun TransactionEntity.toModel() = TransactionModel(
    ownerId = ownerId,
    tag = tag,
    title = title,
    date = date,
    amount = amount
)