package es.finders.scapetheads

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import es.finders.scapetheads.menu.ScoresTest.ScoreScreen
import es.finders.scapetheads.services.AndroidRoom.LocalScoreDatabase
import es.finders.scapetheads.services.AndroidRoom.LocalScoreViewModel
import es.finders.scapetheads.ui.theme.ScapeTheAddsTheme

class MainActivity3 : ComponentActivity() {

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScapeTheAddsTheme {
                val state by viewModel.state.collectAsState()

                ScoreScreen(state = state, onEvent = viewModel::onEvent)
            }
        }
    }
}