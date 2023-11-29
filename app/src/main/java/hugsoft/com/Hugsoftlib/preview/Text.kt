package hugsoft.com.hugsoftlib.preview

import android.widget.TextView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import hugsoft.com.hugsoftlib.R
import hugsoft.com.core.textResource
import hugsoft.com.material3.Text

@Composable
fun TextView(text: CharSequence, modifier: Modifier = Modifier) {
    AndroidView(
        factory = { TextView(it).apply { clipToOutline = true } },
        update = {it.text = text},
        modifier = modifier
    )
}


@Preview(widthDp = 360, showBackground = true)
@Composable
fun PreviewAndroidText() {
    val styled = LocalContext.current.getText(R.string.styled_string)
    TextView(text = styled)
}

@OptIn(ExperimentalTextApi::class)
@Preview(widthDp = 360, showBackground = true)
@Composable
fun PreviewComposeText(){
    val styled = textResource(id = R.string.styled_string)
    Text(text = styled)
}

