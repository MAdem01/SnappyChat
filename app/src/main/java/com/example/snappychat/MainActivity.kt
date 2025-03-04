package com.example.snappychat

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.runtime.remember
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.snappychat.cameraActivity.CameraScreen
import com.example.snappychat.photoReview.PhotoReviewScreen
import com.example.snappychat.ui.theme.SnappyChatTheme
import com.example.snappychat.viewModel.PhotoViewModel

class MainActivity : ComponentActivity() {
    private val photoViewModel : PhotoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(!hasRequiredPermissions()) {
            ActivityCompat.requestPermissions(
                this,
                CAMERAX_PERMISSIONS,
                0
            )
        }

        setContent {
            SnappyChatTheme {
                val currentPhoto = photoViewModel.currentPhoto
                val controller = remember {
                    LifecycleCameraController(applicationContext).apply {
                        setEnabledUseCases(
                            CameraController.IMAGE_CAPTURE or
                                CameraController.VIDEO_CAPTURE
                        )
                    }
                }
                if(currentPhoto == null) {
                   CameraScreen(
                       controller = controller,
                       onPhotoTaken = {photo -> photoViewModel.setPhoto(photo)},
                       this
                   )
                }else{
                    PhotoReviewScreen(
                        photo = currentPhoto,
                        onClose = {photoViewModel.clearPhoto()}
                    )
                }
            }
        }
    }

    private fun hasRequiredPermissions(): Boolean {
        return CAMERAX_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(
                applicationContext,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    companion object {
        private val CAMERAX_PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO
        )
    }
}
