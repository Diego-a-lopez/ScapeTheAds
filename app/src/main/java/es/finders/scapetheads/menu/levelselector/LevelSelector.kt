package es.finders.scapetheads.menu.levelselector

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import es.finders.scapetheads.R
import es.finders.scapetheads.menu.Defeat.Defeat
import es.finders.scapetheads.menu.Victory.Victory
import es.finders.scapetheads.services.UnityBridge
import es.finders.scapetheads.ui.theme.ScapeTheAddsTheme
import es.finders.scapetheads.ui.utils.BasicBackground
import es.finders.scapetheads.ui.utils.ButtonItem
import es.finders.scapetheads.ui.utils.OutlineTextSection
import org.json.JSONObject
import kotlin.random.Random

class LevelSelector : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScapeTheAddsTheme {
                LevelSelectorScreen()
            }
        }
    }

    private lateinit var mService: UnityBridge
    private var mBound: Boolean = false

    private val connection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as UnityBridge.LocalBinder
            mService = binder.getService()
            mBound = true
            mService.setMode(JSONObject().apply {
                put("gamemode", "level")
            })
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            mBound = false
        }
    }

    override fun onStart() {
        super.onStart()
        // Bind to LocalService.
        Intent(this, UnityBridge::class.java).also { intent ->
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        super.onStop()
        unbindService(connection)
        mBound = false
    }
}

@Composable
fun LevelSelectorScreen(modifier: Modifier = Modifier) {
    Box(
        modifier,
        contentAlignment = Alignment.Center,
    ) {
        BasicBackground(modifier.fillMaxSize())
        LevelSelectorLayout(modifier.fillMaxSize())
    }
}

@Preview(showBackground = false, showSystemUi = true)
@Composable
private fun LevelSelectorScreenPreview() {
    ScapeTheAddsTheme {
        LevelSelectorScreen()
    }
}

