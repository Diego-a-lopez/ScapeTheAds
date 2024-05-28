package es.finders.scapetheads.menu.login

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import es.finders.scapetheads.services.auth.SignInState
import es.finders.scapetheads.ui.utils.Logo
import es.finders.scapetheads.ui.utils.SignInButton

@Composable
fun SignInScreen(
    state: SignInState,
    onExit: () -> Unit,
    onSignInClick: () -> Unit
) {
    val context = LocalContext.current

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(key1 = state.signInError) {
        state.signInError?.let { error ->
            Toast.makeText(
                context,
                error,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Logo()
        Spacer(Modifier.size(100.dp))
        /*
        TextInput(
            email,
            { email = it },
            { Text(text = stringResource(R.string.email)) }
        )
        Spacer(Modifier.size(16.dp))
        TextInput(
            password,
            { password = it },
            { Text(text = stringResource(R.string.email)) }
        )
        TextInput(
            password,
            { password = it },
            { Text(text = stringResource(R.string.email)) },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(Modifier.size(16.dp))
        ButtonItem(
            stringResource(R.string.next),
            {},
            Modifier
                .padding(top = 5.dp, bottom = 20.dp)
                .fillMaxWidth(0.70f)
        )
        Spacer(Modifier.size(16.dp))
        */
        // Text(text = stringResource(R.string.or))
        SignInButton(onClick = onSignInClick)
        /*Spacer(Modifier.size(16.dp))
        ButtonItem(
            text = stringResource(R.string.exit), onExit, Modifier
                .padding(top = 5.dp, bottom = 20.dp)
                .fillMaxWidth(0.70f)
        )*/
    }
}
