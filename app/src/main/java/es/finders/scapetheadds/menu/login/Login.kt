package es.finders.scapetheadds.menu.login

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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import es.finders.scapetheadds.R
import es.finders.scapetheadds.menu.nickname.Nickname
import es.finders.scapetheadds.ui.theme.ScapeTheAddsTheme
import es.finders.scapetheadds.ui.utils.BasicBackground
import es.finders.scapetheadds.ui.utils.ButtonItem
import es.finders.scapetheadds.ui.utils.Logo

class Login : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScapeTheAddsTheme {
                LoginScreen()
            }
        }
    }
}

@Composable
fun LoginScreen(modifier: Modifier = Modifier) {
    Box(
        modifier,
        contentAlignment = Alignment.Center,
    ) {
        BasicBackground(modifier.fillMaxSize())
        LoginLayout(modifier.fillMaxSize())
    }
}

@Composable
fun LoginLayout(modifier: Modifier = Modifier) {
    val ctx = LocalContext.current
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Logo()
        Spacer(Modifier.size(100.dp))
        BasicInput()
        Spacer(Modifier.size(16.dp))
        PasswordInput()
        Spacer(Modifier.size(16.dp))
        ButtonItem(
            stringResource(R.string.next),
            {
                startActivity(ctx, Intent(ctx, Nickname::class.java), null)
            },
            Modifier
                .padding(top = 5.dp, bottom = 20.dp)
                .fillMaxWidth(0.70f)
        )
    }
}

@Composable
fun BasicInput(modifier: Modifier = Modifier) {
    OutlinedTextField(
        value = "",
        onValueChange = {},
        modifier = Modifier,
        placeholder = { Text(text = stringResource(R.string.email)) },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White
        ),
        shape = RoundedCornerShape(35),
    )
}

@Composable
fun PasswordInput(modifier: Modifier = Modifier) {
    OutlinedTextField(
        value = "",
        onValueChange = {},
        modifier = Modifier,
        placeholder = { Text(text = stringResource(R.string.password)) },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
        ),
        shape = RoundedCornerShape(35),
        visualTransformation = PasswordVisualTransformation() // Hide password characters
    )
}

@Preview(showBackground = false, showSystemUi = true)
@Composable
private fun LoginScreenPreview() {
    ScapeTheAddsTheme {
        LoginScreen()
    }
}