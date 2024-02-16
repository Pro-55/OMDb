package com.example.omdb.data.firebase.sso

import android.content.Context
import androidx.activity.result.ActivityResultRegistryOwner
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FacebookAuthProvider
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class MetaAuthHelper {

    suspend fun signIn(
        context: Context
    ): AuthCredential? {
        if (context !is ActivityResultRegistryOwner) return null
        val loginManager = LoginManager.getInstance()
        val callbackManager = CallbackManager.Factory.create()
        loginManager.logInWithReadPermissions(
            activityResultRegistryOwner = context,
            callbackManager = callbackManager,
            permissions = listOf("email", "public_profile")
        )
        return suspendCoroutine {
            register(
                loginManager = loginManager,
                callbackManager = callbackManager,
                onResponse = it::resume
            )
        }
    }

    private fun register(
        loginManager: LoginManager,
        callbackManager: CallbackManager,
        onResponse: (AuthCredential?) -> Unit
    ) {
        loginManager.registerCallback(callbackManager = callbackManager,
            callback = object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult) = onResponse(
                    FacebookAuthProvider.getCredential(
                        result.accessToken.token
                    )
                )

                override fun onCancel() = onResponse(null)

                override fun onError(error: FacebookException) = onResponse(null)
            })
    }
}