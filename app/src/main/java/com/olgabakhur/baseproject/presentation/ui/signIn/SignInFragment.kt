package com.olgabakhur.baseproject.presentation.ui.signIn

import android.os.Bundle
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.olgabakhur.baseproject.App
import com.olgabakhur.baseproject.R
import com.olgabakhur.baseproject.databinding.FragmentSignInBinding
import com.olgabakhur.baseproject.presentation.base.BaseFragment
import com.olgabakhur.baseproject.presentation.extensions.collectWhenStarted
import com.olgabakhur.baseproject.presentation.util.viewModel.viewModel
import com.olgabakhur.data.util.result.Result

class SignInFragment : BaseFragment(R.layout.fragment_sign_in) {

    private val binding by viewBinding(FragmentSignInBinding::bind)
    override val viewModel: SignInViewModel by viewModel { App.appComponent.signInViewModel }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSignInButtonClickListener()
    }

    override fun observeViewModel() {
        super.observeViewModel()

        collectWhenStarted(viewModel.signInFlow) { result ->
            when (result) {
                is Result.Success -> {
                    /* do "onSuccess" actions */
                }

                is Result.Error -> {
                    /* do "onError" actions */
                }
            }
        }
    }

    private fun setupSignInButtonClickListener() {
        binding.btnSignIn.setOnClickListener {
            viewModel.signIn("email@gmail.com", "0123456789")
        }
    }
}