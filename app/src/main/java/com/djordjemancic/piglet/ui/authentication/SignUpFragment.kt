package com.djordjemancic.piglet.ui.authentication

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.djordjemancic.piglet.databinding.FragmentSignUpBinding
import com.djordjemancic.piglet.other.Resource
import com.djordjemancic.piglet.ui.home.HomeActivity
import com.google.android.material.transition.MaterialSharedAxis
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    private val viewModel: AuthenticationViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z,true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Z,false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.signUpButton.setOnClickListener {
            viewModel.signUpUser(
               email= binding.signUpEmailField.editText?.text.toString(),
               password = binding.signUpPasswordField.editText?.text.toString(),
               firstName = binding.signUpFirstNameField.editText?.text.toString(),
               lastName = binding.signUpLastNameField.editText?.text.toString()
            )
        }

        lifecycleScope.launch {
            viewModel.userState.collect {
                println("sign up coroutine ${it.status}")
                if(it.status == Resource.Status.SUCCESS) {
                    startActivity(Intent(activity, HomeActivity::class.java))
                   requireActivity().finish()
                }
            }
        }
    }

}