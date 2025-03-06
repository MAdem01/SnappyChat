package com.example.snappychat

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation

import androidx.navigation.compose.rememberNavController
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
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "auth"){

                    navigation(startDestination = "loginScreen", route = "auth"){
                        composable("loginScreen"){
                            LoginScreen(navController)
                        }
                    }
                    navigation(startDestination = "cameraPreview", route = "main"){
                        composable("cameraPreview"){
                            CameraScreen(
                                controller,
                                onPhotoTaken =
                                { photo ->
                                    photoViewModel.setPhoto(photo)
                                    navController.navigate("photoReview")
                                },
                                this@MainActivity
                            )
                        }
                        composable("photoReview"){
                            PhotoReviewScreen(
                                photoViewModel.currentPhoto!!,
                                onClose = {
                                    navController.navigate("cameraPreview"){
                                        popUpTo("photoReview"){
                                            inclusive = true
                                        }
                                    }

                                }
                            )
                        }
                    }
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
