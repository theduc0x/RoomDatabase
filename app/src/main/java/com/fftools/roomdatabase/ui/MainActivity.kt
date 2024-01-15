package com.fftools.roomdatabase.ui

import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.fftools.roomdatabase.adapter.NoteAdapter
import com.fftools.roomdatabase.base.BaseActivity
import com.fftools.roomdatabase.databinding.ActivityMainBinding
import com.fftools.roomdatabase.model.Note
import com.fftools.roomdatabase.my_interface.NoteItemOnClickListener
import com.fftools.roomdatabase.utils.Constants
import com.fftools.roomdatabase.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding>(), NoteItemOnClickListener {
    private val viewModel: MainViewModel by viewModel()
    private var adapter: NoteAdapter? = null

    private val noteLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult? ->
            viewModel.getNoteList()
        }


    override fun createBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun initMain() {
        initView()
        initEvent()
        initObservable()
        setData()
    }

    private fun setData() {
        viewModel.getNoteList()
    }

    private fun initObservable() {
        viewModel.noteListState.observe(this) {
            val status = it ?: return@observe
            status.result?.let { list ->
                adapter?.submitList(list)
            }
        }
    }

    private fun initEvent() {

    }

    private fun initView() {
        adapter = NoteAdapter(this)
        binding.rvNote.adapter = adapter
    }

    override fun onClick(note: Note) {
        val intent = Intent(this, EditNoteActivity::class.java)
        intent.putExtra(Constants.EXTRA_NOTE, note)
        noteLauncher.launch(intent)
    }

    override fun onLongClick(note: Note) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Bạn có chắc chắn muốn xóa không ?")
        alertDialog.setPositiveButton("OK") { _, _ ->
            viewModel.deleteNote(note) {
                viewModel.getNoteList()
            }
        }

        alertDialog.setNegativeButton("Không") { _, _ ->

        }
        val dialog = alertDialog.create()
        dialog.show()
    }
}