package com.plcoding.roomguideandroid

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import es.finders.scapetheadds.AndroidRoom.LocalScoreState
import es.finders.scapetheads.R
import es.finders.scapetheads.services.AndroidRoom.AddScoreDialog
import es.finders.scapetheads.services.AndroidRoom.LocalScoreEvent
import es.finders.scapetheads.services.AndroidRoom.ScoreSortType
import es.finders.scapetheads.ui.theme.ScapeTheAddsTheme

class Scores : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val scoresType =
                intent.getStringExtra("scoresType") ?: stringResource(R.string.global_scores)
            //val scoresType = stringResource(R.string.global_scores)
            ScapeTheAddsTheme {
                //val state by viewModel.state.collectAsState()
                //ScoreScreen(state = state, ctx = LocalContext.current, onEvent = viewModel::onEvent)
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ScoreScreen(
    state: LocalScoreState,
    onEvent: (LocalScoreEvent) -> Unit
) {

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onEvent(LocalScoreEvent.ShowDialog)
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add contact"
                )
            }
        },
    ) { _ ->
        if (state.isAddingScore) {
            AddScoreDialog(state = state, onEvent = onEvent)
        }

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    verticalAlignment = CenterVertically
                ) {
                    ScoreSortType.values().forEach { sortType ->
                        Row(
                            modifier = Modifier
                                .clickable {
                                    onEvent(LocalScoreEvent.SortScores(sortType))
                                },
                            verticalAlignment = CenterVertically
                        ) {
                            RadioButton(
                                selected = state.scoreSortType == sortType,
                                onClick = {
                                    onEvent(LocalScoreEvent.SortScores(sortType))
                                }
                            )
                            Text(text = sortType.name)
                        }
                    }
                }
            }

            items(state.localScores) { contact ->
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "${contact.date} ${contact.score}",
                            fontSize = 20.sp
                        )
                        Text(text = contact.time, fontSize = 12.sp)
                    }
                    IconButton(onClick = {
                        onEvent(LocalScoreEvent.DeleteScore(contact))
                    }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete contact"
                        )
                    }
                }
            }
        }
    }
}