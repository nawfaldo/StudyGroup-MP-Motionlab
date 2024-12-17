package sg.motion.tutorialfirebase.data.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class Note(
    @DocumentId
    val id: String = "", // Firestore document ID
    val title: String = "",
    val content: String = "",
    val userId: String = "", // To associate notes with specific users
    @ServerTimestamp
    val createdAt: Date? = null,
    val lastUpdated: Date? = null
)
