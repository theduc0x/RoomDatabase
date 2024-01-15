package com.fftools.roomdatabase.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fftools.roomdatabase.database.NoteDao
import com.fftools.roomdatabase.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val noteDao: NoteDao
): ViewModel() {
    private val noteList = MutableLiveData<NoteDataState>()
    val noteListState: LiveData<NoteDataState> get() = noteList


    fun insertNote(note: Note, callback: ((Boolean) -> Unit)? = null) = viewModelScope.launch {
        noteDao.insertNote(note)
        callback?.invoke(true)
    }

    fun updateNote(note: Note, callback: ((Boolean) -> Unit)? = null) = viewModelScope.launch {
        noteDao.updateNote(note)
        callback?.invoke(true)
    }

    fun deleteNote(note: Note, callback: ((Boolean) -> Unit)? = null) = viewModelScope.launch {
        noteDao.deleteNote(note)
        callback?.invoke(true)
    }

    fun getNoteList() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                emitNoteDataState(isLoading = true)
                noteDao.getAllNote()
            }.onSuccess { result ->
                emitNoteDataState(result = result)
            }.onFailure { error ->
                emitNoteDataState(error = error.message.toString())
            }
        }
    }

    data class NoteDataState(
        val isLoading: Boolean = false,
        val result: List<Note>? = null,
        val error: String? = null
    )

    private fun emitNoteDataState(
        isLoading: Boolean = false,
        result: List<Note>? = null,
        error: String? = null
    ) {
        val dataState = NoteDataState(isLoading, result, error)
        noteList.postValue(dataState)
    }
}