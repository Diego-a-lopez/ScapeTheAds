package es.finders.scapetheads.menu.Victory

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
fun LevelOver(onExit: () -> Unit, victory: Boolean = true, modifier: Modifier = Modifier) {
    Box(
        modifier,
        contentAlignment = Alignment.Center,
    ) {
        CardBackgroundColumn(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (victory) {
                OutlineTextSection(stringResource(R.string.level_completed))
            } else {
                OutlineTextSection(stringResource(R.string.you_lose))
            }
            Spacer(Modifier.size(300.dp))
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
private fun LevelOverPreview() {
    ScapeTheAddsTheme {
        LevelOver({})
    }
}

