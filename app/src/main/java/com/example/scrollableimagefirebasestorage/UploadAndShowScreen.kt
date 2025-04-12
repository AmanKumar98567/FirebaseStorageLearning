package com.example.scrollableimagefirebasestorage


import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.storage.StorageReference
import java.util.*

@Composable
fun UploadAndShowScreen(storageRef: StorageReference) {
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var imageUrls by remember { mutableStateOf<List<String>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    // Image Picker
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
        val fileName = UUID.randomUUID().toString()
        val ref = storageRef.child(fileName)

        uri?.let {
            ref.putFile(it).addOnSuccessListener {
                Toast.makeText(context, "Image uploaded", Toast.LENGTH_SHORT).show()
                fetchImageUrlsFromStorage(storageRef,
                    onResult = {
                        imageUrls = it
                        isLoading = false
                    },
                    onError = {
                        Toast.makeText(context, "Error: ${it.message}", Toast.LENGTH_SHORT).show()
                        isLoading = false
                    }
                )
            }
        }
    }

    // Fetch images on first load
    LaunchedEffect(Unit) {
        fetchImageUrlsFromStorage(
            storageRef,
            onResult = {
                imageUrls = it
                isLoading = false
            },
            onError = {
                Toast.makeText(context, "Error: ${it.message}", Toast.LENGTH_SHORT).show()
                isLoading = false
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(onClick = { launcher.launch("image/*") }) {
            Text("Pick and Upload Image")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(imageUrls) { url ->
                    Image(
                        painter = rememberAsyncImagePainter(model = url),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .border(2.dp, Color.Gray, RoundedCornerShape(16.dp))
                    )
                }
            }
        }
    }
}
