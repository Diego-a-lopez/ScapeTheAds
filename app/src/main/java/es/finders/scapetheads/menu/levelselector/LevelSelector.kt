package es.finders.scapetheads.menu.levelselector

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import es.finders.scapetheads.R
import es.finders.scapetheads.ui.theme.BeigeBackground
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
        LevelData(name = "1", id = 1, metadata = "First level: Dodge the falling blocks"),
        LevelData(name = "2", id = 2, metadata = "Second level: Coming soon"),
        LevelData(name = "3", id = 3, metadata = "Third level: Coming soon"),
        LevelData(name = "4", id = 3, metadata = "Fourth level: Coming soon"),
        LevelData(name = "5", id = 3, metadata = "Fifth level: Coming soon")
    )
    Box(
        modifier,
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            Card(
                border = BorderStroke(2.dp, Color.Black),
                backgroundColor = BeigeBackground,
                modifier = Modifier
                    .fillMaxWidth(0.95f)
                    .padding(bottom = 12.dp),
            ) {
                OutlineTextSection(stringResource(R.string.level_selection))
            }
            FlowRow(modifier = Modifier.padding(8.dp)) {
                levels.forEach { level ->
                    Card(
                        border = BorderStroke(2.dp, Color.Black),
                        backgroundColor = BeigeBackground,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp)
                            .clickable {
                                onLevelSelected(level)
                            },
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            OutlineTextSection(level.metadata, textSize = 25.sp)
                            Image(
                                painter = painterResource(id = R.drawable.level1),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .height(150.dp)
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(4.dp))
                            )
                        }
                    }
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
    val data = LevelData(name = "1", id = 1, metadata = "Level 1: Dodge the falling blocks")
    ScapeTheAddsTheme {
        //LevelSelectorScreen({}, {})
        Card(
            border = BorderStroke(2.dp, Color.Black),
            backgroundColor = BeigeBackground,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                OutlineTextSection(data.metadata, textSize = 25.sp)
                Image(
                    painter = painterResource(id = R.drawable.level1),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(150.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(4.dp))
                )
            }
        }
    }
}

class LevelData(
    val name: String,
    val id: Int,
    val metadata: String
)