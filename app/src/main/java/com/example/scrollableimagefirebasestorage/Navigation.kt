package com.example.scrollableimagefirebasestorage

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.storage.StorageReference

@Composable
fun Navigation(navController: NavHostController, imagesStorageRef:StorageReference,videosStorageReference:StorageReference){
    
    NavHost(navController = navController, startDestination = "Home"){
        composable("Home"){
            Home(navController = navController )
        }
        composable("UploadAndShow"){
            UploadAndShowScreen(storageRef = imagesStorageRef)
        }
        composable("UploadAndShowVideos"){
            UploadAndShowVideosScreen(storageRef = videosStorageReference)
        }
        
    }

}