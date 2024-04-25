package es.finders.scapetheads.menu.nickname

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import es.finders.scapetheads.R
import es.finders.scapetheads.ui.theme.RedPrimary
import es.finders.scapetheads.ui.theme.ScapeTheAddsTheme
import es.finders.scapetheads.ui.utils.Logo

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

            OutlinedTextField(
                value = nickname,
                onValueChange = { nickname = it },
                modifier = Modifier,
                placeholder = { Text(text = stringResource(R.string.pick_nickname)) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                ),
                shape = RoundedCornerShape(35),
            )

            Spacer(Modifier.size(16.dp))
            Button(
                onClick = { onNext(nickname) },
                modifier = modifier
                    .padding(top = 5.dp, bottom = 20.dp)
                    .fillMaxWidth(0.70f),
                shape = RoundedCornerShape(35),
                contentPadding = PaddingValues(top = 10.dp, bottom = 10.dp),
                border = BorderStroke(1.dp, Color.Black),
                colors = ButtonDefaults.buttonColors(
                    containerColor = RedPrimary
                )

            ) {
                Text(
                    text = stringResource(R.string.next),
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontSize = 5.em,
                )
            }
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