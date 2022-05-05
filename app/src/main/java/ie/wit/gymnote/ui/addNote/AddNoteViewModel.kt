package ie.wit.gymnote.ui.addNote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import ie.wit.gymnote.firebaseDB.FirebaseDBManager
import ie.wit.gymnote.models.NoteModel
import timber.log.Timber
import timber.log.Timber.i

class AddNoteViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Add Gym Note Here"
    }
    val text: LiveData<String> = _text
    private val status = MutableLiveData<Boolean>()
    val observableStatus: LiveData<Boolean>
        get() = status

    fun addNote(firebaseUser: MutableLiveData<FirebaseUser>,
                note: NoteModel
    ) {
        status.value = try {
            //DonationManager.create(donation)
            i("gn AddNoteViewModel::addNote")
            FirebaseDBManager.create(firebaseUser,note)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }

    fun updateNote(firebaseUser: MutableLiveData<FirebaseUser>,
                note: NoteModel
    ) {
        status.value = try {
            //DonationManager.create(donation)
            i("gn AddNoteViewModel::updateNote")
            FirebaseDBManager.update(firebaseUser,note)
            true
        } catch (e: IllegalArgumentException) {
            i("gn ${e.toString()}")
            false
        }
    }

    fun deleteNote(firebaseUser: MutableLiveData<FirebaseUser>,
                   note: NoteModel
    ) {
        status.value = try {
            //DonationManager.create(donation)
            i("gn AddNoteViewModel::updateNote")
            FirebaseDBManager.delete(firebaseUser,note)
            true
        } catch (e: IllegalArgumentException) {
            i("gn ${e.toString()}")
            false
        }
    }
}