package hugsoft.com.material3


import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.TextButton
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import hugsoft.com.core.composableOrNull
import hugsoft.com.core.rememberState

object PreferenceDefaults {
    val IconSpaceReserved = 12.dp
}
private val DefaultPreferenceShape = RectangleShape


@Composable
fun Preference(
    title: CharSequence,
    modifier: Modifier = Modifier,
    singleLineTitle: Boolean = true,
    iconSpaceReserved: Boolean = true,
    icon: ImageVector? = null,
    summery: CharSequence? = null,
    enabled: Boolean = true,
    shape: Shape = DefaultPreferenceShape,
    color: Color = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp),
    widget: @Composable (() -> Unit)? = null,
    revealable: (@Composable () -> Unit)? = null,
    forceVisible: Boolean = false,
) {
    var focusable by remember { mutableStateOf(forceVisible) }
    val manager = LocalFocusManager.current

    val listModifier = when (forceVisible || !enabled || revealable == null) {
        true -> modifier
        else -> {
            val requester = remember(::FocusRequester)
            Modifier
                .focusRequester(requester)
                .onFocusChanged { focusable = it.hasFocus }
                .focusTarget()
                // .pointerInput(Unit) { detectTapGestures {  } }
                .onPreviewKeyEvent {

                    if (it.key != Key.Back)
                        return@onPreviewKeyEvent false
                    manager.clearFocus()
                    true
                }
                .clickable() { requester.requestFocus() }
                .then(modifier)
                .animateContentSize()
        }
    }
    ListTile(
        modifier = listModifier,
        color = color,
        trailing = widget,
        enabled = enabled,
        headline = {
            Text(
                text = title,
                maxLines = if (singleLineTitle) 1 else 2,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.SemiBold
            )
        },
        subtitle = composableOrNull(!summery.isNullOrBlank()) {
            Text(text = summery ?: "", maxLines = 4, overflow = TextOverflow.Ellipsis)
        },
        leading = composableOrNull(icon != null || iconSpaceReserved) {
            when {
                // non-null write the icon.
                icon != null -> Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.padding(top = 4.dp)
                )
                // preserve the space in case user requested.
                iconSpaceReserved -> Spacer(Modifier.padding(PreferenceDefaults.IconSpaceReserved))
            }
        },
        // show footer in case available.
        footer = composableOrNull(revealable != null) {
            Crossfade(targetState = focusable, label = "PREF") { value ->
                if (value) revealable?.invoke()
            }
        },
        shape = shape,
    )
}

@Composable
fun SwitchPreference(
    title: CharSequence,
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit),
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    singleLineTitle: Boolean = true,
    iconSpaceReserved: Boolean = true,
    shape: Shape = DefaultPreferenceShape,
    icon: ImageVector? = null,
    summery: CharSequence? = null,
) {
    Preference(
        modifier = modifier.clickable(enabled = enabled) {
            onCheckedChange(!checked)
        },
        title = title,
        enabled = enabled,
        singleLineTitle = singleLineTitle,
        iconSpaceReserved = iconSpaceReserved,
        icon = icon,
        shape = shape,
        summery = summery,
        widget = {
            Switch(enabled = enabled, checked = checked, onCheckedChange = null)
        },
    )
}

@Composable
fun <T> DropDownPreference(
    title: CharSequence,
    defaultValue: T,
    onRequestChange: (T) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    singleLineTitle: Boolean = true,
    iconSpaceReserved: Boolean = true,
    shape: Shape = DefaultPreferenceShape,
    color: Color = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp),
    onColor: Color = MaterialTheme.colorScheme.onSurface,
    icon: ImageVector? = null,
    entries: List<Pair<String, T>>,
) {
    // ensure that entries are non-emoty.
    require(entries.isNotEmpty())

    // control the expanded state of the dropDownMenu.
    var expanded by rememberState(initial = false)
    // calculate eh default value of these entries
    val default = remember(defaultValue) {
        entries.find { (_, value) -> value == defaultValue }!!.first
    }
    Preference(
        modifier = modifier.clickable(enabled = enabled) {
            expanded = true
        },
        title = title,
        enabled = enabled,
        singleLineTitle = singleLineTitle,
        iconSpaceReserved = iconSpaceReserved,
        icon = icon,
        summery = default,
        shape = shape,
        color = color,
        widget = {
            Box {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                )

                // compose the dropdown
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    // create corresponding composable for
                    // each entry.
                    content = {
                        entries.forEach { (placeHolder, value) ->
                            // handle click on Item.
                            val onEntryClick = {
                                if (value != defaultValue) {
                                    onRequestChange(value)
                                    expanded = false
                                }
                            }
                            // compose item
                            val checked = value == defaultValue
                            val color =
                                if (checked) MaterialTheme.colorScheme.secondary else onColor
                            DropdownMenuItem(
                                onClick = onEntryClick,
                                text = {
                                    Text(
                                        text = placeHolder,
                                        fontWeight = if (value != defaultValue) FontWeight.SemiBold else FontWeight.Bold,
                                        color = color
                                    )
                                },
                                leadingIcon = {
                                    Icon(
                                        contentDescription = null,
                                        tint = color,
                                        painter = painterResource(
                                            if (checked)
                                                R.drawable.radio_button_checked
                                            else
                                                R.drawable.radio_button_unchecked_24
                                        )
                                    )
                                }
                            )
                        }
                    },
                )
            }
        }
    )
}

