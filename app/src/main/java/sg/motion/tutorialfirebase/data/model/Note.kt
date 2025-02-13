package sg.motion.tutorialfirebase.data.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

// TODO : modify this note model for document in firestore
data class Note(
    @DocumentId
    val id: String = "",
    val content: String = "",
    val title: String = "",
    val userId: String = "",
    @ServerTimestamp
    val createdAt: Date? = null,
    val updatedAt: Date? = null
)
