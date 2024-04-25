package es.finders.scapetheads.menu.level

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat
import com.unity3d.player.UnityPlayerActivity

class Level : ComponentActivity() {
    // TODO: Add more games in Unity using sensors (min 3-4)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ContextCompat.startActivity(
            this,
            Intent(this, UnityPlayerActivity::class.java),
            null
        )
    }
}