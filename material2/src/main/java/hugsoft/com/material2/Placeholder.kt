package hugsoft.com.material2

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

private val PLACE_HOLDER_ICON_BOX_SIZE = 192.dp
private val PLACE_HOLDER_ICON_BOX_DEFAULT_SIZE = 56.dp

@Composable
private fun Vertical(
    modifier: Modifier = Modifier,
    icon: @Composable (() -> Unit)? = null,
    message: @Composable (() -> Unit)? = null,
    action: @Composable (() -> Unit)? = null,
    title: @Composable (() -> Unit),
) {
    Column(
        modifier = Modifier

            .fillMaxSize()
            .padding(horizontal = 32.dp, vertical = 16.dp)
            .then(modifier),

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (icon != null) {
            Box(
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .size(PLACE_HOLDER_ICON_BOX_SIZE),
                propagateMinConstraints = true
            ) {
                icon()
            }
        }

        ProvideTextStyle(
            style = MaterialTheme.typography.h4,
            content = title,
            textAlign = TextAlign.Center
        )

        if (message != null) {
            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            ProvideTextStyle(
                style = MaterialTheme.typography.body2,
                alpha = ContentAlpha.medium,
                content = message,
                textAlign = TextAlign.Center
            )
        }

        if (action != null) {
            Spacer(modifier = Modifier.padding(vertical = 32.dp))
            action()
        }
    }
}

@Composable
private fun Horizontal(
    modifier: Modifier = Modifier,
    icon: @Composable (() -> Unit)? = null,
    message: @Composable (() -> Unit)? = null,
    action: @Composable (() -> Unit)? = null,
    title: @Composable (() -> Unit),
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp, vertical = 16.dp)
            .then(modifier),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Spacer(modifier = Modifier.weight(0.15f))

        Column(
            modifier = Modifier
                .padding(end = 32.dp)
                .weight(0.7f, fill = false)
        ) {

            ProvideTextStyle(
                value = MaterialTheme.typography.h4,
                content = title
            )

            if (message != null) {
                Spacer(modifier = Modifier.padding(vertical = 8.dp))
                ProvideTextStyle(
                    style = MaterialTheme.typography.body2,
                    alpha = ContentAlpha.medium,
                    content = message
                )
            }

            //Action
            if (action != null) {
                Spacer(modifier = Modifier.padding(top = 32.dp))
                action()
            }
        }

        if (icon != null) {
            Box(
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .size(PLACE_HOLDER_ICON_BOX_SIZE),
                propagateMinConstraints = true
            ) {
                icon()
            }
        }

        Spacer(modifier = Modifier.weight(0.15f))
    }
}

@Composable
@NonRestartableComposable
fun Placeholder(
    title: @Composable (() -> Unit),
    modifier: Modifier = Modifier,
    vertical: Boolean = true,
    icon: @Composable (() -> Unit)? = null,
    message: @Composable (() -> Unit)? = null,
    action: @Composable (() -> Unit)? = null,
) {
    when (vertical) {
        true -> Vertical(modifier, icon, message, action, title)
        else -> Horizontal(modifier, icon, message, action, title)
    }
}