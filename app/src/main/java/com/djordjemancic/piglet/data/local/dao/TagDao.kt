package com.djordjemancic.piglet.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.djordjemancic.piglet.data.local.entities.TagEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TagDao {

    @Upsert
    suspend fun upsertTag(tagEntity: List<TagEntity>)

    @Delete
    suspend fun deleteTag(tagEntity: TagEntity)

    @Transaction
    @Query("SELECT * FROM TagEntity WHERE ownerId = (:userId)")
    fun getTags(userId: String): Flow<List<TagEntity>>
}