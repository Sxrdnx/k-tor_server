package com.example.data

import com.example.data.collections.Note
import com.example.data.collections.User
import com.example.security.checHashForPassword
import org.litote.kmongo.contains
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.eq
import org.litote.kmongo.reactivestreams.KMongo
import org.litote.kmongo.setValue

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

suspend fun checkIfUSerExists(email: String): Boolean{
    //SELECT * FROM user WHERE  email = $email  <- query equivalente
    return  users.findOne(User::email eq email) != null
}


suspend fun checkPassworForEmail(email: String,passwordToCheck: String): Boolean{
    val actualPassword = users.findOne(User::email eq email)?.password ?: return  false
    return checHashForPassword(passwordToCheck,actualPassword)
}

suspend fun getNotesForUser(email: String): List<Note>{
    return notes.find(Note::owners contains email).toList()
}
suspend fun saveNote(note: Note): Boolean{
    val noteExists = notes.findOneById(note.id) != null
    return if (noteExists){
        notes.updateOneById(note.id,note).wasAcknowledged()
    }else{
        notes.insertOne(note).wasAcknowledged()
    }
}

suspend fun deleteNoteForUser(email: String,noteID:String): Boolean{
    val note = notes.findOne(Note::id eq noteID,Note::owners contains email)
    note?.let {note ->
        if (note.owners.size > 1 ){
            val newOwners = note.owners - email
            val updateResult = notes.updateOne(Note::id eq note.id, setValue(Note::owners,newOwners))
            return updateResult.wasAcknowledged()
        }
        return notes.deleteOneById(note.id).wasAcknowledged()
    } ?: return false
}

suspend fun isOwnerOfNote(noteID: String, owner: String): Boolean {
    val note = notes.findOneById(noteID) ?: return false
    return owner in note.owners
}

suspend fun addOwnerToNote(noteID: String, owner: String): Boolean {
    val owners = notes.findOneById(noteID)?.owners ?: return false
    return notes.updateOneById(noteID, setValue(Note::owners, owners + owner)).wasAcknowledged()
}
//dado que todas las acciones son mediante corutinas debemos envolver la funcion en otra suspernd fun
/*fun registerUser(user: User):Boolean {
    return database.getCollection<User>().insertOne(user).wasAcknowledged()
}*/