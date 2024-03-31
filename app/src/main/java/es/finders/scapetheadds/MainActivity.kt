package es.finders.scapetheadds

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.content.ContextCompat
import es.finders.scapetheadds.menu.login.Login
import es.finders.scapetheadds.services.UnityBridge
import es.finders.scapetheadds.ui.theme.ScapeTheAddsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScapeTheAddsTheme {
                val intent = Intent(this, UnityBridge::class.java)
                startService(intent)
                ContextCompat.startActivity(this, Intent(this, Login::class.java), null)
            }
        }
    }
}