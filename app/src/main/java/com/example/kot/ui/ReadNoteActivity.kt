package com.example.kot.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.example.kot.Models.Note
import com.example.kot.R
import com.example.kot.Utils.DateUtils
import com.example.kot.ViewModels.ReadNoteViewModel
import com.example.kot.databinding.ActivityReadNoteBinding

class ReadNoteActivity : AppCompatActivity() {
    lateinit var note: Note
    private lateinit var binding: ActivityReadNoteBinding
    private val readNoteViewModel by viewModel<ReadNoteViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_delete)
        note = intent.getParcelableExtra("NOTE")!!
        getIncomingParcel()
        binding.btnUpdate.setOnClickListener {
            updateNote()
            this.finish()
        }
        binding.btnCancel.setOnClickListener {
            this.finish()
        }
    }

    private fun getIncomingParcel() {
        val receivedNote = intent.getParcelableExtra<Note>("NOTE")
        receivedNote?.let {
            Log.d("update", it.toString())
            binding.textTitle.setText(it.noteTitle)
            binding.textText.setText(it.noteText)
            binding.timeText.text = it.createdAt

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        updateNote()
    }

    private fun updateNote() {
        val id = note.noteId
        val title = binding.textTitle.text.toString()
        val text = binding.textText.text.toString()
        val date = DateUtils.formatDate()
        val note = Note(noteId = id, noteTitle = title, noteText = text, createdAt = date)
        readNoteViewModel.updateNote(note)
    }

    private fun deleteNote() {
        readNoteViewModel.deleteNote(note)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //Huh...just a hack
        return when (item.itemId) {
            android.R.id.home -> {
                deleteNote()
                this.finish()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

}


