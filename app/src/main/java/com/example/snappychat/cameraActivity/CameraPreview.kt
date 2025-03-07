package com.example.snappychat.cameraActivity

import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun CameraPreview(
    controller: LifecycleCameraController,
    modifier: Modifier = Modifier
){
    val lifeCycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
    AndroidView(
        factory = {
            PreviewView(it).apply{
                this.controller = controller
                controller.bindToLifecycle(lifeCycleOwner)
            }
        },
        modifier = modifier
    )
}