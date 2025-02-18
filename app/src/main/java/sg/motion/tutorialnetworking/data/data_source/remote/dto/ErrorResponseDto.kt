package sg.motion.tutorialnetworking.data.data_source.remote.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

// TODO : update this dto class
@Keep
data class ErrorResponseDto(
    @SerializedName("code") val code : String? = null,
    @SerializedName("message") val message : String? = null,
)