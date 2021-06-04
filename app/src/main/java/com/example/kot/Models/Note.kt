package com.example.kot.Models

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "notes_table")
@Parcelize
data class Note(
    @PrimaryKey(autoGenerate = true)
    var noteId: Int=0,
    val noteText: String,
    val createdAt: String,
    val noteTitle: String
):Parcelable
