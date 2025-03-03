package com.example.snappychat

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cameraswitch
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.snappychat.ui.theme.SnappyChatTheme

class MainActivity : ComponentActivity() {

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
                var currentPhoto by remember { mutableStateOf<Bitmap?>(null) }
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
                       onPhotoTaken = {photo -> currentPhoto = photo}
                   )
                }else{
                    PhotoPreviewScreen(
                        photo = currentPhoto!!,
                        onClose = {currentPhoto = null}
                    )
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun CameraScreen(controller: LifecycleCameraController, onPhotoTaken: (Bitmap) -> Unit) {
        val scaffoldState = rememberBottomSheetScaffoldState()

        BottomSheetScaffold(
            scaffoldState = scaffoldState,
            sheetPeekHeight = 0.dp,
            sheetContent = {  }
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                CameraPreview(controller = controller, modifier = Modifier.fillMaxSize())

                IconButton(
                    onClick = { toggleCamera(controller) },
                    modifier = Modifier.align(Alignment.TopStart)
                ) {
                    Icon(imageVector = Icons.Default.Cameraswitch, contentDescription = "Switch Camera")
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    IconButton(
                        onClick = { takePhoto(controller, onPhotoTaken) }
                    ) {
                        Icon(imageVector = Icons.Default.PhotoCamera, contentDescription = "Take Photo")
                    }
                }
            }
        }
    }

    @Composable
    fun PhotoPreviewScreen(photo: Bitmap, onClose: () -> Unit) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                bitmap = photo.asImageBitmap(),
                contentDescription = "Photo",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            IconButton(
                onClick = { onClose() },
                modifier = Modifier.align(Alignment.TopStart)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close Photo")
            }
        }
    }


    private fun takePhoto(
        controller: LifecycleCameraController,
        onPhotoTaken: (Bitmap) -> Unit
    ){
        controller.takePicture(
            ContextCompat.getMainExecutor(applicationContext),
            object : ImageCapture.OnImageCapturedCallback(){
                override fun onCaptureSuccess(image: ImageProxy) {
                    super.onCaptureSuccess(image)
                    onPhotoTaken(image.toBitmap())
                    image.close()
                }

                override fun onError(exception: ImageCaptureException) {
                    super.onError(exception)
                    Log.e("Camera", "Error taking a picture", exception)
                }
            }
        )
    }

    private fun hasRequiredPermissions(): Boolean {
        return CAMERAX_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(
                applicationContext,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun toggleCamera(controller:LifecycleCameraController){
        if(controller.cameraSelector == CameraSelector.DEFAULT_FRONT_CAMERA){
            controller.cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
        }else{
            controller.cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
        }
    }

    companion object {
        private val CAMERAX_PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO
        )
    }
}
