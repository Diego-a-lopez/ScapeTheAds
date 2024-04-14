package es.finders.scapetheads.menu.nickname

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import es.finders.scapetheads.R
import es.finders.scapetheads.menu.home.Home
import es.finders.scapetheads.ui.theme.ScapeTheAddsTheme
import es.finders.scapetheads.ui.utils.BasicBackground
import es.finders.scapetheads.ui.utils.ButtonItem
import es.finders.scapetheads.ui.utils.Logo

@Composable
fun NicknameScreen(modifier: Modifier = Modifier,
                   onSignOut: () -> Unit) {
    val ctx = LocalContext.current
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Logo()
        Spacer(Modifier.size(150.dp))
        NicknameInput()
        Spacer(Modifier.size(16.dp))
        ButtonItem(
            stringResource(R.string.next),
            {
                ContextCompat.startActivity(ctx, Intent(ctx, Home::class.java), null)
            },
            Modifier
                .padding(top = 5.dp, bottom = 20.dp)
                .fillMaxWidth(0.70f)
        )
        ButtonItem(
            stringResource(R.string.exit),
            onSignOut,
            Modifier
                .padding(top = 5.dp, bottom = 20.dp)
                .fillMaxWidth(0.70f)
        )
    }
}

@Composable
fun NicknameInput(modifier: Modifier = Modifier) {
    OutlinedTextField(
        value = "",
        onValueChange = {},
        modifier = Modifier,
        placeholder = { Text(text = stringResource(R.string.pick_nickname)) },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White
        ),
        shape = RoundedCornerShape(35),
    )
}

@Preview(showBackground = false, showSystemUi = true)
@Composable
private fun NicknameScreenPreview() {
    ScapeTheAddsTheme {
        NicknameScreen(onSignOut = {})
    }
}