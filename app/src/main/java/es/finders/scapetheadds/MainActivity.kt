package es.finders.scapetheadds

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.content.ContextCompat
import es.finders.scapetheadds.menu.login.Login
import es.finders.scapetheadds.services.UnityBridge
import es.finders.scapetheadds.ui.theme.ScapeTheAddsTheme
import org.json.JSONObject

class MainActivity : ComponentActivity() {

    lateinit var unityService: UnityBridge
    var isServiceBound = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val serviceIntent = Intent(this, UnityBridge::class.java)
        bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE)

        setContent {
            ScapeTheAddsTheme {
                ContextCompat.startActivity(this, Intent(this, Login::class.java), null)
            }
        }
    }

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(componentName: ComponentName, binder: IBinder) {
            val localBinder = binder as UnityBridge.LocalBinder
            unityService = localBinder.getService()
            isServiceBound = true
        }

        override fun onServiceDisconnected(componentName: ComponentName) {
            isServiceBound = false
        }
    }

    // Método para cambiar el modo de juego desde la actividad principal
    fun changeGameMode(newMode: String, level: Int? = null) {
        if (isServiceBound) {
            if (level === null) {
                unityService.setMode(JSONObject().apply {
                    put("gameMode", newMode)
                })
            } else {
                unityService.setMode(JSONObject().apply {
                    put("gameMode", newMode)
                    put("level", level)
                })
            }
        }
    }
}