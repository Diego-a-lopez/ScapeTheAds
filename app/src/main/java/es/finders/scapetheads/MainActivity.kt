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
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
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
import es.finders.scapetheads.menu.level.Level
import es.finders.scapetheads.menu.levelselector.LevelSelectorScreen
import es.finders.scapetheads.menu.login.SignInScreen
import es.finders.scapetheads.menu.nickname.NicknameScreen
import es.finders.scapetheads.menu.settings.SettingsScreen
import es.finders.scapetheads.services.AndroidRoom.LocalScoreDatabase
import es.finders.scapetheads.services.AndroidRoom.LocalScoreViewModel
import es.finders.scapetheads.services.AndroidRoom.user.UserNickname
import es.finders.scapetheads.services.AndroidRoom.user.UserNicknameDatabase
import es.finders.scapetheads.services.auth.GoogleAuthClient
import es.finders.scapetheads.services.auth.SignInViewModel
import es.finders.scapetheads.services.firestore.FirestoreClient
import es.finders.scapetheads.services.unity.UnityBridge
import es.finders.scapetheads.ui.theme.ScapeTheAddsTheme
import es.finders.scapetheads.ui.utils.BasicBackground
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.json.JSONObject

class MainActivity : ComponentActivity() {


    private val Context.dataStore by preferencesDataStore(name = "settings")

    private object PreferencesKeys {
        val LANGUAGE_KEY = stringPreferencesKey("language")
        val VOLUME_KEY = intPreferencesKey("volume")
        val THEME_KEY = booleanPreferencesKey("theme")
    }


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
            "localScores.db"
        ).build()
    }

    private var currentUserNickname: String = ""
    private val userDB by lazy {
        Room.databaseBuilder(
            applicationContext,
            UserNicknameDatabase::class.java,
            "userNickname.db"
        ).allowMainThreadQueries().build()
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

    private lateinit var unityBridge: UnityBridge
    private var mBound: Boolean = false

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as UnityBridge.LocalBinder
            unityBridge = binder.getService()
            mBound = true
            unityBridge.setMode(JSONObject().apply {
                put("gamemode", "level")
            })
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            mBound = false
        }
    }

    suspend fun initiateDataStore() {
        applicationContext.dataStore.edit { settings ->
            settings[PreferencesKeys.LANGUAGE_KEY] = "English"
            settings[PreferencesKeys.VOLUME_KEY] = 50
            settings[PreferencesKeys.THEME_KEY] = false
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

    override fun onCreate(savedInstanceState: Bundle?) {
        var scoreMode: String = getString(R.string.local_scores)

        super.onCreate(savedInstanceState)
        val intent = Intent(this, UnityBridge::class.java)
        startService(intent)

        //TODO: better way to do this? (runBlocking)
        //maybe this is enough tho, since it is a light operation
        runBlocking {
            initiateDataStore()
        }
        setContent {
            ScapeTheAddsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    BasicBackground(Modifier.fillMaxSize())
                    val navController = rememberNavController()
                    val ctx = LocalContext.current
                    NavHost(navController = navController, startDestination = "sign_in") {
                        composable("sign_in") {
                            val viewModel = viewModel<SignInViewModel>()
                            val state by viewModel.state.collectAsStateWithLifecycle()
                            LaunchedEffect(key1 = Unit) {
                                if (googleAuthUiClient.getSignedInUser() != null) {
                                    val userId = googleAuthUiClient.getSignedInUser()!!.userId
                                    if (userDB.dao.userExists(userId)) {
                                        currentUserNickname =
                                            userDB.dao.getByUserId(userId).nickname
                                        navController.navigate("home")
                                    } else {
                                        navController.navigate("nickname")
                                    }
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

                                    val userId = googleAuthUiClient.getSignedInUser()!!.userId
                                    if (userDB.dao.userExists(userId)) {
                                        currentUserNickname =
                                            userDB.dao.getByUserId(userId).nickname
                                        navController.navigate("home")
                                    } else {
                                        navController.navigate("nickname")
                                    }
                                }
                            }

                            SignInScreen(
                                state = state,
                                onExit = {
                                    finishAffinity()
                                },
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
                            NicknameScreen(
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
                                            userDB.dao.upsert(
                                                UserNickname(
                                                    userId = googleAuthUiClient.getSignedInUser()!!.userId,
                                                    nickname = it
                                                )
                                            )
                                            currentUserNickname = it
                                            firestoreClient.addNickname(it)
                                        }
                                    }
                                }
                            )
                        }

                        composable("home") {
                            HomeScreen(
                                onExit = {
                                    lifecycleScope.launch {
                                        googleAuthUiClient.signOut()
                                        Toast.makeText(
                                            applicationContext,
                                            getString(R.string.signed_out),
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
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
                                onPlay = {
                                    unityBridge.setInfinite()
                                    ContextCompat.startActivity(
                                        ctx,
                                        Intent(ctx, Level::class.java),
                                        null
                                    )
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
                                },
                                onLevelSelected = {
                                    unityBridge.setLevel(it.id)
                                    ContextCompat.startActivity(
                                        ctx,
                                        Intent(ctx, Level::class.java),
                                        null
                                    )
                                }
                            )
                        }

                        composable("leaderboard") {
                            // TODO: Check if state reloads correctly when new scores are added
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

                        //Flow variable to the settings datastore to read is values  and pass them to the settings screen
                        // TODO: WTF
                        val preferencesLanguageFlow: Flow<String> =
                            dataStore.data.map { preferences ->
                                preferences[PreferencesKeys.LANGUAGE_KEY] ?: "English"
                            }
                        val preferencesVolumeFlow: Flow<Int> = dataStore.data.map { preferences ->
                            preferences[PreferencesKeys.VOLUME_KEY] ?: 50
                        }
                        val preferencesThemeFlow: Flow<Boolean> =
                            dataStore.data.map { preferences ->
                                preferences[PreferencesKeys.THEME_KEY] ?: false
                            }
                        composable("settings") {
                            SettingsScreen(
                                onExit = {
                                    navController.popBackStack()
                                },
                                onEnglish = {
                                    runBlocking {
                                        dataStore.edit { settings ->
                                            settings[PreferencesKeys.LANGUAGE_KEY] = "English"
                                        }
                                    }
                                    Toast.makeText(
                                        applicationContext,
                                        "English",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                },
                                onSpanish = {
                                    runBlocking {
                                        dataStore.edit { settings ->
                                            settings[PreferencesKeys.LANGUAGE_KEY] = "Spanish"
                                        }
                                    }
                                    Toast.makeText(
                                        applicationContext,
                                        "Spanish",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                },
                                preferencesLanguageFlow = preferencesLanguageFlow,
                                onVolume = {
                                    runBlocking {
                                        dataStore.edit { settings ->
                                            settings[PreferencesKeys.VOLUME_KEY] = it
                                        }
                                    }
                                    Toast.makeText(
                                        applicationContext,
                                        "Volume changed to $it%",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                },
                                preferencesVolumeFlow = preferencesVolumeFlow,
                                onTheme = {
                                    runBlocking {
                                        dataStore.edit { settings ->
                                            settings[PreferencesKeys.THEME_KEY] = it
                                        }
                                    }
                                    Toast.makeText(
                                        applicationContext,
                                        "Theme changed to ${if (it) "Dark" else "Light"}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                },
                                preferencesThemeFlow = preferencesThemeFlow
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