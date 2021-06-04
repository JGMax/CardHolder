package gortea.jgmax.cardholder.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.textfield.TextInputEditText
import gortea.jgmax.cardholder.R
import gortea.jgmax.cardholder.databinding.FragmentLoginBinding
import gortea.jgmax.cardholder.interfaces.Callbacks
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private val callbacks: Callbacks? by lazy {
        context as? Callbacks
    }

    private val binding: FragmentLoginBinding by lazy {
        FragmentLoginBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding.apply {
            loginButton.setOnClickListener { onLoginClick() }
            registerButton.setOnClickListener { onRegisterClick() }
        }
        return binding.root
    }

    private fun onLoginClick() {
        binding.apply {
            if (checkNotEmptyFields(userNameTextInput, passwordTextInput)) {

                val userName = userNameTextInput.value() as String
                val password = passwordTextInput.value() as String

                CoroutineScope(Dispatchers.IO).launch {
                    if (callbacks?.onLoginClick(userName, password) == false) {
                        CoroutineScope(Dispatchers.Main).launch {
                            userNameTextInput.error = getString(R.string.incorrect_data)
                            passwordTextInput.error = getString(R.string.incorrect_data)
                        }
                    }
                }
            }
        }
    }

    private fun checkNotEmptyFields(vararg fields: TextInputEditText) : Boolean {
        var notEmpty = true
        fields.forEach {
            if (it.isNullOrEmpty()) {
                it.error = getString(R.string.empty_field)
                notEmpty = false
            }
        }
        return notEmpty
    }

    private fun TextInputEditText.isNullOrEmpty() : Boolean {
        return value().isNullOrEmpty()
    }

    private fun TextInputEditText.value() : String? = text?.toString()

    private fun onRegisterClick() {
        binding.apply {
            if (checkNotEmptyFields(userNameTextInput, passwordTextInput)) {

                val userName = userNameTextInput.value() as String
                val password = passwordTextInput.value() as String

                CoroutineScope(Dispatchers.IO).launch {
                    if (callbacks?.onRegisterClick(userName, password) == false) {
                        CoroutineScope(Dispatchers.Main).launch {
                            userNameTextInput.error = getString(R.string.user_name_already_created)
                        }
                    }
                }
            }
        }
    }
}