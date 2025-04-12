package com.example.scrollableimagefirebasestorage


import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.scrollableimagefirebasestorage.ui.theme.ScrollableimagefirebaseStorageTheme
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val storage = Firebase.storage
        val imageStorageRef = storage.reference.child("images")
        val videoStorageRef = storage.reference.child("videos")




        setContent {
            val navController = rememberNavController() // SAFE AND CORRECT
            ScrollableimagefirebaseStorageTheme {
                Surface(
                    modifier = Modifier,
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation(
                        navController = navController,
                        imagesStorageRef = imageStorageRef,
                        videosStorageReference = videoStorageRef
                    )
                }
            }
        }

    }
}
