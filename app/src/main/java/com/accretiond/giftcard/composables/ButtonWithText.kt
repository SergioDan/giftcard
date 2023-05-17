package com.accretiond.giftcard.composables

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BrokenImage
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ButtonWithText(
    imageVector: ImageVector,
    text: String,
    onClick: ()-> Unit = { },
    modifier: Modifier = Modifier
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Cyan,
            contentColor = Color.Black
        )
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = null
        )
        Spacer(
            modifier = Modifier.size(ButtonDefaults.IconSpacing)
        )
        Text(
            text = text
        )
    }
}

@Preview()
@Composable
fun ButtonWithTextPreview() {
    ButtonWithText(
        imageVector = Icons.Outlined.BrokenImage,
        text = "Example"
    )
}