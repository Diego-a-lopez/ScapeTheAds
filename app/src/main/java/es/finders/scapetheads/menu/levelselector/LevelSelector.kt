package es.finders.scapetheads.menu.levelselector

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import es.finders.scapetheads.R
import es.finders.scapetheads.ui.theme.ScapeTheAddsTheme
import es.finders.scapetheads.ui.utils.BackButton
import es.finders.scapetheads.ui.utils.ButtonItem
import es.finders.scapetheads.ui.utils.CardBackgroundColumn
import es.finders.scapetheads.ui.utils.OutlineTextSection

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LevelSelectorScreen(
    onExit: () -> Unit,
    onLevelSelected: (level: LevelData) -> Unit,
    modifier: Modifier = Modifier
) {
    val buttonModifier = Modifier
        .width(70.dp)
        .padding(4.dp)
        .height(70.dp)

    val levels = listOf(
        LevelData(name = "1", id = 1, metadata = "First level"),
        LevelData(name = "2", id = 2, metadata = "Second level"),
        LevelData(name = "3", id = 3, metadata = "Third level")
    )
    Box(
        modifier,
        contentAlignment = Alignment.Center,
    ) {
        CardBackgroundColumn(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlineTextSection(stringResource(R.string.level_selection))
            FlowRow(modifier = Modifier.padding(8.dp)) {
                levels.forEach { level ->
                    ButtonItem(
                        level.name,
                        { onLevelSelected(level) },
                        buttonModifier
                    )
                }
            }
        }
        BackButton(
            onExit, Modifier
                .align(Alignment.TopEnd)
                .padding(top = 20.dp)
        )
    }
}

@Preview(showBackground = false, showSystemUi = true)
@Composable
private fun LevelSelectorScreenPreview() {
    ScapeTheAddsTheme {
        LevelSelectorScreen({}, {})
    }
}

class LevelData(
    val name: String,
    val id: Int,
    val metadata: String
)