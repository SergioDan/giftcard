package com.accretiond.giftcard.screens

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.accretiond.giftcard.R
import com.accretiond.giftcard.composables.CameraPreview
import com.accretiond.giftcard.composables.Permission

@Composable
fun NoPermissionContent(modifier: Modifier, context: Context) {
    Column(modifier) {
        Text(text = stringResource(id = R.string.no_camera))
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                context.startActivity(
                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", context.packageName, null)
                    }
                )
            }
        ) {
            Text(text = stringResource(id = R.string.open_settings))
        }
    }
}

@Composable
fun CameraScreenContent(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Permission(
        permission = Manifest.permission.CAMERA,
        permissionNotAvailableContent = {
            NoPermissionContent(modifier = modifier, context = context)
        }
    ) {
        CameraPreview(modifier = modifier)
    }
}