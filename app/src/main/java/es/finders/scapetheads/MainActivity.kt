package es.finders.scapetheads

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.content.ContextCompat
import es.finders.scapetheads.menu.login.Login
import es.finders.scapetheads.ui.theme.ScapeTheAddsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScapeTheAddsTheme {
                //val intent = Intent(this, UnityBridge::class.java)
                //startService(intent)
                ContextCompat.startActivity(
                    this,
                    Intent(this, Login::class.java),
                    null
                )
            }
        }
    }
}