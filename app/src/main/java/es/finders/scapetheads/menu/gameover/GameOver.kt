package es.finders.scapetheads.menu.gameover

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import es.finders.scapetheads.R
import es.finders.scapetheads.ui.theme.ScapeTheAddsTheme
import es.finders.scapetheads.ui.utils.ButtonItem
import es.finders.scapetheads.ui.utils.CardBackgroundColumn
import es.finders.scapetheads.ui.utils.OutlineTextSection

@Composable
fun GameOverScreen(
    onExit: () -> Unit,
    modifier: Modifier = Modifier,
    stage: Int,
    clearTime: Int,
    score: Int
) {
    // TODO: Store in local room score
    // TODO: Send to firestore data if highscore > current user highscore
    Box(
        modifier,
        contentAlignment = Alignment.Center,
    ) {
        val colorScheme = MaterialTheme.colorScheme
        CardBackgroundColumn(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlineTextSection("Game Over")
            Spacer(Modifier.size(300.dp))
            Surface(
                modifier = Modifier.padding(16.dp).background(colorScheme.background),
                shape = MaterialTheme.shapes.medium,
                border = BorderStroke(3.dp, colorScheme.tertiary),
            ) {
                Column {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(text = "Niveles superados:", color = colorScheme.onBackground)
                        Spacer(modifier = Modifier.weight(1f))
                        Text(text = "$stage", color = colorScheme.onBackground)
                    }
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(text = "Tiempo:", color = colorScheme.onBackground)
                        Spacer(modifier = Modifier.weight(1f))
                        Text(text = "$clearTime s", color = colorScheme.onBackground)
                    }
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(text = "Puntuación:", color = colorScheme.onBackground)
                        Spacer(modifier = Modifier.weight(1f))
                        Text(text = "$score pto", color = colorScheme.onBackground)
                    }
                }

            }
            Spacer(Modifier.size(16.dp))
            ButtonItem(
                stringResource(R.string.next),
                onExit,
                Modifier
                    .padding(top = 5.dp, bottom = 20.dp)
                    .fillMaxWidth(0.70f)
            )
        }
    }
}

@Preview(showBackground = false, showSystemUi = true)
@Composable
private fun GameOverScreenPreview() {
    ScapeTheAddsTheme {
        GameOverScreen({}, stage = 1, clearTime = 600, score = 250)
    }
}
