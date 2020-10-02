package com.example.omdb.ui.authentication

import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.omdb.BaseFragment
import com.example.omdb.R
import com.example.omdb.databinding.FragmentSignUpBinding
import com.example.omdb.models.User
import com.example.omdb.ui.HomeViewModel
import com.example.omdb.util.Constants
import com.example.omdb.util.extensions.getViewModel
import com.example.omdb.util.extensions.hideKeyboard
import com.example.omdb.util.extensions.isValidEmail
import com.example.omdb.util.extensions.isValidName
import java.util.*
import javax.inject.Inject

class SignUpFragment : BaseFragment() {

    companion object {
        private val TAG = SignUpFragment::class.java.simpleName
    }

    //Global
    @Inject lateinit var factory: ViewModelProvider.Factory
    @Inject lateinit var sp: SharedPreferences
    private lateinit var binding: FragmentSignUpBinding
    private val viewModel by lazy { requireActivity().getViewModel<HomeViewModel>(factory) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSave.setOnClickListener {
            hideKeyboard()
            clearFocus()
            if (isValid()) {
                val id = UUID.randomUUID().toString()
                val fName = binding.editFirstName.text.toString()
                val lName = binding.editLastName.text.toString()
                val email = binding.editEmail.text.toString()
                val user = User(_id = id, firstName = fName, lastName = lName, email = email)
                viewModel.signUp(user)
                sp.edit().putBoolean(Constants.KEY_SIGN_UP_STATUS, true).apply()
                val action = SignUpFragmentDirections.navigateSignUpToHome()
                findNavController().navigate(action)
            }
        }

        binding.editFirstName.apply {
            addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {}

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val text = s?.toString()?.trim() ?: ""
                    if (text.isEmpty() || text.length == 1) binding.txtProfile.text = text
                }
            })
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

    private fun isValid(): Boolean {
        var isValid = true

        val fName = binding.editFirstName.text?.toString() ?: ""
        if (!fName.isValidName()) {
            binding.tilFirstName.error = " "
            isValid = false
        }

        val lName = binding.editLastName.text?.toString() ?: ""
        if (!lName.isValidName()) {
            binding.tilLastName.error = " "
            isValid = false
        }

        val eMail = binding.editEmail.text?.toString() ?: ""
        if (!eMail.isValidEmail()) {
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


}
