package com.example.kot.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import androidx.core.util.ObjectsCompat
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.lifecycleScope
import androidx.transition.Scene
import androidx.transition.Transition
import androidx.transition.TransitionInflater
import androidx.transition.TransitionManager
import com.example.kot.Models.User
import com.example.kot.Objects.ObjectBox
import com.example.kot.R
import com.example.kot.databinding.ActivityLogBinding
import io.objectbox.Box
import kotlinx.android.synthetic.main.activity_log.*
import kotlinx.coroutines.launch

class LogActivity : AppCompatActivity() {
    private  lateinit var box:Box<User>
    private lateinit var binding: ActivityLogBinding
    private  lateinit var scene1: Scene
    private lateinit var scene2: Scene
    private lateinit var transition: Transition
    private  lateinit var currentScene: Scene
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_help)
        box= ObjectBox.boxStore.boxFor(User::class.java)
        scene1= Scene.getSceneForLayout(sceneLayout, R.layout.scene1,this)
        scene2= Scene.getSceneForLayout(sceneLayout, R.layout.scene2,this)
        transition=TransitionInflater.from(this).inflateTransition(R.transition.layout_transition)
        scene2.enter()
        currentScene=scene2
        binding.btnUnLock.setOnClickListener {
           lifecycleScope.launch { unLock() }
        }
    }
    private suspend fun unLock(){
        val user: User= box.all[0]
        if (TextUtils.isEmpty(binding.setPassWord.text.toString())) return
        if (ObjectsCompat.equals(user.firstPassword ,binding.setPassWord.text.toString().toInt())){
            ObjectBox.dataStore.edit { settings->
                settings[Auth.Keys.SET]=false
            }
           startActivity(Intent(this, MainActivity::class.java))
            this.finish()

        }
        else{
            TransitionManager.go(scene1,transition)
            currentScene=scene1
          binding.passError.error="incorrect PassWord"
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId==android.R.id.home){
         TODO(reason = "User password recovery not implemented yet")
        }
        return super.onOptionsItemSelected(item)
    }
}