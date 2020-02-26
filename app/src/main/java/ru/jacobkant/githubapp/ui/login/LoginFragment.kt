package ru.jacobkant.githubapp.ui.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKScope
import kotlinx.android.synthetic.main.frag_login.*
import ru.jacobkant.githubapp.App
import ru.jacobkant.githubapp.R
import ru.jacobkant.githubapp.di.getViewModel

class LoginFragment : Fragment(R.layout.frag_login) {

    private val viewModel: LoginViewModel by getViewModel(App.components.appComponent)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        main_fragment_login_button.setOnClickListener {
            viewModel.clickVkLogin()
        }
        viewModel.loginVkSideEffect.observe(viewLifecycleOwner, Observer {
            VK.login(requireActivity(), listOf(VKScope.STATUS))
        })
    }
}
