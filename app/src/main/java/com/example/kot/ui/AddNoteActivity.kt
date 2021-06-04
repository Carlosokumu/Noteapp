package com.example.kot.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.*
import com.example.kot.Models.Note
import com.example.kot.Utils.DateUtils
import com.example.kot.ViewModels.AddNoteViewModel
import com.example.kot.databinding.ActivityAddNoteBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddNoteBinding
    private val addNoteViewModel by viewModel<AddNoteViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.saveImage.setOnClickListener {
           saveNote()
        }
        binding.cancelImage.setOnClickListener {
            saveNote()
        }
    }
    private fun saveNote(){
            if (TextUtils.isEmpty(binding.noteTitle.text) && TextUtils.isEmpty(binding.noteText.text)){
                Toast.makeText(applicationContext,"nothing saved",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                return
            }
        val noteTitle = binding.noteTitle.text.toString()
        val noteText = binding.noteText.text.toString()
        val date = DateUtils.formatDate()
        val note = Note(noteTitle = noteTitle, noteText = noteText, createdAt = date)
        addNoteViewModel.saveNote(note)
        startActivity(Intent(this, MainActivity::class.java))
    }


}