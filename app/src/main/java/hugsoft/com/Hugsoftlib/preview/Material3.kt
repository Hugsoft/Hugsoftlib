package hugsoft.com.hugsoftlib.preview

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import hugsoft.com.core.rememberState
import hugsoft.com.material3.Label
import hugsoft.com.material3.SliderPreference
import hugsoft.com.material3.TextFieldPreference

@Preview
@Composable
fun PreviewMaterial2Pref() {
    MaterialTheme {
        var value by rememberState(initial = TextFieldValue(""))
        Column {
            TextFieldPreference(
                title = "Preference",
                summery = "Tis is a test for observable preview preference.",
                value = value,
                onValueChange = { value = it },
                label = "Preview",
                placeholder = "This is a placeholder text",
                icon = Icons.Default.Build,
                preview = { Label(text = "${value.text}")},
                leadingFieldIcon = Icons.Outlined.FavoriteBorder,
            )
            var slider by rememberState(initial = 0.1f)
            SliderPreference(title = "title", summery = "saume zkhzkjd zfkjshf", defaultValue = slider, onValueChange = { slider = it })
        }
    }
}