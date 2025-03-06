package com.example.snappychat.cameraActivity

import android.app.Application
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.lifecycle.AndroidViewModel

class CameraViewModel(application: Application) : AndroidViewModel(application) {
    val cameraController = LifecycleCameraController(application).apply {
        setEnabledUseCases(CameraController.IMAGE_CAPTURE or CameraController.VIDEO_CAPTURE)
    }
}
