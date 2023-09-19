package com.djordjemancic.piglet.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.djordjemancic.piglet.data.dto.Tag
import com.djordjemancic.piglet.data.remote.models.TagModel

@Entity()
data class TagEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val ownerId: String = "",
    val tagName: String = "",
    val tagColor: String = "",
)

fun TagEntity.fromModel(model: TagModel) = TagEntity(
    ownerId = model.ownerId,
    tagName = model.tagName,
    tagColor = model.tagColor
)

fun TagEntity.toModel() = TagModel(
    ownerId = ownerId,
    tagName = tagName,
    tagColor = tagColor
)

fun TagEntity.fromTag(tag: Tag, userId: String) = TagEntity(
    ownerId = userId,
    tagName = tag.tagName,
    tagColor = tag.tagColor
)

fun TagEntity.toTag() = Tag(
    tagName = tagName,
    tagColor = tagColor
)