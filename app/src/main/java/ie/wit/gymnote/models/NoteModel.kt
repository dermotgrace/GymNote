package ie.wit.gymnote.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.parcelize.Parcelize

@IgnoreExtraProperties
@Parcelize
data class NoteModel(
    var uid: String? = "",
    var noteTitle: String? = "",
    var noteDate: String? = "",
    var noteDetail: String? = "",
    var noteType: String? = "",
    var noteComplete: Boolean? = false)
    : Parcelable
{
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "noteTitle" to noteTitle,
            "noteDate" to noteDate,
            "noteDetail" to noteDetail,
            "noteType" to noteType,
            "noteComplete" to noteComplete
        )
    }
}
