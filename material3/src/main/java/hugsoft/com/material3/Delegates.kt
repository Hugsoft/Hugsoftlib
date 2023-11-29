package hugsoft.com.material3

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.RenderVectorGroup
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.PlatformParagraphStyle
import androidx.compose.ui.text.PlatformSpanStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontSynthesis
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.LocaleList
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.Hyphens
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.text.style.TextGeometricTransform
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.style.TextMotion
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp


@Composable
inline fun Label(
    text: CharSequence,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontWeight: FontWeight? = null,
    textAlign: TextAlign? = null,
    maxLines: Int = 1,
    style: TextStyle = LocalTextStyle.current,
) {
    Text(
        text = text,
        modifier = modifier,
        style = style,
        maxLines = maxLines,
        color = color,
        overflow = TextOverflow.Ellipsis,
        fontWeight = fontWeight,
        fontSize = fontSize,
        textAlign = textAlign
    )
}



@Composable
inline fun IconButton(
    icon: Painter,
    contentDescription: String?,
    noinline onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: IconButtonColors = IconButtonDefaults.iconButtonColors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    IconButton(
        onClick = onClick,
        modifier,
        enabled,
        colors,
        interactionSource,
        content = {
            Icon(painter = icon, contentDescription = contentDescription)
        }
    )
}


@Composable
inline fun IconButton(
    icon: ImageVector,
    contentDescription: String?,
    noinline onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: IconButtonColors = IconButtonDefaults.iconButtonColors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    IconButton(
        rememberVectorPainter(image = icon),
        contentDescription,
        onClick,
        modifier,
        enabled,
        colors,
        interactionSource
    )
}


@Composable
inline fun Text(
    text: CharSequence,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    inlineContent: Map<String, InlineTextContent> = mapOf(),
    noinline onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current
) {
    when(text){
        is String -> Text(text, modifier, color, fontSize, fontStyle, fontWeight, fontFamily, letterSpacing, textDecoration, textAlign, lineHeight, overflow, softWrap, maxLines, minLines, onTextLayout, style)
        is AnnotatedString -> Text(text, modifier, color, fontSize, fontStyle, fontWeight, fontFamily, letterSpacing, textDecoration, textAlign, lineHeight, overflow, softWrap, maxLines, minLines, inlineContent, onTextLayout, style)
        else -> error("$text must be either AnnotatedString or String!!")
    }
}


@Composable
inline fun Button(
    label: CharSequence,
    noinline onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: Painter? = null,
    enabled: Boolean = true,
    shape: Shape = ButtonDefaults.shape,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
    border: BorderStroke? = null,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    androidx.compose.material3.Button(
        onClick,
        modifier,
        enabled,
        shape,
        colors,
        elevation,
        border,
        contentPadding,
        interactionSource,
        content = {
            if (icon != null) Icon(
                painter = icon,
                contentDescription = null,
                modifier = Modifier.padding(end = ButtonDefaults.IconSpacing)
            )
            Label(text = label)
        }
    )
}

@Composable
inline fun Button2(
    label: CharSequence,
    noinline onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: Painter? = null,
    enabled: Boolean = true,
    shape: Shape = ButtonDefaults.shape,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
    border: BorderStroke? = null,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    Button2(
        onClick,
        modifier,
        enabled,
        shape,
        colors,
        elevation,
        border,
        contentPadding,
        interactionSource,
        content = {
            if (icon != null) Icon(
                painter = icon,
                contentDescription = null,
                modifier = Modifier.padding(bottom = ButtonDefaults.IconSpacing)
            )
            Label(text = label)
        }
    )
}

@Composable
inline fun OutlinedButton(
    label: CharSequence,
    noinline onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: Painter? = null,
    enabled: Boolean = true,
    shape: Shape = ButtonDefaults.outlinedShape,
    colors: ButtonColors = ButtonDefaults.outlinedButtonColors(),
    elevation: ButtonElevation? = null,
    border: BorderStroke? = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(0.12f)),
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    androidx.compose.material3.OutlinedButton(
        onClick,
        modifier,
        enabled,
        shape,
        colors,
        elevation,
        border,
        contentPadding,
        interactionSource
    ) {
        if (icon != null)
            Icon(painter = icon, contentDescription = null, modifier = Modifier.padding(end = ButtonDefaults.IconSpacing))
        Label(text = label)
    }
}


@Composable
inline fun OutlinedButton2(
    label: CharSequence,
    noinline onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: Painter? = null,
    enabled: Boolean = true,
    shape: Shape = ButtonDefaults.outlinedShape,
    colors: ButtonColors = ButtonDefaults.outlinedButtonColors(),
    elevation: ButtonElevation? = null,
    border: BorderStroke? = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(0.12f)),
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    OutlinedButton2(
        onClick,
        modifier,
        enabled,
        shape,
        colors,
        elevation,
        border,
        contentPadding,
        interactionSource
    ) {
        if (icon != null)
            Icon(painter = icon, contentDescription = null, modifier = Modifier.padding(bottom = ButtonDefaults.IconSpacing))
        Label(text = label)
    }
}

