package com.example.data

import com.example.data.collections.Note
import com.example.data.collections.User
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

/**
 * el cliente especifica como queremos acceder a nuesta db, en este caso definimos que sera por corutina
 * y que todas las operaciones que se hagan se aran con corutinas
 */
private val client  = KMongo.createClient().coroutine
private val database = client.getDatabase("NotesDatabase")
private val users = database.getCollection<User>()
private val notes = database.getCollection<Note>()

suspend fun registerUser(user: User):Boolean {
    return users.insertOne(user).wasAcknowledged()
}

//dado que todas las acciones son mediante corutinas debemos envolver la funcion en otra suspernd fun
/*fun registerUser(user: User):Boolean {
    return database.getCollection<User>().insertOne(user).wasAcknowledged()
}*/