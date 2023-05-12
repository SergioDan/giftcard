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
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
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

@OptIn(ExperimentalTextApi::class)
@Composable
fun GreetingText(message: String,
                 from: String,
                 modifier: Modifier = Modifier) {

    val gradiendColors = listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.secondary, MaterialTheme.colorScheme.tertiary)

    Column(
        verticalArrangement = Arrangement.Bottom,
        modifier = modifier
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(brush = Brush.linearGradient(gradiendColors))) {
                    append(message)
                }
            },
            fontSize = 45.sp,
            lineHeight = 50.sp,
            textAlign = TextAlign.Start
        )

        Column(
            modifier = Modifier
                .align(Alignment.End)
                .padding(16.dp)
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(ParagraphStyle(textAlign = TextAlign.End)) {
                        withStyle(SpanStyle(fontSize = 14.sp)) {
                            append("From\n")
                        }
                        withStyle(SpanStyle(fontSize = 30.sp)) {
                            append(from)
                        }
                    }

                },
                color = MaterialTheme.colorScheme.tertiary,
                style = LocalTextStyle.current.merge(
                    TextStyle(
                        lineHeight = 2.5.em,
                        lineHeightStyle = LineHeightStyle(
                            alignment = LineHeightStyle.Alignment.Center,
                            trim = LineHeightStyle.Trim.None
                        )
                    )
                ),
                modifier = Modifier.align(Alignment.End)
            )
        }

    }
}

@Composable
fun GreetingImage(message: String,
                 from: String,
                 modifier: Modifier = Modifier) {
    val image = painterResource(id = R.drawable.androidparty)

    Box(modifier) {
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