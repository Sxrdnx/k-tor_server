package com.example.data.collections

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

/**
 * Entidad  que reprecenta una nota (documento)
 * @property id el id es strin y al crear el objeto ObjetId se genera un id random
 */
data class Note(
    val title: String,
    val content: String,
    val date : Long,
    val owners: List<String>,
    val color :String,
    @BsonId
    val id: String = ObjectId().toString()
)
