package es.finders.scapetheads.services.auth

data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)