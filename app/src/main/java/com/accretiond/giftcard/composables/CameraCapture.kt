package com.accretiond.giftcard.composables

import android.graphics.drawable.Icon
import android.util.Log
import android.view.Surface
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.core.UseCase
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Camera
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toFile
import com.accretiond.giftcard.R
import com.accretiond.giftcard.utils.takePicture
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayInputStream
import java.io.File

@Composable
fun CameraCapture(
    modifier: Modifier,
    cameraSelector: CameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA,
    onImageFile: (File) -> Unit = { }
) {
    Box(
        modifier = modifier
    ) {
        val context = LocalContext.current
        val lifecycleOwner = LocalLifecycleOwner.current
        val coroutineScope = rememberCoroutineScope()
        var previewUseCase: UseCase by remember {
            mutableStateOf(
                Preview.Builder().build()
            )
        }
        val imageCaptureUseCase by remember {
            mutableStateOf(
                ImageCapture.Builder()
                    .setCaptureMode(ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY)
                    .setTargetRotation(Surface.ROTATION_0)
                    .build()
            )
        }

        CameraPreview(
            modifier = Modifier.fillMaxSize(),
            onUseCase = {
                previewUseCase = it
            }
        )

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement
                .spacedBy(8.dp)
        ) {
            Button(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(16.dp),
                colors = ButtonDefaults
                    .buttonColors(
                        containerColor = Color.Cyan,
                        contentColor = Color.Black
                    ),
                onClick = {
                    coroutineScope.launch {
                        onImageFile(imageCaptureUseCase.takePicture(context.executor))
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Camera,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )
            }

            PhotoPicker(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(16.dp)
                    .height(48.dp),
                onImageUriChange = {
                    it?.let { uri ->
                        coroutineScope.launch {
                            // save result to file
                            val file = withContext(Dispatchers.IO) {
                                kotlin.runCatching {
                                    File.createTempFile("selected_image", "jpg")
                                }.getOrElse {exception ->
                                    Log.e("TakePicture", "Failed to create temporary file", exception)
                                    File("/dev/null")
                                }
                            }

                            context.contentResolver.openInputStream(uri)?.let {content ->
                                val inputStream = ByteArrayInputStream(content.readBytes())

                                inputStream.use { input ->
                                    file.outputStream().use { output ->
                                        input.copyTo(output)
                                    }
                                }
                                withContext(Dispatchers.IO) {
                                    content.close()
                                }
                            }


                            onImageFile(file)
                        }
                    }
                }
            )
        }

        // launch at the beginning and after the previewUseCase changes
        LaunchedEffect(previewUseCase) {
            val cameraProvider = context.getCameraProvider()
            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    cameraSelector,
                    previewUseCase,
                    imageCaptureUseCase
                )
            } catch(exception: Exception) {
                Log.e("CameraCapture", "Lifecycle - Use case binding failed", exception)
            }
        }
    }
}