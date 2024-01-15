package com.fftools.roomdatabase.ui

import android.content.Intent
import com.fftools.roomdatabase.base.BaseActivity
import com.fftools.roomdatabase.databinding.ActivityEditNoteBinding
import com.fftools.roomdatabase.model.Note
import com.fftools.roomdatabase.utils.Constants.EXTRA_NOTE
import com.fftools.roomdatabase.utils.Utils.convertSecondsToDateTime
import com.fftools.roomdatabase.utils.extension.parcelable
import com.fftools.roomdatabase.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditNoteActivity : BaseActivity<ActivityEditNoteBinding>() {
    private var noteEdit: Note? = null
    private val viewModel: MainViewModel by viewModel()

    override fun createBinding() = ActivityEditNoteBinding.inflate(layoutInflater)

    override fun initMain() {
        getDataIntent()
        initToolbar()
        initView()
        initEvent()
    }

    private fun initEvent() {
        binding.ivBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.ivSave.setOnClickListener {
            noteEdit.getNoteUpdate()
            viewModel.insertNote(noteEdit!!) {
                val intent = Intent()
                finish()
            }
        }
    }

    private fun initView() {
        if (noteEdit != null) {
            binding.apply {
                tvTime.text = System.currentTimeMillis().convertSecondsToDateTime()
                etTitle.setText(noteEdit!!.title)
                etContent.setText(noteEdit!!.content)
            }
        } else {
            noteEdit = Note()
        }
    }

    private fun initToolbar() {

    }

    private fun getDataIntent() {
        noteEdit = intent.parcelable(EXTRA_NOTE, Note::class.java)
    }

    private fun Note?.getNoteUpdate() {
        this?.apply {
            title = binding.etTitle.text.toString()
            content = binding.etContent.text.toString()
            editTime = System.currentTimeMillis()
        }
    }
}