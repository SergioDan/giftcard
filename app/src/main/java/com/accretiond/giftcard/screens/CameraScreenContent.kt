package com.accretiond.giftcard.screens

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.NavigateNext
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.accretiond.giftcard.R
import com.accretiond.giftcard.composables.ButtonWithText
import com.accretiond.giftcard.composables.CameraCapture
import com.accretiond.giftcard.composables.ContentScaleMenu
import com.accretiond.giftcard.composables.Permission
import com.accretiond.styletransfer.TransformationActivity
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import java.io.File

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
fun CameraContent(
    modifier: Modifier = Modifier,
    onImageFile: (File) -> Unit = { }
) {
    val context = LocalContext.current
    Permission(
        permission = Manifest.permission.CAMERA,
        permissionNotAvailableContent = {
            NoPermissionContent(modifier = modifier, context = context)
        }
    ) {
        CameraCapture(
            modifier = modifier,
            onImageFile = onImageFile
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CameraMainContent(
    modifier: Modifier,
    onStyledImage: (String) -> Unit = {}
) {
    val emptyImageUri = Uri.parse("file://dev/null")
    var imageUri by remember {
        mutableStateOf(emptyImageUri)
    }

    var contentScale: ContentScale by remember {
        mutableStateOf(ContentScale.None)
    }

    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = {
            it.data?.getStringExtra("styled_path")?.let {styledPath ->
                onStyledImage(styledPath)
            }
        }
    )

    if (imageUri != emptyImageUri) {
        Box(modifier) {
            GlideImage(
                model = imageUri,
                contentDescription = null,
                contentScale = contentScale,
                alignment = Alignment.TopCenter,
                modifier = Modifier.fillMaxSize()
            )
            Column(modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(8.dp),) {
                ContentScaleMenu(
                    modifier = Modifier.background(Color.Cyan),
                    onContentScale = {
                        contentScale = it
                    }
                )
                Row(

                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    ButtonWithText(
                        imageVector = Icons.Outlined.Delete,
                        text = stringResource(id = R.string.delete),
                        onClick = {
                            imageUri = emptyImageUri
                        },
                        modifier = Modifier.weight(1f)
                    )
                    ButtonWithText(
                        imageVector = Icons.Outlined.NavigateNext,
                        text = stringResource(id = R.string.next),
                        onClick = {
                            launcher.launch(
                                Intent(context, TransformationActivity::class.java).apply {
                                    this.putExtra("path", imageUri.path)
                                }
                            )
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    } else {
        CameraContent(
            modifier = modifier,
            onImageFile = {
                imageUri = it.toUri()
            }
        )
    }
}

