package es.finders.scapetheads.services.firestore

import android.util.Log
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.time.Instant.now

class FirestoreClient {

    private val db = Firebase.firestore
    private val TAG = "FirestoreClient"
    private val collection = "highscores"

    fun addHighscore(data: HighScoreData) {
        db.collection(collection)
            .whereEqualTo("nickname", data.nickname)
            .get()
            .addOnSuccessListener { result ->
                if (result.isEmpty) {
                    uploadData(data)
                }
                var first = true
                for (document in result) {
                    if (first) {
                        editData(data, document)
                        first = false
                    } else {
                        deleteData(document)
                    }
                }
            }
            .addOnFailureListener { e ->
                uploadData(data)
            }
    }

    fun getTopHighscores(n: Long) {
        db.collection(collection)
            .orderBy("score", Query.Direction.DESCENDING)
            .limit(n)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error getting documents.", e)
            }
    }

    suspend fun checkNicknameExists(nickname: String): Boolean {
        return db.collection(collection)
            .whereEqualTo("nickname", nickname)
            .get()
            .await().documents.size > 0
    }

    private fun uploadData(data: HighScoreData) {
        val json = hashMapOf(
            "nickname" to data.nickname,
            "score" to data.score,
            "time" to data.time,
            "date" to data.date
        )

        db.collection(collection)
            .add(json)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

    private fun editData(data: HighScoreData, doc: QueryDocumentSnapshot) {
        val json = hashMapOf(
            "nickname" to data.nickname,
            "score" to data.score,
            "time" to data.time,
            "date" to data.date
        )

        db.collection(collection)
            .document(doc.id)
            .set(json)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot updated with ID: ${doc.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error updating document", e)
            }
    }

    private fun deleteData(doc: QueryDocumentSnapshot) {
        db.collection(collection)
            .document(doc.id)
            .delete()
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot deleted with ID: ${doc.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error deleting document", e)
            }
    }

    fun addNickname(nickname: String) {
        addHighscore(HighScoreData(nickname, 0, 0, now().epochSecond))
    }


}

data class HighScoreData(
    val nickname: String,
    val score: Long,
    val time: Long,
    val date: Long,
)