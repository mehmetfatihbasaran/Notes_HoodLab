package com.example.notes_hoodlab.repository

import com.example.notes_hoodlab.model.Notes
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

const val NOTES_COLLECTION_REF = "notes"

class StorageRepository {

    fun user() = Firebase.auth.currentUser
    fun hasUser(): Boolean = Firebase.auth.currentUser != null

    fun getUserId(): String = Firebase.auth.currentUser?.uid.orEmpty()

    private val notesReference: CollectionReference
        get() = Firebase.firestore.collection(NOTES_COLLECTION_REF)

    fun getUserNotes(
        userId: String,
    ): Flow<Resources<List<Notes>>> = callbackFlow {
        var snapshotStateListener: ListenerRegistration? = null

        try {
            snapshotStateListener = notesReference
                .orderBy("timestamp")
                .whereEqualTo("userId", userId)
                .addSnapshotListener { snapshot, e ->
                    val response = if (snapshot != null) {
                        val notes = snapshot.toObjects(Notes::class.java)
                        Resources.Success(data = notes)
                    } else {
                        Resources.Error(throwable = e?.cause)
                    }
                    trySend(response)

                }


        } catch (e: Exception) {
            trySend(Resources.Error(e.cause))
            e.printStackTrace()
        }

        awaitClose {
            snapshotStateListener?.remove()
        }


    }

    fun getNote(
        noteId:String,
        onError:(Throwable?) -> Unit,
        onSuccess: (Notes?) -> Unit
    ){
        notesReference
            .document(noteId)
            .get()
            .addOnSuccessListener {
                onSuccess.invoke(it?.toObject(Notes::class.java))
            }
            .addOnFailureListener {result ->
                onError.invoke(result.cause)
            }


    }

    fun addNote(
        userId: String,
        title: String,
        description: String,
        timestamp: Timestamp,
        color: Int = 0,
        onComplete: (Boolean) -> Unit,
    ){
        val documentId = notesReference.document().id
        val note = Notes(
            userId,
            title,
            description,
            timestamp,
            colorIndex = color,
            documentId = documentId
        )
        notesReference
            .document(documentId)
            .set(note)
            .addOnCompleteListener { result ->
                onComplete.invoke(result.isSuccessful)
            }


    }

    fun deleteNote(noteId: String,onComplete: (Boolean) -> Unit){
        notesReference.document(noteId)
            .delete()
            .addOnCompleteListener {
                onComplete.invoke(it.isSuccessful)
            }
    }

    fun updateNote(
        title: String,
        note:String,
        color: Int,
        noteId: String,
        onResult:(Boolean) -> Unit
    ){
        val updateData = hashMapOf<String,Any>(
            "colorIndex" to color,
            "description" to note,
            "title" to title,
        )

        notesReference.document(noteId)
            .update(updateData)
            .addOnCompleteListener {
                onResult(it.isSuccessful)
            }



    }

    fun signOut() = Firebase.auth.signOut()


}