@Composable
private fun TextButtons(
    modifier: Modifier = Modifier,
    onConfirmClick: () -> Unit,
    onCancelClick: () -> Unit
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextButton(onClick = onCancelClick) {
            Label(text = stringResource(id = android.R.string.cancel))
        }
        TextButton(onClick = onConfirmClick) {
            Label(text = stringResource(id = android.R.string.ok))
        }
    }
}

@Composable
fun SliderPreference(
    title: CharSequence,
    defaultValue: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    steps: Int = 0,
    enabled: Boolean = true,
    singleLineTitle: Boolean = true,
    iconSpaceReserved: Boolean = true,
    icon: ImageVector? = null,
    shape: Shape = DefaultPreferenceShape,
    summery: CharSequence? = null,
    forceVisible: Boolean = false,
    iconChange: ImageVector? = null,
    preview: (@Composable () -> Unit)? = null,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
) {

    val revealable =
        @Composable {
            val startPadding = (if (iconSpaceReserved) 24.dp + 16.dp else 0.dp) + 8.dp
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = startPadding)
            ) {
                // place slider
                var value by rememberState(initial = defaultValue)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    if (iconChange != null)
                        Icon(
                            imageVector = iconChange,
                            contentDescription = null,
                        )
                    Slider(
                        value = value,
                        onValueChange = {
                            value = it
                        },
                        valueRange = valueRange,
                        steps = steps,
                        modifier = Modifier.weight(1f)
                    )
                    if (iconChange != null) {
                        Icon(
                            imageVector = iconChange,
                            contentDescription = null,
                            modifier = Modifier.scale(1.5f)
                        )
                    }
                }

                val manager = LocalFocusManager.current
                val onCancelClick = {
                    if (!forceVisible)
                        manager.clearFocus(true)
                }
                val onConfirmClick = {
                    if (!forceVisible)
                        manager.clearFocus(true)
                    onValueChange(value)
                }
                TextButtons(
                    onCancelClick = onCancelClick,
                    onConfirmClick = onConfirmClick,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

    Preference(
        modifier = modifier,
        title = title,
        enabled = enabled,
        singleLineTitle = singleLineTitle,
        iconSpaceReserved = iconSpaceReserved,
        icon = icon,
        shape = shape,
        forceVisible = forceVisible,
        summery = summery,
        widget = preview,
        revealable = revealable
    )
}

private val TextFieldShape = RoundedCornerShape(10)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TextFieldPreference(
    title: CharSequence,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    singleLineTitle: Boolean = true,
    iconSpaceReserved: Boolean = true,
    icon: ImageVector? = null,
    summery: CharSequence? = null,
    forceVisible: Boolean = false,
    maxLines: Int = 1,
    shape: Shape = DefaultPreferenceShape,
    leadingFieldIcon: ImageVector? = null,
    label: CharSequence? = null,
    placeholder: CharSequence? = null,
    preview: (@Composable () -> Unit)? = null,
) {
    val manager = LocalFocusManager.current
    val revealable = @Composable {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .padding(start = (if (iconSpaceReserved) 24.dp + 8.dp else 8.dp) + 8.dp, end = 8.dp)
                .fillMaxWidth(),
            maxLines = maxLines,
            label = composableOrNull(label != null) {
                Label(text = label ?: "")
            },
            placeholder = composableOrNull(placeholder != null) {
                Text(text = placeholder ?: "")
            },
            shape = TextFieldShape,
            singleLine = maxLines == 1,
            leadingIcon = composableOrNull(leadingFieldIcon != null) {
                Icon(
                    imageVector = leadingFieldIcon ?: Icons.Default.Create,
                    contentDescription = null
                )
            },
            trailingIcon = {
                IconButton(
                    icon = Icons.Default.Close,
                    onClick = {
                        if (value.text.isEmpty())
                            if (!forceVisible) manager.clearFocus(true)
                            else onValueChange(TextFieldValue(""))
                    },
                    contentDescription = null
                )
            },
            keyboardActions = KeyboardActions(
                onDone = { if (!forceVisible) manager.clearFocus(true) }
            ),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)
        )
    }
    // Actual Host
    Preference(
        modifier = modifier,
        title = title,
        enabled = enabled,
        singleLineTitle = singleLineTitle,
        iconSpaceReserved = iconSpaceReserved,
        icon = icon,
        shape = shape,
        forceVisible = forceVisible,
        summery = summery,
        widget = preview,
        revealable = revealable
    )
}




