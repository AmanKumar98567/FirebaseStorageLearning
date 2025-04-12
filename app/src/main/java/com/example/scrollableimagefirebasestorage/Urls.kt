package com.example.scrollableimagefirebasestorage


import com.google.firebase.storage.StorageReference

fun fetchImageUrlsFromStorage(
    storageRef: StorageReference,
    onResult: (List<String>) -> Unit,
    onError: (Exception) -> Unit
) {
    storageRef.listAll()
        .addOnSuccessListener { listResult ->
            val imageUrls = mutableListOf<String>()
            val items = listResult.items
            if (items.isEmpty()) onResult(emptyList())

            var completed = 0
            items.forEach { item ->
                item.downloadUrl.addOnSuccessListener { uri ->
                    imageUrls.add(uri.toString())
                    completed++
                    if (completed == items.size) {
                        onResult(imageUrls)
                    }
                }.addOnFailureListener {
                    onError(it)
                }
            }
        }
        .addOnFailureListener {
            onError(it)
        }
}



fun fetchVideoUrlsFromStorage(
    storageRef: StorageReference,
    onResult: (List<String>) -> Unit,
    onError: (Exception) -> Unit
) {
    storageRef.listAll()
        .addOnSuccessListener { listResult ->
            val videoUrls = mutableListOf<String>()
            val items = listResult.items
            if (items.isEmpty()) onResult(emptyList())

            var completed = 0
            items.forEach { item ->
                item.downloadUrl.addOnSuccessListener { uri ->
                    videoUrls.add(uri.toString())
                    completed++
                    if (completed == items.size) {
                        onResult(videoUrls)
                    }
                }.addOnFailureListener {
                    onError(it)
                }
            }
        }
        .addOnFailureListener {
            onError(it)
        }
}


