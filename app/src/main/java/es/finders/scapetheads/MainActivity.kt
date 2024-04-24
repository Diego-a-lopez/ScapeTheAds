package es.finders.scapetheads

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.google.android.gms.auth.api.identity.Identity
import es.finders.scapetheads.menu.Defeat.DefeatScreen
import es.finders.scapetheads.menu.Victory.VictoryScreen
import es.finders.scapetheads.menu.home.HomeScreen
import es.finders.scapetheads.menu.leaderboard.LeaderboardScreen
import es.finders.scapetheads.menu.levelselector.LevelSelectorScreen
import es.finders.scapetheads.menu.login.SignInScreen
import es.finders.scapetheads.menu.nickname.NicknameScreen
import es.finders.scapetheads.menu.settings.SettingsScreen
import es.finders.scapetheads.services.AndroidRoom.LocalScoreDatabase
import es.finders.scapetheads.services.AndroidRoom.LocalScoreViewModel
import es.finders.scapetheads.services.auth.GoogleAuthClient
import es.finders.scapetheads.services.auth.SignInViewModel
import es.finders.scapetheads.services.firestore.FirestoreClient
import es.finders.scapetheads.services.unity.UnityBridge
import es.finders.scapetheads.ui.theme.ScapeTheAddsTheme
import es.finders.scapetheads.ui.utils.BasicBackground
import kotlinx.coroutines.launch
import org.json.JSONObject

class MainActivity : ComponentActivity() {


    private val Context.dataStore by preferencesDataStore(name = "settings")


    private val googleAuthUiClient by lazy {
        GoogleAuthClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    private val firestoreClient by lazy {
        FirestoreClient()
    }

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            LocalScoreDatabase::class.java,
            "localscores.db"
        ).build()
    }

    private val viewModel by viewModels<LocalScoreViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return LocalScoreViewModel(db.dao) as T
                }
            }
        }
    )

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

    // TODO: Fix typography, font looks bad
    override fun onCreate(savedInstanceState: Bundle?) {
        var scoreMode: String = getString(R.string.local_scores)
        super.onCreate(savedInstanceState)
        val intent = Intent(this, UnityBridge::class.java)
        startService(intent)
        setContent {
            ScapeTheAddsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    BasicBackground(Modifier.fillMaxSize())
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "sign_in") {
                        composable("sign_in") {
                            val viewModel = viewModel<SignInViewModel>()
                            val state by viewModel.state.collectAsStateWithLifecycle()
                            LaunchedEffect(key1 = Unit) {
                                if (googleAuthUiClient.getSignedInUser() != null) {
                                    // TODO: Fix return to nickname when user tries to quit
                                    // TODO: Check if used has nickname locally
                                    // If he does go to Home
                                    // Else
                                    navController.navigate("nickname")
                                }
                            }

                            val launcher = rememberLauncherForActivityResult(
                                contract = ActivityResultContracts.StartIntentSenderForResult(),
                                onResult = { result ->
                                    if (result.resultCode == RESULT_OK) {
                                        lifecycleScope.launch {
                                            val signInResult = googleAuthUiClient.signInWithIntent(
                                                intent = result.data ?: return@launch
                                            )
                                            viewModel.onSignInResult(signInResult)
                                        }
                                    }
                                }
                            )

                            LaunchedEffect(key1 = state.isSignInSuccessful) {
                                if (state.isSignInSuccessful) {
                                    Toast.makeText(
                                        applicationContext,
                                        "Sign in successful",
                                        Toast.LENGTH_LONG
                                    ).show()

                                    // TODO: Check if used has nickname locally
                                    // If he does go to Home
                                    // Else
                                    navController.navigate("nickname")
                                    viewModel.resetState()
                                }
                            }

                            SignInScreen(
                                state = state,
                                onSignInClick = {
                                    lifecycleScope.launch {
                                        val signInIntentSender = googleAuthUiClient.signIn()
                                        launcher.launch(
                                            IntentSenderRequest.Builder(
                                                signInIntentSender ?: return@launch
                                            ).build()
                                        )
                                    }
                                }
                            )
                        }

                        composable("nickname") {
                            //Log.d("LoginScreen", googleAuthUiClient.getSignedInUser().toString())
                            // firestoreClient.testUpload()

                            NicknameScreen(
                                onSignOut = {
                                    lifecycleScope.launch {
                                        googleAuthUiClient.signOut()
                                        Toast.makeText(
                                            applicationContext,
                                            getString(R.string.signed_out),
                                            Toast.LENGTH_LONG
                                        ).show()

                                        navController.popBackStack()
                                    }
                                },
                                onNext = {
                                    lifecycleScope.launch {
                                        if (firestoreClient.checkNicknameExists(it)) {
                                            Toast.makeText(
                                                applicationContext,
                                                "Nickname already chosen",
                                                Toast.LENGTH_LONG
                                            ).show()
                                        } else {
                                            navController.navigate("home")
                                            firestoreClient.addNickname(it)
                                        }
                                    }
                                }
                            )
                        }

                        composable("home") {
                            HomeScreen(
                                onExit = {
                                    finishAffinity()
                                },
                                onHighscore = {
                                    scoreMode = getString(R.string.local_scores)
                                    navController.navigate("leaderboard")
                                },
                                onLeaderboard = {
                                    scoreMode = getString(R.string.global_scores)
                                    navController.navigate("leaderboard")
                                },
                                onSelectLevel = {
                                    navController.navigate("level_selector")
                                },
                                onSettings = {
                                    navController.navigate("settings")
                                }
                            )
                        }

                        composable("level_selector") {
                            // TODO: Add launch Victory or Defeat depending on unityBridge result
                            LevelSelectorScreen(
                                onExit = {
                                    navController.popBackStack()
                                }
                            )
                        }

                        composable("leaderboard") {
                            val state by viewModel.state.collectAsState()
                            Log.d("TEST", scoreMode)
                            LeaderboardScreen(
                                onExit = {
                                    navController.popBackStack()
                                },
                                scoresType = scoreMode,
                                state = state,
                                firestoreClient = firestoreClient
                            )
                        }

                        composable("settings") {
                            SettingsScreen(
                                onExit = {
                                    navController.popBackStack()
                                },
                                onEnglish = {
                                    Toast.makeText(
                                        applicationContext,
                                        "English",
                                        Toast.LENGTH_LONG
                                    ).show()
                                },
                                onSpanish = {
                                    Toast.makeText(
                                        applicationContext,
                                        "Spanish",
                                        Toast.LENGTH_LONG
                                    ).show()
                                },
                                onVolume = {
                                    Toast.makeText(
                                        applicationContext,
                                        "Volume",
                                        Toast.LENGTH_LONG
                                    ).show()
                                },
                                onTheme = {
                                    Toast.makeText(
                                        applicationContext,
                                        "Theme",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            )
                        }

                        composable("defeat") {
                            DefeatScreen(
                                onExit = {
                                    navController.popBackStack()
                                }
                            )
                        }

                        composable("victory") {
                            VictoryScreen(
                                onExit = {
                                    navController.popBackStack()
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycleScope.launch {
            googleAuthUiClient.signOut()
            Toast.makeText(
                applicationContext,
                getString(R.string.signed_out),
                Toast.LENGTH_LONG
            ).show()
        }
    }

}