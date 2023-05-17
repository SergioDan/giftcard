package com.accretiond.giftcard.composables

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CameraRoll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun PhotoPicker(
    modifier: Modifier = Modifier,
    onImageUriChange: (Uri?) -> Unit
) {

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = onImageUriChange
    )

    ButtonWithText(
        modifier = modifier,
        imageVector = Icons.Outlined.CameraRoll,
        text = "Pick Photo",
        onClick = {
            singlePhotoPickerLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        }
    )
}

@Preview
@Composable
fun PhotoPickerButtonPreview() {
    PhotoPicker(onImageUriChange = {

    })
}
