package es.finders.scapetheads

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.media.MediaPlayer
import android.os.Bundle
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
import androidx.compose.runtime.remember
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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.Room
import com.google.android.gms.auth.api.identity.Identity
import es.finders.scapetheads.menu.Victory.LevelOver
import es.finders.scapetheads.menu.gameover.GameOverScreen
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
import es.finders.scapetheads.services.singletons.appModule
import es.finders.scapetheads.services.unity.UnityBridge
import es.finders.scapetheads.ui.theme.ScapeTheAddsTheme
import es.finders.scapetheads.ui.utils.BasicBackground
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import java.util.Locale

class MainActivity : ComponentActivity() {


    private val Context.dataStore by preferencesDataStore(name = "settings")

    private lateinit var mediaPlayer: MediaPlayer

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

    private val unityBridge: UnityBridge by inject()

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

    fun updateLocale(context: Context, language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }

    suspend fun initiateDataStore() {
        applicationContext.dataStore.edit { settings ->
            settings[PreferencesKeys.LANGUAGE_KEY] = "English"
            settings[PreferencesKeys.VOLUME_KEY] = 50
            settings[PreferencesKeys.THEME_KEY] = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize MediaPlayer with the music file
        mediaPlayer = MediaPlayer.create(this, R.raw.jingle)
        mediaPlayer.isLooping = true // Loop the music if necessary

        // Start music playback
        mediaPlayer.start()

        // Observe volume changes
        val preferencesVolumeFlow: Flow<Int> = dataStore.data.map { preferences ->
            preferences[PreferencesKeys.VOLUME_KEY] ?: 50
        }

        lifecycleScope.launch {
            preferencesVolumeFlow.collect { volume ->
                setMediaPlayerVolume(volume)
            }
        }


        val languageFlow: Flow<String> = dataStore.data.map { preferences ->
            preferences[PreferencesKeys.LANGUAGE_KEY] ?: "en"
        }
        startKoin {
            androidContext(this@MainActivity)
            modules(appModule)
        }
        runBlocking {
            initiateDataStore()
        }

        var scoreMode: String = getString(R.string.local_scores)
        unityBridge.setMode(JSONObject().apply {
            put("gamemode", "level")
        })

        setContent {
            val ctx = LocalContext.current
            val navController = rememberNavController()
            val language by languageFlow.collectAsState(initial = "en")

            LaunchedEffect(language) {
                updateLocale(ctx, language)
            }

            val eventFlow by remember { unityBridge.eventFlow }.collectAsState(initial = null)
            eventFlow?.let { event ->
                val sharedPreferences = ctx.getSharedPreferences("GamePrefs", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("gameResult", event)
                editor.apply()
            }

            ScapeTheAddsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    BasicBackground(Modifier.fillMaxSize())
                    NavHost(navController = navController, startDestination = "sign_in") {
                        composable("sign_in") {
                            Log.d("NAVIGATION", "Sign in")
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
                            Log.d("NAVIGATION", "Home")
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

                            val sharedPreferences = getSharedPreferences("GamePrefs", MODE_PRIVATE)
                            val gameResult = sharedPreferences.getString("gameResult", null)
                            if (gameResult != null) {
                                val gameState: String = gameResult
                                val message = JSONObject(gameState)
                                when (message.getString("type")) {
                                    "InfiniteMode" -> {
                                        val stage = message.getInt("stage")
                                        val clearTime = message.getInt("clearTime")
                                        val score = message.getInt("score")
                                        navController.navigate("game_over/$stage/$clearTime/$score")
                                    }

                                    "LevelClear" -> {
                                        navController.navigate("victory")
                                    }

                                    "LevelFailed" -> {
                                        navController.navigate("defeat")
                                    }
                                }
                            }
                        }

                        composable("level_selector") {
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
                                    lifecycleScope.launch {
                                        dataStore.edit { settings ->
                                            settings[PreferencesKeys.LANGUAGE_KEY] = "en"
                                        }
                                        navController.popBackStack()
                                    }
                                    Toast.makeText(
                                        applicationContext,
                                        "English",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                },
                                onSpanish = {
                                    lifecycleScope.launch {
                                        dataStore.edit { settings ->
                                            settings[PreferencesKeys.LANGUAGE_KEY] = "es"
                                        }
                                        navController.popBackStack()
                                    }
                                    Toast.makeText(
                                        applicationContext,
                                        "Spanish",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                },
                                preferencesLanguageFlow = preferencesLanguageFlow,
                                onVolume = { volume ->
                                    lifecycleScope.launch {
                                        dataStore.edit { settings ->
                                            settings[PreferencesKeys.VOLUME_KEY] = volume
                                        }
                                    }
                                    Toast.makeText(
                                        applicationContext,
                                        "Volume changed to $volume%",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                },
                                preferencesVolumeFlow = preferencesVolumeFlow,
                                onTheme = { isDark ->
                                    lifecycleScope.launch {
                                        dataStore.edit { settings ->
                                            settings[PreferencesKeys.THEME_KEY] = isDark
                                        }
                                    }
                                    Toast.makeText(
                                        applicationContext,
                                        "Theme changed to ${if (isDark) "Dark" else "Light"}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                },
                                preferencesThemeFlow = preferencesThemeFlow
                            )
                        }

                        // Infinite mode
                        composable("game_over/{stage}/{clearTime}/{score}",
                            arguments = listOf(
                                navArgument("stage") { type = NavType.IntType },
                                navArgument("clearTime") { type = NavType.IntType },
                                navArgument("score") { type = NavType.IntType }
                            )) { backStackEntry ->
                            val stage = backStackEntry.arguments?.getInt("stage") ?: 0
                            val clearTime = backStackEntry.arguments?.getInt("clearTime") ?: 0
                            val score = backStackEntry.arguments?.getInt("score") ?: 0
                            Log.d("NAVIGATION", "Game over")
                            GameOverScreen(
                                stage = stage,
                                clearTime = clearTime,
                                score = score,
                                onExit = {
                                    getSharedPreferences("GamePrefs", MODE_PRIVATE).edit().clear()
                                        .apply()
                                    navController.popBackStack()
                                }
                            )
                        }

                        // Level select mode
                        composable("defeat") {
                            Log.d("NAVIGATION", "Defeat")
                            LevelOver(
                                onExit = {
                                    getSharedPreferences("GamePrefs", MODE_PRIVATE).edit().clear()
                                        .apply()
                                    navController.popBackStack()
                                },
                                victory = false
                            )
                        }

                        composable("victory") {
                            Log.d("NAVIGATION", "Victory")
                            LevelOver(
                                onExit = {
                                    getSharedPreferences("GamePrefs", MODE_PRIVATE).edit().clear()
                                        .apply()
                                    navController.popBackStack()
                                },
                                victory = true
                            )
                        }
                    }
                }
            }
        }
    }

    private fun setMediaPlayerVolume(volume: Int) {
        val volumeLevel = volume / 100f
        mediaPlayer.setVolume(volumeLevel, volumeLevel)
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