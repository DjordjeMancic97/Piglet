package com.djordjemancic.piglet.ui.authentication

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import com.djordjemancic.piglet.R
import com.djordjemancic.piglet.databinding.FragmentSignInBinding
import com.djordjemancic.piglet.other.Resource

import com.djordjemancic.piglet.ui.home.HomeActivity
import com.google.android.material.transition.MaterialSharedAxis
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SignInFragment : Fragment() {
    private lateinit var binding: FragmentSignInBinding
    private val viewModel: AuthenticationViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.Z,true)
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Z,false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.dontHaveAccountButton.setOnClickListener {
            parentFragmentManager.commit {
                replace(R.id.authenticationFragmentContainer, SignUpFragment())
                addToBackStack(null)
            }
        }

        binding.signInButton.setOnClickListener {
            viewModel.signInUser(binding.signInEmailField.editText?.text.toString(), binding.signInPasswordField.editText?.text.toString())
        }

        lifecycleScope.launch {
            viewModel.userState.collectLatest {
                if(it.status == Resource.Status.SUCCESS) {
                    startActivity(Intent(activity, HomeActivity::class.java))
                    requireActivity().finish()
                }
            }
        }
    }
}