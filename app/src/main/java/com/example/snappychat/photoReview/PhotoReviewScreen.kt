package com.example.snappychat.photoReview

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp

@Composable
fun PhotoReviewScreen(photo: Bitmap, onClose: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            bitmap = photo.asImageBitmap(),
            contentDescription = "Photo",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        IconButton(
            onClick = { onClose() },
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(12.dp, 12.dp)
                .width(35.dp)
                .height(35.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close Photo",
                tint = Color.White,
                modifier = Modifier
                    .fillMaxSize()
            )
        }
    }
}