package ie.wit.gymnote.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LogoutViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Logout"
    }
    val text: LiveData<String> = _text
}