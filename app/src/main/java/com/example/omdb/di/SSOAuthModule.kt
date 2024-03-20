package com.example.omdb.di

import android.app.Application
import com.example.omdb.data.firebase.sso.GoogleAuthHelper
import com.example.omdb.data.firebase.sso.MetaAuthHelper
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object SSOAuthModule {

    @ViewModelScoped
    @Provides
    fun provideSignInClient(
        application: Application
    ): SignInClient = Identity.getSignInClient(application)

    @ViewModelScoped
    @Provides
    fun provideGoogleAuthHelper(
        client: SignInClient
    ): GoogleAuthHelper = GoogleAuthHelper(client = client)

    @ViewModelScoped
    @Provides
    fun provideMetaAuthHelper(): MetaAuthHelper = MetaAuthHelper()

}