@Composable
fun LevelSelectorLayout(modifier: Modifier = Modifier) {

    val buttonModifier = Modifier
        .width(70.dp)
        .padding(4.dp)
        .height(70.dp)
    val ctx = LocalContext.current

    Column(
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlineTextSection(stringResource(R.string.level_selection))
        Row {
            ButtonItem(
                "1",
                {
                    Toast.makeText(ctx, "Level 1 selected", Toast.LENGTH_LONG).show()
                    goRandomlyToVictoryOrDefeat(ctx)
                },
                buttonModifier
            )
            ButtonItem(
                "2",
                {
                    Toast.makeText(ctx, "Level 2 selected", Toast.LENGTH_LONG).show()
                    goRandomlyToVictoryOrDefeat(ctx)
                },
                buttonModifier
            )
            ButtonItem(
                "3",
                {
                    Toast.makeText(ctx, "Level 3 selected", Toast.LENGTH_LONG).show()
                    goRandomlyToVictoryOrDefeat(ctx)
                },
                buttonModifier
            )
            ButtonItem(
                "4",
                {
                    Toast.makeText(ctx, "Level 4 selected", Toast.LENGTH_LONG).show()
                    goRandomlyToVictoryOrDefeat(ctx)
                },
                buttonModifier
            )
        }
        Row {
            ButtonItem(
                "5",
                {
                    Toast.makeText(ctx, "Level 5 selected", Toast.LENGTH_LONG).show()
                    goRandomlyToVictoryOrDefeat(ctx)
                },
                buttonModifier
            )
            ButtonItem(
                "6",
                {
                    Toast.makeText(ctx, "Level 6 selected", Toast.LENGTH_LONG).show()
                    goRandomlyToVictoryOrDefeat(ctx)
                },
                buttonModifier
            )
            ButtonItem(
                "7",
                {
                    Toast.makeText(ctx, "Level 7 selected", Toast.LENGTH_LONG).show()
                    goRandomlyToVictoryOrDefeat(ctx)
                },
                buttonModifier
            )
            ButtonItem(
                "8",
                {
                    Toast.makeText(ctx, "Level 8 selected", Toast.LENGTH_LONG).show()
                    goRandomlyToVictoryOrDefeat(ctx)
                },
                buttonModifier
            )
        }
        Row {
            ButtonItem(
                "9",
                {
                    Toast.makeText(ctx, "Level 9 selected", Toast.LENGTH_LONG).show()
                    goRandomlyToVictoryOrDefeat(ctx)
                },
                buttonModifier
            )
            ButtonItem(
                "10",
                {
                    Toast.makeText(ctx, "Level 10 selected", Toast.LENGTH_LONG).show()
                    goRandomlyToVictoryOrDefeat(ctx)
                },
                buttonModifier
            )
            ButtonItem(
                "11",
                {
                    Toast.makeText(ctx, "Level 11 selected", Toast.LENGTH_LONG).show()
                    goRandomlyToVictoryOrDefeat(ctx)
                },
                buttonModifier
            )
            ButtonItem(
                "12",
                {
                    Toast.makeText(ctx, "Level 12 selected", Toast.LENGTH_LONG).show()
                    goRandomlyToVictoryOrDefeat(ctx)
                },
                buttonModifier
            )
        }

        Row {
            ButtonItem(
                "13",
                {
                    Toast.makeText(ctx, "Level 13 selected", Toast.LENGTH_LONG).show()
                    goRandomlyToVictoryOrDefeat(ctx)
                },
                buttonModifier
            )
            ButtonItem(
                "14",
                {
                    Toast.makeText(ctx, "Level 14 selected", Toast.LENGTH_LONG).show()
                    goRandomlyToVictoryOrDefeat(ctx)
                },
                buttonModifier
            )
            ButtonItem(
                "15",
                {
                    Toast.makeText(ctx, "Level 15 selected", Toast.LENGTH_LONG).show()
                    goRandomlyToVictoryOrDefeat(ctx)
                },
                buttonModifier
            )
            ButtonItem(
                "16",
                {
                    Toast.makeText(ctx, "Level 16 selected", Toast.LENGTH_LONG).show()
                    goRandomlyToVictoryOrDefeat(ctx)
                },
                buttonModifier
            )
        }

        Row {
            ButtonItem(
                "17",
                {
                    Toast.makeText(ctx, "Level 17 selected", Toast.LENGTH_LONG).show()
                    goRandomlyToVictoryOrDefeat(ctx)
                },
                buttonModifier
            )
            ButtonItem(
                "18",
                {
                    Toast.makeText(ctx, "Level 18 selected", Toast.LENGTH_LONG).show()
                    goRandomlyToVictoryOrDefeat(ctx)
                },
                buttonModifier
            )
            ButtonItem(
                "19",
                {
                    Toast.makeText(ctx, "Level 19 selected", Toast.LENGTH_LONG).show()
                    goRandomlyToVictoryOrDefeat(ctx)
                },
                buttonModifier
            )
            ButtonItem(
                "20",
                {
                    Toast.makeText(ctx, "Level 20 selected", Toast.LENGTH_LONG).show()
                    goRandomlyToVictoryOrDefeat(ctx)
                },
                buttonModifier
            )
        }

        Row {
            ButtonItem(
                "21",
                {
                    Toast.makeText(ctx, "Level 21 selected", Toast.LENGTH_LONG).show()
                    goRandomlyToVictoryOrDefeat(ctx)
                },
                buttonModifier
            )
            ButtonItem(
                "22",
                {
                    Toast.makeText(ctx, "Level 22 selected", Toast.LENGTH_LONG).show()
                    goRandomlyToVictoryOrDefeat(ctx)
                },
                buttonModifier
            )
            ButtonItem(
                "23",
                {
                    Toast.makeText(ctx, "Level 23 selected", Toast.LENGTH_LONG).show()
                    goRandomlyToVictoryOrDefeat(ctx)
                },
                buttonModifier
            )
            ButtonItem(
                "24",
                {
                    Toast.makeText(ctx, "Level 24 selected", Toast.LENGTH_LONG).show()
                    goRandomlyToVictoryOrDefeat(ctx)
                },
                buttonModifier
            )
        }
    }
}

fun goRandomlyToVictoryOrDefeat(ctx: Context) {
    if (Random.nextBoolean()) {
        ContextCompat.startActivity(ctx, Intent(ctx, Defeat::class.java), null)
    } else {
        ContextCompat.startActivity(ctx, Intent(ctx, Victory::class.java), null)
    }
}