package com.accretiond.giftcard.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ContentScaleMenu(
    modifier: Modifier = Modifier,
    onContentScale: (ContentScale) -> Unit = { }
) {
    var expanded by remember {
        mutableStateOf(false)
    }

    var selectedText: ContentScale by remember {
        mutableStateOf(ContentScale.None)
    }

    val contentScales = listOf("None" to ContentScale.None,
        "Crop" to ContentScale.Crop,
        "FillBounds" to ContentScale.FillBounds,
        "FillHeight" to ContentScale.FillHeight,
        "FillWidth" to ContentScale.FillWidth,
        "Fit" to ContentScale.Fit,
        "Inside" to ContentScale.Inside)

    val icon = if(expanded) {
        Icons.Outlined.KeyboardArrowUp
    } else {
        Icons.Outlined.KeyboardArrowDown
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
       Text(
           text = contentScales
               .firstOrNull { it.second == selectedText }
               ?.first ?: "None"
       )
        Spacer(modifier = Modifier.width(10.dp))
        IconButton(
            modifier = Modifier.size(20.dp),
            onClick = {
                expanded = true
            }
        ) {
            Icon(imageVector = icon, contentDescription = null)
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(Color.Cyan)
        ) {
            contentScales.forEach {scale ->
                DropdownMenuItem(
                    text = {
                        Text(text = scale.first)
                    },
                    onClick = {
                        onContentScale(scale.second)
                        selectedText = scale.second
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun ContentScaleMenuPreview() {
    ContentScaleMenu()
}