package com.example.kot.ui

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kot.Adapter.MyAdapter
import com.example.kot.Models.Note
import com.example.kot.Interfaces.MyInterface
import com.example.kot.Objects.ObjectBox
import com.example.kot.R
import com.example.kot.ViewModels.NotesViewModel
import com.example.kot.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class MainActivity : AppCompatActivity(), MyInterface {
    private val notesViewModel by viewModel<NotesViewModel>()
    private val noteAdapter: MyAdapter by lazy { MyAdapter()}
    private lateinit var binding: ActivityMainBinding
    private lateinit var emptyView: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(false)
        val view= layoutInflater.inflate(R.layout.layout_top, null)
        supportActionBar?.customView =view
        supportActionBar?.setDisplayShowCustomEnabled(true)
        view.findViewById<ImageButton>(R.id.lock).setOnClickListener {
            startActivity(Intent(this@MainActivity, Auth::class.java))
        }
        emptyView = findViewById<FrameLayout>(R.id.emptyView)
        binding.add.setOnClickListener {
            val intent = Intent(this@MainActivity, AddNoteActivity::class.java)
            startActivity(intent)
        }
        notesViewModel.allNotes.observe(this) { note ->
            noteAdapter.setViews(binding.recView, emptyView)
            noteAdapter.setInterface(this)
            noteAdapter.setData(note)
        }
        binding.recView.apply {
            this.layoutManager = LinearLayoutManager(this@MainActivity)
            this.adapter = noteAdapter
            this.addItemDecoration(DividerItemDecoration(this.context,
                    DividerItemDecoration.VERTICAL))
        }

    }


    override fun onNote(note: Note) {
        startActivity(Intent(this@MainActivity, ReadNoteActivity::class.java).apply {
            putExtra("NOTE", note)
        })
    }



    private fun setActivity(){
       startActivity(Intent(this@MainActivity, LogActivity::class.java))
   }
    private suspend fun check(): Boolean{
       return ObjectBox.dataStore.data.first()[Auth.Keys.SET] ?: false
    }

    override  fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            if (check()){
                setActivity()
            }
        }
    }
    private suspend fun checkForAppLevel(): Boolean{
        return ObjectBox.dataStore.data.first()[Auth.Keys.SET_FOR_APP] ?: false
    }

    override fun onBackPressed() {
        super.onBackPressed()
       lifecycleScope.launch {
           if (checkForAppLevel()){
               ObjectBox.dataStore.edit { settings->
                   settings[Auth.Keys.SET]=true
               }
           }
       }
        finishAffinity()
    }

}