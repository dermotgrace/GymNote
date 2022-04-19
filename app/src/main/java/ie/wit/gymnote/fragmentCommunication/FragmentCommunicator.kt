package ie.wit.gymnote.fragmentCommunication

import ie.wit.gymnote.models.NoteModel

interface FragmentCommunicator {
    fun passData(noteTitle: String, noteDate: String, noteDetails: String)
}