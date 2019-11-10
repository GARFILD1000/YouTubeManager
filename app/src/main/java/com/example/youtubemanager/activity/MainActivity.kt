package com.example.youtubemanager.activity

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.youtubemanager.R
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.youtubemanager.App

class MainActivity : AppCompatActivity() {
    companion object {
        const val PERMISSIONS_REQUEST_CODE = 20001
    }

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
}