@Composable
inline fun TextButton(
    label: CharSequence,
    noinline onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: Painter? = null,
    enabled: Boolean = true,
    shape: Shape = ButtonDefaults.textShape,
    colors: ButtonColors = ButtonDefaults.textButtonColors(),
    elevation: ButtonElevation? = null,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = ButtonDefaults.TextButtonContentPadding,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    androidx.compose.material3.TextButton(
        onClick,
        modifier,
        enabled,
        shape,
        colors,
        elevation,
        border,
        contentPadding,
        interactionSource
    ) {
        if (icon != null)
            Icon(painter = icon, contentDescription = null,   Modifier.padding(end = ButtonDefaults.IconSpacing))
        Label(text = label)
    }
}

@Composable
inline fun TextButton2(
    label: CharSequence,
    noinline onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: Painter? = null,
    enabled: Boolean = true,
    shape: Shape = ButtonDefaults.textShape,
    colors: ButtonColors = ButtonDefaults.textButtonColors(),
    elevation: ButtonElevation? = null,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = ButtonDefaults.TextButtonContentPadding,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    TextButton2(
        onClick,
        modifier,
        enabled,
        shape,
        colors,
        elevation,
        border,
        contentPadding,
        interactionSource
    ) {
        if (icon != null)
            Icon(painter = icon, contentDescription = null,   Modifier.padding(bottom = ButtonDefaults.IconSpacing))
        Label(text = label)
    }
}

@Composable
inline fun FilledTonalButton(
    label: CharSequence,
    noinline onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: Painter? = null,
    enabled: Boolean = true,
    shape: Shape = ButtonDefaults.textShape,
    colors: ButtonColors = ButtonDefaults.textButtonColors(),
    elevation: ButtonElevation? = null,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = ButtonDefaults.TextButtonContentPadding,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    FilledTonalButton2(
        onClick,
        modifier,
        enabled,
        shape,
        colors,
        elevation,
        border,
        contentPadding,
        interactionSource
    ) {
        if (icon != null)
            Icon(painter = icon, contentDescription = null,   Modifier.padding(end = ButtonDefaults.IconSpacing))
        Label(text = label)
    }
}

@Composable
inline fun FilledTonalButton2(
    label: CharSequence,
    noinline onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: Painter? = null,
    enabled: Boolean = true,
    shape: Shape = ButtonDefaults.textShape,
    colors: ButtonColors = ButtonDefaults.textButtonColors(),
    elevation: ButtonElevation? = null,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = ButtonDefaults.TextButtonContentPadding,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    FilledTonalButton2(
        onClick,
        modifier,
        enabled,
        shape,
        colors,
        elevation,
        border,
        contentPadding,
        interactionSource
    ) {
        if (icon != null)
            Icon(painter = icon, contentDescription = null,   Modifier.padding(bottom = ButtonDefaults.IconSpacing))
        Text(text = label)
    }
}


@Composable
inline fun ElevatedButton(
    label: CharSequence,
    noinline onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: Painter? = null,
    enabled: Boolean = true,
    shape: Shape = ButtonDefaults.textShape,
    colors: ButtonColors = ButtonDefaults.textButtonColors(),
    elevation: ButtonElevation? = null,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = ButtonDefaults.TextButtonContentPadding,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    androidx.compose.material3.ElevatedButton(
        onClick,
        modifier,
        enabled,
        shape,
        colors,
        elevation,
        border,
        contentPadding,
        interactionSource
    ) {
        if (icon != null)
            Icon(painter = icon, contentDescription = null,   Modifier.padding(end = ButtonDefaults.IconSpacing))
        Label(text = label)
    }
}

@Composable
inline fun ElevatedButton2(
    label: CharSequence,
    noinline onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: Painter? = null,
    enabled: Boolean = true,
    shape: Shape = ButtonDefaults.textShape,
    colors: ButtonColors = ButtonDefaults.textButtonColors(),
    elevation: ButtonElevation? = null,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = ButtonDefaults.TextButtonContentPadding,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    ElevatedButton2(
        onClick,
        modifier,
        enabled,
        shape,
        colors,
        elevation,
        border,
        contentPadding,
        interactionSource
    ) {
        if (icon != null)
            Icon(painter = icon, contentDescription = null,   Modifier.padding(bottom = ButtonDefaults.IconSpacing))
        Label(text = label)
    }
}


internal inline fun composableOrNull(condition: Boolean, noinline content: @Composable () -> Unit) = when (condition) {
    true -> content
    else -> null
}


