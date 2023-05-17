package com.accretiond.giftcard.utils

import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.nio.ByteBuffer
import java.util.concurrent.Executor
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

suspend fun ImageCapture.takePicture(executor: Executor): File {
    val photoFile = withContext(Dispatchers.IO) {
        kotlin.runCatching {
            File.createTempFile("image", "jpg")
        }.getOrElse {exception ->
            Log.e("TakePicture", "Failed to create temporary file", exception)
            File("/dev/null")
        }
    }

    return suspendCoroutine { continuation ->
        val outputOptions = ImageCapture
            .OutputFileOptions
            .Builder(photoFile)
            .build()

        takePicture(outputOptions, executor, object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                continuation.resume(photoFile)
            }

            override fun onError(exception: ImageCaptureException) {
                Log.e("TakePicture", "Failed to capture image", exception)
                continuation.resumeWithException(exception)
            }
        })
    }
}

suspend fun ImageCapture.takePictureWithSavingIt(executor: Executor): Pair<ByteBuffer, Int> {
    return suspendCoroutine {continuation ->
        takePicture(executor, object : ImageCapture.OnImageCapturedCallback() {
            override fun onCaptureSuccess(image: ImageProxy) {
                continuation.resume(image.planes[0].buffer to image.imageInfo.rotationDegrees)
                image.close()
            }

            override fun onError(exception: ImageCaptureException) {
                Log.e("TakePicture", "Failed to capture image", exception)
                continuation.resumeWithException(exception)
            }
        })
    }
}