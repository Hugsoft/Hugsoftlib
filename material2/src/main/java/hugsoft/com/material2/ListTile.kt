package hugsoft.com.material2

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideTextStyle
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp



@Composable
@Deprecated("Use the new ListTile.")
inline fun ListTile(
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    enabled: Boolean = true,
    centreVertically: Boolean = false,
    noinline leading: @Composable (() -> Unit)? = null,
    noinline secondaryText: @Composable (() -> Unit)? = null,
    noinline overlineText: @Composable (() -> Unit)? = null,
    noinline trailing: @Composable (() -> Unit)? = null,
    noinline bottom: @Composable (() -> Unit)? = null,
    noinline text: @Composable () -> Unit
) {
    ListTile(
        headline = text,
        modifier = modifier,
        enabled = enabled,
        leading = leading,
        subtitle = secondaryText,
        overline = overlineText,
        trailing = trailing,
        footer = bottom,
        centerAlign = centreVertically,
        color = if (selected) LocalContentColor.current.copy(0.2f) else Color.Transparent
    )
}

private val LIST_TILE_START_PADDING = 16.dp
private val LIST_TILE_VERTICAL_PADDING = 8.dp
private val LIST_TILE_END_PADDING = 16.dp
private val LIST_ITEM_THREE_LINE_VERTICAL_PADDING = 12.dp

private val LEADING_CONTENT_END_PADDING = 16.dp

private val TRAILING_CONTENT_START_PADDING = 16.dp

private val LIST_ITEM_ONE_LINE_CONTAINER_HEIGHT = 56.0.dp
private val LIST_ITEM_TWO_LINE_CONTAINER_HEIGHT = 72.0.dp
private val LIST_ITEM_THREE_LINE_CONTAINER_HEIGHT = 88.0.dp


@Composable
fun ListTile(
    headline: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colors.surface,
    onColor: Color = contentColorFor(backgroundColor = color),
    enabled: Boolean = true,
    shape: Shape = RectangleShape,
    subtitle: (@Composable () -> Unit)? = null,
    overline: (@Composable () -> Unit)? = null,
    leading: (@Composable () -> Unit)? = null,
    trailing: (@Composable () -> Unit)? = null,
    footer: (@Composable () -> Unit)? = null,
    centerAlign: Boolean = false
) {
    val content = @Composable {
        val typography = MaterialTheme.typography
        CompositionLocalProvider(
            LocalContentColor provides onColor,
            LocalContentAlpha provides if (enabled) ContentAlpha.high else ContentAlpha.disabled,
        ) {
            ProvideTextStyle(typography.subtitle1) {
                headline() // 0
            }
            leading?.invoke() // 1
            trailing?.invoke() // 2
            ProvideTextStyle(typography.overline) {
                overline?.invoke() // 3
            }
            CompositionLocalProvider(
                LocalContentAlpha provides if (enabled) ContentAlpha.medium else ContentAlpha.disabled,
                content = {
                    ProvideTextStyle(value = typography.body2) {
                        subtitle?.invoke() // 4
                    }
                }
            )
            footer?.invoke()// 5
        }
    }
    val lines = when {
        subtitle == null && overline == null -> 1
        subtitle == null || overline == null -> 2
        else -> 3
    }
    val isThreeLine = lines == 3
    val minHeight: Dp = when (lines) {
        1 -> LIST_ITEM_ONE_LINE_CONTAINER_HEIGHT
        2 -> LIST_ITEM_TWO_LINE_CONTAINER_HEIGHT
        else -> LIST_ITEM_THREE_LINE_CONTAINER_HEIGHT // 3
    }
    val vPadding =
        if (isThreeLine) LIST_ITEM_THREE_LINE_VERTICAL_PADDING else LIST_TILE_VERTICAL_PADDING
    val outerPaddingValues =
        PaddingValues(LIST_TILE_START_PADDING, vPadding, LIST_TILE_END_PADDING, vPadding)
    Layout(
        content = content,
        modifier = modifier
            .background(color, shape)
            .fillMaxWidth()
            .heightIn(minHeight)
            .padding(outerPaddingValues)
    ) { measurables, constraints ->
        val width = constraints.maxWidth
        val leadingPaddingPx = LEADING_CONTENT_END_PADDING.roundToPx()
        val trailingPaddingPx = TRAILING_CONTENT_START_PADDING.roundToPx()
        // Leading Placeable.
        // The leading placeable has same index since headline is never null.
        // Leading placeable will be placed at the start of the screen.
        var index = 0
        // Measure it as desired size.
        // The width that is consumed till now
        val unrestricted = constraints.copy(minWidth = 0, minHeight = 0)
        val leadingPlaceable =
            if (leading != null) measurables[++index].measure(unrestricted) else null
        val trailingPlaceable =
            if (trailing != null) measurables[++index].measure(unrestricted) else null
        // The space remained for text part of this composable.
        var remaining = width -
                (if (leadingPlaceable == null) 0 else leadingPlaceable.width + leadingPaddingPx) -
                (if (trailingPlaceable == null) 0 else trailingPlaceable.width + trailingPaddingPx)
        // fill-space between leading and trailing placeable.
        val textConstraints = constraints.copy(
            minWidth = 0, maxWidth = remaining.coerceIn(0, width), 0
        )
        val overlinePlaceable =
            if (overline != null) measurables[++index].measure(textConstraints) else null
        val subtitlePlaceable =
            if (subtitle != null) measurables[++index].measure(textConstraints) else null
        // measure bottom unrestricted.
        val bottomPlaceable = if (footer != null)
            measurables[++index].measure(unrestricted)
        else
            null
        // must be non null
        val headingPlaceable = measurables[0].measure(textConstraints)
        // Height must be equal to the height of the children.
        val height = maxOf(
            (leadingPlaceable?.height ?: 0),
            (overlinePlaceable?.height ?: 0) + headingPlaceable.height + (subtitlePlaceable?.height
                ?: 0),
            (trailingPlaceable?.height ?: 0),
            constraints.minHeight
        )
        val topAlign =
            if (centerAlign) false else height > LIST_ITEM_ONE_LINE_CONTAINER_HEIGHT.toPx() * 1.2f
        // place each item as per restrictions.
        layout(width, height + (bottomPlaceable?.height ?: 0)) {
            var x: Int = 0
            var y: Int = if (topAlign) 0 else height / 2 - (leadingPlaceable?.height ?: 0) / 2
            leadingPlaceable?.placeRelative(x, y)
            y = if (topAlign) 0 else height / 2 - (trailingPlaceable?.height ?: 0) / 2
            x = (width - (trailingPlaceable?.width ?: 0)).coerceIn(0, width)
            trailingPlaceable?.placeRelative(x, y)
            // overline
            x = if (leadingPlaceable == null) 0 else (leadingPlaceable.width + leadingPaddingPx)
            y = if (topAlign) 0 else (height / 2 - ((overlinePlaceable?.height
                ?: 0) + headingPlaceable.height + (subtitlePlaceable?.height ?: 0)) / 2)
            overlinePlaceable?.placeRelative(x, y)
            y += overlinePlaceable?.height ?: 0
            headingPlaceable.placeRelative(x, y)
            y += headingPlaceable.height
            subtitlePlaceable?.placeRelative(x, y)
            x = 0
            y = height
            bottomPlaceable?.placeRelative(x, y)
        }
    }
}



