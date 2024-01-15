package com.fftools.roomdatabase.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.fftools.roomdatabase.databinding.ItemNoteBinding
import com.fftools.roomdatabase.model.Note
import com.fftools.roomdatabase.my_interface.NoteItemOnClickListener
import com.fftools.roomdatabase.utils.Utils.convertSecondsToDateTime

class NoteAdapter(val listener: NoteItemOnClickListener): ListAdapter<Note, NoteAdapter.NoteViewHolder>(NOTE_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            ItemNoteBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class NoteViewHolder(val binding: ItemNoteBinding): ViewHolder(binding.root) {
        fun bind(item: Note) {
            binding.apply {
                tvTitle.text = item.title
                tvContent.text = item.content
                tvDate.text = item.editTime.convertSecondsToDateTime()

                root.setOnClickListener {
                    listener.onClick(item)
                }
                root.setOnLongClickListener {
                    listener.onLongClick(item)
                    true
                }
            }
        }
    }



    companion object {
        val NOTE_COMPARATOR = object : DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem == newItem
            }
        }
    }


}