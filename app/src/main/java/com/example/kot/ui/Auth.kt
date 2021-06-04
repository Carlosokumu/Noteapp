package com.example.kot.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.view.View.*
import android.widget.Toast
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.core.remove
import androidx.lifecycle.lifecycleScope
import com.example.kot.Models.User
import io.objectbox.Box
import kotlinx.coroutines.launch
import com.example.kot.Objects.ObjectBox
import com.example.kot.R
import com.example.kot.databinding.ActivityAuthBinding
import kotlinx.coroutines.flow.first

class Auth : AppCompatActivity() {
    private lateinit var binding : ActivityAuthBinding
     private lateinit var box: Box<User>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        box= ObjectBox.boxStore.boxFor(User::class.java)
        binding.login.setOnClickListener {
            lifecycleScope.launch {
                setUpData()
            }
        }
        setSet()
        binding.remove.setOnClickListener {
           lifecycleScope.launch { clearData() }
        }
    }
    private suspend fun setUpData()= run {
        if(validate()) {
            val user = User(firstPassword = binding.firstPassword.text.toString().toInt(), confirmPassword = binding.secondPassword.text.toString().toInt())
            box.put(user)
            binding.Vis.visibility= GONE
            binding.profile.visibility=GONE
            binding.gone.visibility = VISIBLE
            ObjectBox.dataStore.edit { settings->
               settings[Keys.SET]=true
                settings[Keys.SET_FOR_APP]=true
            }
        }
    }
    private fun  validate(): Boolean{
        return when(TextUtils.isEmpty(binding.firstPassword.text )|| TextUtils.isEmpty(binding.secondPassword.text)){
            true->{
                Toast.makeText(applicationContext,"fields cannot be empty and must match",Toast.LENGTH_SHORT).show()
                false
            }
            false ->{
                true
            }
        }

    }
    /*
    private fun  valid(): Boolean{
        return when(ObjectsCompat.equals(firstPassWord.text , secondPassWord.text)){
            true->{
                passFirst.error=R.string.app_name.toString()
                true
            }
            false ->{
                false
            }
        }
    }

     */
    object Keys {
        val SET = preferencesKey<Boolean>("set_password")
        val SET_FOR_APP = preferencesKey<Boolean>("all_app")

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId==android.R.id.home){
            lifecycleScope.launch {
                if(check()){
                    ObjectBox.dataStore.edit { settings->
                        settings[Keys.SET]=false
                    }
                }
            }
            this.finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        lifecycleScope.launch {
            if(check()){
                ObjectBox.dataStore.edit { settings->
                    settings[Keys.SET]=false
                }
            }
        }

    }
    private suspend fun check(): Boolean{
        return ObjectBox.dataStore.data.first()[Auth.Keys.SET] ?: false
    }
    private suspend fun forAppLevel(): Boolean{
        return ObjectBox.dataStore.data.first()[Keys.SET_FOR_APP] ?: false
    }
    private fun setSet(){
       lifecycleScope.launch {
           if(forAppLevel()){
               binding.Vis.visibility= GONE
               binding.profile.visibility=GONE
               binding.gone.visibility = VISIBLE
           }
          }

        }
    /*
    This function clears the password set if any
     */
    private suspend fun clearData(){
        ObjectBox.dataStore.edit {
           it.remove(Keys.SET_FOR_APP)
            it.remove(Keys.SET)
        }
        box.removeAll()
        Toast.makeText(this,"successfully removed",Toast.LENGTH_SHORT).show()
        this.finish()
    }
}



