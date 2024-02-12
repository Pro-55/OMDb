package com.example.omdb.data.firebase.sso

import android.content.Intent
import android.content.IntentSender
import com.example.omdb.BuildConfig
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await

class GoogleAuthHelper(
    private val client: SignInClient
) {

    suspend fun signIn(): IntentSender? = try {
        client.beginSignIn(
            buildSignInRequest()
        )
            .await()
    } catch (e: Exception) {
        null
    }
        ?.pendingIntent
        ?.intentSender

    fun getAuthCredential(data: Intent?): AuthCredential {
        val googleIdToken = client.getSignInCredentialFromIntent(data)
            .googleIdToken
        return GoogleAuthProvider.getCredential(googleIdToken, null)
    }

    private fun buildSignInRequest(): BeginSignInRequest = BeginSignInRequest.Builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions
                .builder()
                .setSupported(true)
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(BuildConfig.GCP_CLIENT_ID)
                .build()
        )
        .setAutoSelectEnabled(true)
        .build()
}