package com.olgabakhur.baseproject.presentation.ui.signin

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.olgabakhur.baseproject.App
import com.olgabakhur.baseproject.R
import com.olgabakhur.baseproject.databinding.FragmentSignInBinding
import com.olgabakhur.baseproject.presentation.base.BaseFragment
import com.olgabakhur.baseproject.presentation.extensions.collectLatestWhenStarted
import com.olgabakhur.baseproject.presentation.extensions.collectWhenStarted
import com.olgabakhur.baseproject.presentation.util.viewmodel.viewModel
import com.olgabakhur.data.util.result.Result

class SignInFragment : BaseFragment(R.layout.fragment_sign_in) {

    override val binding by viewBinding(FragmentSignInBinding::bind)
    override val viewModel: SignInViewModel by viewModel { App.appComponent.signInViewModel }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSignInButtonClickListener()
    }

    override fun setupToolbarMenu() {
        super.setupToolbarMenu()

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.clear()
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean = false
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun observeViewModel() {
        super.observeViewModel()

        /* Read from the DataStore */
        collectWhenStarted(viewModel.isUserLoggedIn()) { isLoggedIn ->
            updateUiOnLoginStatus(isLoggedIn)
        }

        /* Api call result*/
        collectLatestWhenStarted(viewModel.signInFlow) { result ->
            when (result) {
                is Result.Success -> { /* do "onSuccess" actions */
                }
                is Result.Error -> { /* do "onError" actions */
                }
            }
        }
    }

    private fun updateUiOnLoginStatus(isLoggedIn: Boolean) {
        binding.tvSignInStatus.apply {
            text = getString(
                if (isLoggedIn) {
                    R.string.sign_in_status_completed_message
                } else {
                    R.string.sign_in_status_uncompleted_message
                }
            )
            setTextColor(
                resources.getColor(
                    if (isLoggedIn) {
                        R.color.bodyText
                    } else {
                        R.color.colorError
                    }, null
                )
            )
        }

        binding.btnSignIn.text = getString(
            if (isLoggedIn) {
                R.string.general_continue
            } else {
                R.string.general_sign_in
            }
        )
    }

    private fun setupSignInButtonClickListener() {
        binding.btnSignIn.setOnClickListener {
            /* Fake implementation */
            viewModel.signInFake {
                navigate(
                    SignInFragmentDirections.actionSignInFragmentToBreakingNewsFragment()
                )
            }

            /* Replace the fake implementation with the real one. */
            /* viewModel.signIn("email@gmail.com", "0123456789") */
        }
    }
}