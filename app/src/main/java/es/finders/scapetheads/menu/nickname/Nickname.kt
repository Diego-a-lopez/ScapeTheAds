package es.finders.scapetheads.menu.nickname

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import es.finders.scapetheads.R
import es.finders.scapetheads.ui.theme.ScapeTheAddsTheme
import es.finders.scapetheads.ui.utils.ButtonItem
import es.finders.scapetheads.ui.utils.Logo
import es.finders.scapetheads.ui.utils.TextInput

@Composable
fun NicknameScreen(
    onNext: (nickname: String) -> Unit,
    modifier: Modifier = Modifier
) {
    var nickname by remember { mutableStateOf("") }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.size(150.dp))
        Logo()
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            TextInput(
                nickname,
                { nickname = it },
                { Text(text = stringResource(R.string.pick_nickname)) }
            )

            Spacer(Modifier.size(16.dp))
            ButtonItem(
                stringResource(R.string.next),
                onClick = { onNext(nickname) },
                modifier = modifier
                    .padding(top = 5.dp, bottom = 20.dp)
                    .fillMaxWidth(0.70f),
            )
        }
    }
}

@Preview(showBackground = false, showSystemUi = true)
@Composable
private fun NicknameScreenPreview() {
    ScapeTheAddsTheme {
        NicknameScreen(onNext = {})
    }
}