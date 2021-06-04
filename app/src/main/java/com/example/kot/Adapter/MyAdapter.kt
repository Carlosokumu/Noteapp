package com.example.kot.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kot.Models.Note
import com.example.kot.Interfaces.MyInterface
import com.example.kot.R

class MyAdapter: RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    private var searchableList = emptyList<Note>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var myInterface: MyInterface
    lateinit var view: View
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
       val view=LayoutInflater.from(parent.context).inflate(R.layout.notes_layout,parent,false)
        return MyViewHolder(view)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.textNote.text= searchableList[position].noteText
        holder.noteTitle.text= searchableList[position].noteTitle
        holder.itemView.setOnClickListener {
          myInterface.onNote(searchableList[position])
        }
    }
  fun setViews(rec:RecyclerView,view:View)= run {
      this.recyclerView=rec
      this.view=view
  }
    fun setInterface(note: MyInterface){
       this.myInterface=note
    }
    internal fun setData(notes: List<Note>){
        this.searchableList = ArrayList(notes)
        if (searchableList.isEmpty()){
            recyclerView.visibility=GONE
            view.visibility=View.VISIBLE

        }
        else {
            recyclerView.visibility=View.VISIBLE
            view.visibility= GONE
        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
       return searchableList.size
    }
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var  textNote:TextView=itemView.findViewById(R.id.notes)
        var  noteTitle:TextView=itemView.findViewById(R.id.noteTitle)
    }
}