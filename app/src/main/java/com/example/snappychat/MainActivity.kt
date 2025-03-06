package com.example.snappychat

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.snappychat.cameraActivity.CameraScreen
import com.example.snappychat.cameraActivity.CameraViewModel
import com.example.snappychat.photoReview.PhotoReviewScreen
import com.example.snappychat.ui.theme.SnappyChatTheme
import com.example.snappychat.viewModel.PhotoViewModel

class MainActivity : ComponentActivity() {
    private val photoViewModel : PhotoViewModel by viewModels()
    private val cameraViewModel: CameraViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hasRequiredPermissions()

        setContent {
            SnappyChatTheme {
                val controller = cameraViewModel.cameraController
                if(photoViewModel.currentPhoto == null){
                    CameraScreen(
                        controller = controller,
                        onPhotoTaken =
                            { photo ->
                            photoViewModel.setPhoto(photo)
                            },
                        this
                    )
                }else{
                    PhotoReviewScreen(
                        photoViewModel.currentPhoto!!,
                        onClose = {
                            photoViewModel.clearPhoto()
                        }
                    )
                }
            }
        }
    }


    private fun hasRequiredPermissions(){
        if(CAMERAX_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(
                applicationContext,
                it
            ) == PackageManager.PERMISSION_DENIED
        }){
            ActivityCompat.requestPermissions(
                this,
                CAMERAX_PERMISSIONS,
                0
            )
        }
    }

    companion object {
        private val CAMERAX_PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO
        )
    }
}
