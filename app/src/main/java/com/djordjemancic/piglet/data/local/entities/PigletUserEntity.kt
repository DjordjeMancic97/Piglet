package com.djordjemancic.piglet.data.local.entities

import androidx.room.Embedded
import androidx.room.Relation
import com.djordjemancic.piglet.data.dto.PigletUser
import com.djordjemancic.piglet.data.dto.UserData
import com.djordjemancic.piglet.data.remote.models.PigletUserModel
import com.djordjemancic.piglet.data.remote.models.UserDataModel
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class PigletUserEntity(
    @Embedded val userData: UserDataEntity = UserDataEntity(),
    @Relation(
        entity = TransactionEntity::class,
        parentColumn = "userId",
        entityColumn = "ownerId"
    )
    val transactions: List<TransactionEntity> = emptyList(),
    @Relation(
        entity = TagEntity::class,
        parentColumn = "userId",
        entityColumn = "ownerId"
    )
    val tags: List<TagEntity> = emptyList()
)

fun PigletUserEntity.fromUser(user: PigletUser, userId: String) = PigletUserEntity(
    userData = UserDataEntity(
        userId = userId,
        firstName = userId,
        lastName = user.userData.lastName,
        email = user.userData.email
    ),
    transactions = user.transactions.map {
        TransactionEntity().fromTransaction(it, userId)
    },
    tags = user.tags.map { TagEntity().fromTag(it, userId) }
)

fun PigletUserEntity.toUser() = PigletUser(
    userData = UserData(
        firstName = userData.firstName,
        lastName = userData.lastName,
        email = userData.email
    ),
    transactions = transactions.map { it.toTransaction() },
    tags = tags.map { it.toTag() }
)

fun PigletUserEntity.fromModel(user: PigletUserModel) = PigletUserEntity(
    userData = UserDataEntity(
        userId = user.userData.userId,
        firstName = user.userData.firstName,
        lastName = user.userData.lastName,
        email = user.userData.email
    ),
    transactions = user.transactions.map { TransactionEntity().fromModel(it) },
    tags = user.tags.map { TagEntity().fromModel(it) }
)

fun PigletUserEntity.toModel() = PigletUserModel(
    userData = UserDataModel(
        userId = userData.userId,
        firstName = userData.firstName,
        lastName = userData.lastName,
        email = userData.email
    ),
    transactions = transactions.map { it.toModel() },
    tags = tags.map { it.toModel() }
)