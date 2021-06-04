package com.example.kot.Models

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import org.jetbrains.annotations.Nullable

@Entity
data class User(@Id var id: Long = 0, @Nullable var firstPassword: Int?=null,@Nullable var confirmPassword: Int?=null)
