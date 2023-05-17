package com.accretiond.styletransfer

import android.graphics.Bitmap
import android.graphics.Matrix
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

fun Bitmap.saveToJPG(): File? {
    //save bitmap to file
   return try {
       val file = File.createTempFile("styleImage", "jpg")
       val stream: OutputStream = FileOutputStream(file)
       this.compress(Bitmap.CompressFormat.JPEG,25,stream)
       stream.flush()
       stream.close()
       file
   } catch (exception: Exception) {
       Log.e("BitmapUtils", "Saving bitmap to JPG file failed", exception)
       null
   }
}

fun Bitmap.rotate(degrees: Float): Bitmap {
    val matrix = Matrix().apply { postRotate(degrees) }
    return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
}
