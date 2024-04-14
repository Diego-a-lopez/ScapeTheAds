package es.finders.scapetheads.auth

data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)