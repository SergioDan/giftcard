package com.accretiond.giftcard.composables

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.accretiond.giftcard.R
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionRequired
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Permission(
    permission: String = android.Manifest.permission.CAMERA,
    rationale: String = stringResource(id = R.string.permission_explanation),
    permissionNotAvailableContent: @Composable () -> Unit = { },
    content: @Composable () -> Unit = { }
) {
    val permissionState = rememberPermissionState(permission)
    PermissionRequired(
        permissionState = permissionState,
        permissionNotGrantedContent = {
            Rationale(
                text = rationale,
                onRequestPermission = { permissionState.launchPermissionRequest() }
            )
        },
        permissionNotAvailableContent = permissionNotAvailableContent,
        content = content
    )

}

@Composable
fun Rationale(text: String, onRequestPermission: () -> Unit ) {
    AlertDialog(onDismissRequest = { /* not allowed */ },
        title = {
            Text(text = stringResource(id = R.string.permission_alert_title))
        },
        text = {
            Text(text = text)
        },
        confirmButton = {
            Button(onClick= onRequestPermission) {
                Text(text = stringResource(id = R.string.permission_alert_confirm))
            }
        }
    ) 
}
