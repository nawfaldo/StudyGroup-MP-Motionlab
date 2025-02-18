package sg.motion.tutorialnetworking.data.data_source.remote.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

// TODO : update this dto class
@Keep
data class NoteDeleteResponseDto(
    @SerializedName("success") val success : Boolean? = null,

    @SerializedName("error") val error : ErrorResponseDto? = null
)


