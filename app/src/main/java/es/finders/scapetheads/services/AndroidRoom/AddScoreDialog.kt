package es.finders.scapetheads.services.AndroidRoom

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import es.finders.scapetheadds.AndroidRoom.LocalScoreState

@Composable
fun AddScoreDialog(
    state: LocalScoreState,
    onEvent: (LocalScoreEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = {
            onEvent(LocalScoreEvent.HideDialog)
        },
        title = { Text(text = "Add score") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(
                    value = state.date,
                    onValueChange = {
                        onEvent(LocalScoreEvent.SetDate(it))
                    },
                    placeholder = {
                        Text(text = "Date")
                    }
                )
                TextField(
                    value = state.scoreVal,
                    onValueChange = {
                        onEvent(LocalScoreEvent.SetScoreVal(it))
                    },
                    placeholder = {
                        Text(text = "Score")
                    }
                )
                TextField(
                    value = state.time,
                    onValueChange = {
                        onEvent(LocalScoreEvent.SetTime(it))
                    },
                    placeholder = {
                        Text(text = "Time")
                    }
                )
            }
        },
        buttons = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                Button(onClick = {
                    onEvent(LocalScoreEvent.SaveScore)
                }) {
                    Text(text = "Save")
                }
            }
        }
    )
}