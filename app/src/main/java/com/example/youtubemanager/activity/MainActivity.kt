package com.example.youtubemanager.activity

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.youtubemanager.R
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.youtubemanager.App
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity(){
    companion object {
        const val PERMISSIONS_REQUEST_CODE = 20001
        const val RECEIVE_FIREBASE_MESSAGE = "com.example.youtubemanager.RECEIVE_FIREBASE_MESSAGE"
        private const val LOG_TAG = "MainActivity"
    }

    private val broadcastReceiver = object: BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let {broadcastIntent ->
                if (broadcastIntent.action == RECEIVE_FIREBASE_MESSAGE) {
                    val messageTitle = broadcastIntent.extras?.getString("title")
                    val messageBody = broadcastIntent.extras?.getString("body")
                    Toast.makeText(
                        this@MainActivity,
                        messageBody,
                        Toast.LENGTH_LONG
                    ).show()
                    Log.d(LOG_TAG, "Firebase Message title='$messageTitle' body='$messageBody'")
                }
            }
        }
    }
    private lateinit var broadcastManager: LocalBroadcastManager

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        broadcastManager = LocalBroadcastManager.getInstance(this)
        broadcastManager.registerReceiver(
            broadcastReceiver,
            IntentFilter().apply{
                addAction(RECEIVE_FIREBASE_MESSAGE)
            }
        )

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        checkAllNeededPermissions()
    }

    private fun checkAllNeededPermissions() {
        val application = (applicationContext as App)
        val neededPermissions = arrayOf(Manifest.permission.INTERNET)

        val allPermissionsGranted = application.checkPermissions(this, *neededPermissions)
        if (!allPermissionsGranted) {
            application.requestPermissions(this, PERMISSIONS_REQUEST_CODE, *neededPermissions)
        }
    }

    fun goWatchVideo() {
        navController.navigate(R.id.playVideoFragment)
    }

    fun goToChannel() {
        navController.navigate(R.id.channelFragment)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let {
            val text = it.extras?.get("firebase_message")
            Toast.makeText(this, "Received firebase message: $text", Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroy() {
        broadcastManager.unregisterReceiver(broadcastReceiver)
        super.onDestroy()
    }
}
