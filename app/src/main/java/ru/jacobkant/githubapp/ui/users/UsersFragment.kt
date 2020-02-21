package ru.jacobkant.githubapp.ui.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.frag_users.*
import ru.jacobkant.githubapp.App
import ru.jacobkant.githubapp.R
import ru.jacobkant.githubapp.di.getViewModel
import ru.jacobkant.githubapp.ui.main.MainViewModel

class UsersFragment: Fragment() {

    private val viewModel: MainViewModel by getViewModel(App.components.appComponent)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.frag_users, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.myVkUser.observe(viewLifecycleOwner, Observer {
            frag_users_tv_username.text = "${it.firstName} ${it.lastName}"
            frag_users_tv_login.text = it.loginName
            Picasso.get()
                .load(it.photoUrl)
                .into(frag_users_iv_avatar)

        })
    }
}