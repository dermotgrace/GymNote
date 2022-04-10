package ie.wit.gymnote.ui.notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddNoteViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Add Gym Note Here"
    }
    val text: LiveData<String> = _text
}