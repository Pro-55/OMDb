package com.example.omdb.ui.authentication

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.example.omdb.BuildConfig
import com.example.omdb.R
import com.example.omdb.databinding.FragmentSignUpBinding
import com.example.omdb.framework.BaseFragment
import com.example.omdb.models.Status
import com.example.omdb.models.local.EntityUser
import com.example.omdb.ui.HomeViewModel
import com.example.omdb.util.Constants
import com.example.omdb.util.Constants.REQUEST_GOOGLE_SIGN_IN
import com.example.omdb.util.extensions.*
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.*
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class SignUpFragment : BaseFragment() {

    //Global
    private val TAG = SignUpFragment::class.java.simpleName
    @Inject lateinit var sp: SharedPreferences
    @Inject lateinit var auth: FirebaseAuth
    @Inject lateinit var crashlytics: FirebaseCrashlytics
    private lateinit var binding: FragmentSignUpBinding
    private val viewModel by viewModels<HomeViewModel>()
    private val fbCallbackManager: CallbackManager by lazy { CallbackManager.Factory.create() }
    private var profileUrl: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnGoogle.setOnClickListener { hitLoginGoogle() }

        binding.btnFacebook.setOnClickListener { hitLoginFacebook() }

        binding.btnSave.setOnClickListener {
            hideKeyboard()
            clearFocus()
            val firstName = binding.editFirstName.text?.toString()?.trim()
            val lastName = binding.editLastName.text?.toString()?.trim()
            val email = binding.editEmail.text?.toString()?.trim()
            if (isValid(firstName, lastName, email)) {
                val id = auth.currentUser?.uid ?: UUID.randomUUID().toString()
                val user = EntityUser(
                    _id = id,
                    firstName = firstName ?: "",
                    lastName = lastName ?: "",
                    email = email ?: "",
                    profileUrl = profileUrl
                )
                viewModel.signUp(user).observe(viewLifecycleOwner, { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            sp.edit().putBoolean(Constants.KEY_SIGN_UP_STATUS, true).apply()
                            val action = SignUpFragmentDirections.navigateSignUpToHome()
                            findNavController().navigate(action)
                        }
                        Status.ERROR -> showShortToast(resource.message)
                    }
                })

            }
        }

        binding.editFirstName.apply {
            setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) binding.tilFirstName.error = null
            }
            setOnEditorActionListener { _, _, _ ->
                clearFocus()
                hideKeyboard()
                true
            }
        }

        binding.editLastName.apply {
            setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) binding.tilLastName.error = null
            }
            setOnEditorActionListener { _, _, _ ->
                clearFocus()
                hideKeyboard()
                true
            }
        }

        binding.editEmail.apply {
            setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) binding.tilEmail.error = null
            }
            setOnEditorActionListener { _, _, _ ->
                clearFocus()
                hideKeyboard()
                true
            }
        }
    }

    private fun hitLoginGoogle() {
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(BuildConfig.GCP_CLIENT_ID)
            .requestEmail()
            .requestProfile()
            .build()

        // Build a GoogleSignInClient with the options specified by gso.
        val googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)

        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, REQUEST_GOOGLE_SIGN_IN)
    }

    private fun hitLoginFacebook() {

        val fbLoginManager = LoginManager.getInstance()

        val fbParams = listOf("email", "public_profile")

        // Set Read permission for fields
        fbLoginManager.logInWithReadPermissions(this@SignUpFragment, fbParams)

        // Facebook callback for retrieving Access Token
        fbLoginManager.registerCallback(fbCallbackManager, object : FacebookCallback<LoginResult> {

            override fun onSuccess(result: LoginResult) {
                val credential = FacebookAuthProvider.getCredential(result.accessToken.token)
                firebaseAuth(credential)
            }

            override fun onCancel() {
                Log.e(TAG, "TestLog: Facebook sign in canceled!")
            }

            override fun onError(e: FacebookException) {
                Log.e(TAG, "TestLog: Facebook sign in failed: $e")
                crashlytics.recordException(e)
                showShortSnackBar(Constants.REQUEST_FAILED_MESSAGE)
            }

        })

    }

    private fun isValid(firstName: String?, lastName: String?, email: String?): Boolean {
        var isValid = true

        if (firstName.isNullOrEmpty()) {
            binding.tilFirstName.error = " "
            isValid = false
        }

        if (lastName.isNullOrEmpty()) {
            binding.tilLastName.error = " "
            isValid = false
        }

        if (!email.isValidEmail()) {
            binding.tilEmail.error = " "
            isValid = false
        }

        return isValid
    }

    private fun clearFocus() {
        requireActivity().currentFocus?.clearFocus()
    }

    private fun hideKeyboard() {
        requireActivity().hideKeyboard()
    }

    override fun onStart() {
        super.onStart()
        val user = auth.currentUser ?: return
        setFirebaseUser(user)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // Facebook Callback for Sign In
        fbCallbackManager.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            REQUEST_GOOGLE_SIGN_IN -> {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)
                    if (account != null) {
                        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                        firebaseAuth(credential)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.e(TAG, "TestLog: Google sign in failed: $e")
                    crashlytics.recordException(e)
                    showShortSnackBar(Constants.REQUEST_FAILED_MESSAGE)
                }
            }
        }
    }

    private fun firebaseAuth(credential: AuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser ?: return@addOnCompleteListener
                    setFirebaseUser(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.e(TAG, "TestLog: signInWithCredential failed: ${task.exception}")
                }
            }
    }

    private fun setFirebaseUser(user: FirebaseUser) {
        val name = user.displayName?.split(" ")
        if (!name.isNullOrEmpty()) {
            val firstName = name.first()
            val lastName = name.last()

            if (firstName.isNotEmpty()) binding.editFirstName.setText(firstName)
            if (lastName.isNotEmpty()) binding.editLastName.setText(lastName)
        }
        val email = user.email
        if (!email.isNullOrEmpty()) binding.editEmail.setText(email)

        profileUrl = user.photoUrl?.toString()

        glide().load(profileUrl)
            .transform(CircleCrop())
            .placeholder(
                ResourcesCompat.getDrawable(resources, R.drawable.ic_profile_placeholder, null)
            )
            .apply(RequestOptions().override(200, 200))
            .into(binding.imgProfile)
    }

}
