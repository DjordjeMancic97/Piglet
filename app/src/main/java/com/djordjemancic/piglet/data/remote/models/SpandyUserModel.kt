package com.djordjemancic.piglet.data.remote.models

import com.djordjemancic.piglet.data.dto.PigletUser
import com.djordjemancic.piglet.data.dto.Tag
import com.djordjemancic.piglet.data.dto.Transaction
import com.djordjemancic.piglet.data.dto.UserData
import com.djordjemancic.piglet.data.local.entities.PigletUserEntity
import com.djordjemancic.piglet.data.local.entities.TagEntity
import com.djordjemancic.piglet.data.local.entities.TransactionEntity
import com.djordjemancic.piglet.data.local.entities.UserDataEntity

data class PigletUserModel(
    val userData: UserDataModel = UserDataModel("", "", ""),
    val transactions: List<TransactionModel> = emptyList(),
    val tags: List<TagModel> = emptyList()
)

fun PigletUserModel.toUser() = PigletUser(
    userData = UserData(firstName = userData.firstName, lastName = userData.lastName, email = userData.email),
    transactions = transactions.map { Transaction(tag= it.tag, title = it.title, date = it.date, amount = it.amount) },
    tags = tags.map { Tag(tagName = it.tagName, tagColor = it.tagColor) }
)

fun PigletUserModel.fromUser(user: PigletUser, userId: String) = PigletUserModel(
    userData = UserDataModel(userId, user.userData.firstName, user.userData.lastName, user.userData.email),
    transactions = user.transactions.map { TransactionModel(tag = it.tag, title = it.title, date = it.date, amount = it.amount) },
    tags = user.tags.map { TagModel(tagColor = it.tagColor, tagName = it.tagName, ownerId = userId) }
)

fun PigletUserModel.toEntity() = PigletUserEntity(
    userData = UserDataEntity(userId = userData.userId, firstName = userData.firstName, lastName = userData.lastName, email = userData.email),
    transactions = transactions.map { TransactionEntity(tag= it.tag, title = it.title, date = it.date, amount = it.amount) },
    tags = tags.map { TagEntity(tagName = it.tagName, tagColor = it.tagColor) }
)

fun PigletUserModel.fromEntity(user: PigletUserEntity) = PigletUserModel(
    userData = UserDataModel(user.userData.userId, user.userData.firstName, user.userData.lastName, user.userData.email),
    transactions = user.transactions.map { TransactionModel(tag = it.tag, title = it.title, date = it.date, amount = it.amount) },
    tags = user.tags.map { TagModel(tagColor = it.tagColor, tagName = it.tagName) }
)