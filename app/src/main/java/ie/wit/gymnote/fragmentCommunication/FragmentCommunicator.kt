package ie.wit.gymnote.fragmentCommunication

interface FragmentCommunicator {
    fun passData(noteTitle: String, noteDate: String, noteDetails: String)
}