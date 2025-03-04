package com.example.snappychat.viewModel

import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class PhotoViewModel : ViewModel(){
    var currentPhoto by mutableStateOf<Bitmap?>(null)
        private set

    fun setPhoto(photo : Bitmap){
        currentPhoto = photo
    }

    fun clearPhoto(){
        currentPhoto = null
    }
}