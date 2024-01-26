package com.example.omdb.ui.authentication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.request.RequestOptions
import com.example.omdb.BuildConfig
import com.example.omdb.R
import com.example.omdb.databinding.FragmentSignUpBinding
import com.example.omdb.domain.model.Resource
import com.example.omdb.framework.BaseFragment
import com.example.omdb.util.Constants
import com.example.omdb.util.Constants.REQUEST_GOOGLE_SIGN_IN
import com.example.omdb.util.extensions.addProfilePlaceholder
import com.example.omdb.util.extensions.disable
import com.example.omdb.util.extensions.diskCacheStrategyAll
import com.example.omdb.util.extensions.enable
import com.example.omdb.util.extensions.glide
import com.example.omdb.util.extensions.hideKeyboard
import com.example.omdb.util.extensions.isValidEmail
import com.example.omdb.util.extensions.showShortSnackBar
import com.example.omdb.util.extensions.showShortToast
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.github.razir.progressbutton.attachTextChangeAnimator
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignUpFragment : BaseFragment() {

    //Global
    private val TAG = SignUpFragment::class.java.simpleName
    @Inject lateinit var crashlytics: FirebaseCrashlytics
    private lateinit var binding: FragmentSignUpBinding
    private val viewModel by viewModels<SignUpViewModel>()
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

        setListeners()

        setObservers()
    }

    private fun setListeners() {
        binding.btnGoogle.setOnClickListener { hitLoginGoogle() }

        binding.btnFacebook.setOnClickListener { hitLoginFacebook() }

        binding.btnSave.apply {
            this@SignUpFragment.bindProgressButton(this)
            attachTextChangeAnimator()
            setOnClickListener {
                requireActivity().hideKeyboard()
                clearFocus()
                val firstName = binding.editFirstName.text?.toString()?.trim()
                val lastName = binding.editLastName.text?.toString()?.trim()
                val email = binding.editEmail.text?.toString()?.trim()
                if (isValid(firstName, lastName, email)) {
                    viewModel.signUp(
                        firstName = firstName ?: "",
                        lastName = lastName ?: "",
                        email = email ?: "",
                        profileUrl = profileUrl
                    )
                }
            }
        }

        binding.editFirstName.apply {
            setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) binding.tilFirstName.error = null
            }
            setOnEditorActionListener { _, _, _ ->
                clearFocus()
                requireActivity().hideKeyboard()
                true
            }
        }

        binding.editLastName.apply {
            setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) binding.tilLastName.error = null
            }
            setOnEditorActionListener { _, _, _ ->
                clearFocus()
                requireActivity().hideKeyboard()
                true
            }
        }

        binding.editEmail.apply {
            setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) binding.tilEmail.error = null
            }
            setOnEditorActionListener { _, _, _ ->
                clearFocus()
                requireActivity().hideKeyboard()
                true
            }
        }
    }

    private fun setObservers() {
        viewModel.firebaseUser.observe(viewLifecycleOwner) {
            if (it !is Resource.Success) return@observe
            setFirebaseUser(user = it.data!!)
        }

        viewModel.user.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> startLoading()
                is Resource.Success -> {
                    stopLoading(true)
                    val action = SignUpFragmentDirections.navigateSignUpToHome()
                    findNavController().navigate(action)
                }
                is Resource.Error -> stopLoading(false, it.msg)
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
        fbLoginManager.logInWithReadPermissions(this@SignUpFragment, fbCallbackManager, fbParams)

        // Facebook callback for retrieving Access Token
        fbLoginManager.registerCallback(fbCallbackManager, object : FacebookCallback<LoginResult> {

            override fun onSuccess(result: LoginResult) {
                val credential = FacebookAuthProvider.getCredential(result.accessToken.token)
                viewModel.fetchFirebaseUser(credential)
            }

            override fun onCancel() {
                Log.e(TAG, "TestLog: Facebook sign in canceled!")
            }

            override fun onError(error: FacebookException) {
                Log.e(TAG, "TestLog: Facebook sign in failed: $error")
                crashlytics.recordException(error)
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
                        viewModel.fetchFirebaseUser(credential)
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
            .diskCacheStrategyAll()
            .circleCrop()
            .addProfilePlaceholder(requireContext())
            .apply(RequestOptions().override(200, 200))
            .into(binding.imgProfile)
    }

    private fun startLoading() {
        binding.editFirstName.disable()
        binding.editLastName.disable()
        binding.editEmail.disable()
        binding.btnGoogle.disable()
        binding.btnFacebook.disable()
        binding.btnSave.apply {
            disable()
            showProgress {
                buttonText = "Saving..."
                progressColor = ContextCompat.getColor(requireContext(), R.color.color_primary_text)
            }
        }
    }

    private fun stopLoading(isSuccess: Boolean, message: String? = null) {
        if (!isSuccess) showShortToast(message)
        binding.editFirstName.enable()
        binding.editLastName.enable()
        binding.editEmail.enable()
        binding.btnGoogle.enable()
        binding.btnFacebook.enable()
        binding.btnSave.apply {
            enable()
            if (isSuccess) {
                hideProgress("Saved")
            } else {
                hideProgress("Save")
            }
        }
    }

}