package com.fftools.roomdatabase.my_interface

import com.fftools.roomdatabase.model.Note

interface NoteItemOnClickListener {
    fun onClick(note: Note)
    fun onLongClick(note: Note)
}