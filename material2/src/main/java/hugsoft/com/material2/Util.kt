package hugsoft.com.material2

import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit

@Composable
inline fun ProvideTextStyle(
    style: TextStyle,
    alpha: Float = LocalContentAlpha.current,
    color: Color = LocalContentColor.current,
    textSelectionColors: TextSelectionColors = LocalTextSelectionColors.current,
    noinline content: @Composable () -> Unit,
) {
    val mergedStyle = LocalTextStyle.current.merge(style)
    CompositionLocalProvider(
        LocalContentColor provides color,
        LocalContentAlpha provides alpha,
        LocalTextSelectionColors provides textSelectionColors,
        LocalTextStyle provides mergedStyle,
        content = content
    )
}

@Composable
inline fun ProvideTextStyle(
    style: TextStyle = LocalTextStyle.current,
    alpha: Float = LocalContentAlpha.current,
    color: Color = LocalContentColor.current,
    textSelectionColors: TextSelectionColors = LocalTextSelectionColors.current,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    noinline content: @Composable () -> Unit,
) {
    val mergedStyle =
        style.merge(
            TextStyle(
                color = color.copy(alpha),
                fontSize = fontSize,
                fontWeight = fontWeight,
                textAlign = textAlign,
                lineHeight = lineHeight,
                fontFamily = fontFamily,
                textDecoration = textDecoration,
                fontStyle = fontStyle,
                letterSpacing = letterSpacing
            )
        )

    CompositionLocalProvider(
        LocalTextSelectionColors provides textSelectionColors,
        LocalTextStyle provides mergedStyle,
        content = content
    )
}

val ContentAlpha.Divider
    get() = hugsoft.com.material2.Divider
private const val Divider = 0.12f


val ContentAlpha.Indication
    get() = hugsoft.com.material2.Indication
private const val Indication = 0.1f
