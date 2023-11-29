package hugsoft.com.material2.neumorphic

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import hugsoft.com.core.shadow.SpotLight


@Stable
interface NeumorphicButtonColors {

    @Composable
    fun backgroundColor(enabled: Boolean): State<Color>


    @Composable
    fun contentColor(enabled: Boolean): State<Color>


    fun shadow(elevation: Dp, adjacent: Boolean): Color
}



@Immutable
private class DefaultNeumorphicButtonColors(
    private val backgroundColor: Color,
    private val contentColor: Color,
    private val disabledBackgroundColor: Color,
    private val disabledContentColor: Color,
    private val lightShadowColor: Color,
    private val darkShadowColor: Color
) : NeumorphicButtonColors {
    @Composable
    override fun backgroundColor(enabled: Boolean): State<Color> {
        return rememberUpdatedState(if (enabled) backgroundColor else disabledBackgroundColor)
    }

    @Composable
    override fun contentColor(enabled: Boolean): State<Color> {
        return rememberUpdatedState(if (enabled) contentColor else disabledContentColor)
    }

    override fun shadow(elevation: Dp, adjacent: Boolean): Color {
        return when {
            elevation > 0.dp && adjacent -> lightShadowColor
            // opposite
            elevation > 0.dp && !adjacent -> darkShadowColor
            elevation < 0.dp && adjacent -> darkShadowColor

            // case elevation < 0.dp && !adjacent
            else -> lightShadowColor
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as DefaultNeumorphicButtonColors

        if (backgroundColor != other.backgroundColor) return false
        if (contentColor != other.contentColor) return false
        if (disabledBackgroundColor != other.disabledBackgroundColor) return false
        if (disabledContentColor != other.disabledContentColor) return false
        if (lightShadowColor != other.lightShadowColor) return false
        if (darkShadowColor != other.darkShadowColor) return false

        return true
    }

    override fun hashCode(): Int {
        var result = backgroundColor.hashCode()
        result = 31 * result + contentColor.hashCode()
        result = 31 * result + disabledBackgroundColor.hashCode()
        result = 31 * result + disabledContentColor.hashCode()
        result = 31 * result + lightShadowColor.hashCode()
        result = 31 * result + darkShadowColor.hashCode()
        return result
    }
}

object NeumorphicButtonDefaults {

    private val ButtonHorizontalPadding = 16.dp
    private val ButtonVerticalPadding = 8.dp


    val ContentPadding = PaddingValues(
        start = ButtonHorizontalPadding,
        top = ButtonVerticalPadding,
        end = ButtonHorizontalPadding,
        bottom = ButtonVerticalPadding
    )


    val MinWidth = 64.dp


    val MinHeight = 36.dp


    @Suppress("UNUSED_PARAMETER")
    @Composable
    fun elevation(
        defaultElevation: Dp = 6.dp,
        pressedElevation: Dp = 0.dp,
        disabledElevation: Dp = 0.dp,
        hoveredElevation: Dp = 7.dp,
        focusedElevation: Dp = 7.dp,
    ) =
        ButtonDefaults.elevation(
            defaultElevation = defaultElevation,
            pressedElevation = pressedElevation,
            disabledElevation = disabledElevation,
            hoveredElevation = hoveredElevation,
            focusedElevation = focusedElevation
        )


    @Composable
    fun neumorphicButtonColors(
        backgroundColor: Color = MaterialTheme.colors.background,
        contentColor: Color = contentColorFor(backgroundColor),
        disabledBackgroundColor: Color = MaterialTheme.colors.onBackground.copy(alpha = 0.12f)
            .compositeOver(MaterialTheme.colors.background),
        disabledContentColor: Color = MaterialTheme.colors.onBackground
            .copy(alpha = ContentAlpha.disabled),
        lightShadowColor: Color = DefaultLightShadowColor,
        darkShadowColor: Color = DefaultDarkShadowColor,
    ): NeumorphicButtonColors =
        DefaultNeumorphicButtonColors(
            backgroundColor = backgroundColor,
            contentColor = contentColor,
            disabledBackgroundColor = disabledBackgroundColor,
            disabledContentColor = disabledContentColor,
            lightShadowColor = lightShadowColor,
            darkShadowColor = darkShadowColor
        )
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NeumorphicButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: CornerBasedShape = MaterialTheme.shapes.small,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    elevation: ButtonElevation = NeumorphicButtonDefaults.elevation(),
    border: BorderStroke? = null,
    colors: NeumorphicButtonColors = NeumorphicButtonDefaults.neumorphicButtonColors(),
    contentPadding: PaddingValues = NeumorphicButtonDefaults.ContentPadding,
    spotLight: SpotLight = SpotLight.TOP_LEFT,
    content: @Composable RowScope.() -> Unit,
) {
    val contentColor by colors.contentColor(enabled)
    val depth by elevation.elevation(enabled = enabled, interactionSource = interactionSource)
    Neumorphic(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        color = colors.backgroundColor(enabled).value,
        contentColor = contentColor.copy(alpha = 1f),
        border = border,
        elevation = depth,
        interactionSource = interactionSource,
        lightShadowColor = colors.shadow(elevation = depth, true),
        darkShadowColor = colors.shadow(elevation = depth, false),
        spotLight = spotLight
    ) {
        CompositionLocalProvider(LocalContentAlpha provides contentColor.alpha) {
            ProvideTextStyle(
                value = MaterialTheme.typography.button
            ) {
                Row(
                    Modifier
                        .defaultMinSize(
                            minWidth = NeumorphicButtonDefaults.MinWidth,
                            minHeight = NeumorphicButtonDefaults.MinHeight
                        )
                        .padding(contentPadding),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    content = content
                )
            }
        }
    }
}