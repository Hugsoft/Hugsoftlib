package hugsoft.com.Hugsoftlib

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import hugsoft.com.core.rememberState
import hugsoft.com.hugsoftlib.theme.HugsoftlibTheme
import hugsoft.com.material2.Preference
import hugsoft.com.material2.SwitchPreference
import hugsoft.com.preferences.Preferences

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val x = Preferences(this)
        setContent {
            HugsoftlibTheme { // A surface container using the 'background' color from the theme
                PLaceholder()
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}


@Preview
@Composable
fun PLaceholder() {
    Scaffold {
       Column(modifier = Modifier.padding(it)) {
           Preference(
               title = "App Version",
               summery = buildAnnotatedString {
                   append("2.2.5-debug")
                   append("\nHave feedback we would like to here, but please dont share sensitive information.\nTap to open feedback dialog.")
               },
               icon = Icons.Outlined.Info,

               )

           var checked by rememberState(initial = false)
           SwitchPreference(
               title = "Color Status Bar",
               summery = "Force Color Status Bar.",
               checked = checked,
               onCheckedChange = { checked =!checked},
               icon = Icons.Outlined.Settings
           )
       }
    }
}