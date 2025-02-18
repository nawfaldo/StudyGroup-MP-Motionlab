package sg.motion.tutorialnetworking.data.data_source.remote.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

// TODO : update this dto class
@Keep
data class NoteRequestDto(
    @SerializedName("content") val content : String? = null,
    @SerializedName("authorName") val authorName : String? = null,
    @SerializedName("createdAt") val createdAt : String? = null,
    @SerializedName("updatedAt") val updatedAt : String? = null,
)
