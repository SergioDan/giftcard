package com.accretiond.giftcard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.accretiond.giftcard.ui.theme.GiftCardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GiftCardTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GreetingImage(
                        message = stringResource(R.string.happy_mother_s_day_mom),
                        from = stringResource(R.string.your_son_and_husband)
                    )
                }
            }
        }
    }
}

@Composable
fun GreetingText(message: String,
                 from: String,
                 modifier: Modifier = Modifier) {

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Text(
            text = message,
            fontSize = 95.sp,
            lineHeight = 116.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary
        )

        Column(
            modifier = Modifier
                .align(Alignment.End)
                .padding(16.dp)
        ) {
            Text(
                text = "From",
                fontSize= 14.sp,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.align(Alignment.End).padding(top = 4.dp)
            )
            Text(
                text = from,
                fontSize= 30.sp,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.align(Alignment.End)
            )
        }

    }
}

@Composable
fun GreetingImage(message: String,
                 from: String,
                 modifier: Modifier = Modifier) {
    val image = painterResource(id = R.drawable.p_20210704_140942)

    Box {
        Image(
            painter = image,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            alignment = Alignment.TopCenter,
            alpha = 0.5F,
            modifier = Modifier
                .fillMaxSize()
                .blur(1.dp)
        )
        GreetingText(
            message = message,
            from = from,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GiftCardTheme {
        GreetingText(
            message = stringResource(id = R.string.happy_mother_s_day_mom),
            from = stringResource(id = R.string.your_son_and_husband)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingImagePreview() {
    GiftCardTheme {
        GreetingImage(
            message = stringResource(id = R.string.happy_mother_s_day_mom),
            from = stringResource(id = R.string.your_son_and_husband)
        )
    }
}