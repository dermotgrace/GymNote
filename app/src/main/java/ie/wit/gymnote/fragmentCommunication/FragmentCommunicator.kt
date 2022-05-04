package ie.wit.gymnote.fragmentCommunication

import ie.wit.gymnote.models.NoteModel

interface FragmentCommunicator {
    fun passDataToNotes(note: NoteModel)
    fun passDataToEditNote(note: NoteModel)
}