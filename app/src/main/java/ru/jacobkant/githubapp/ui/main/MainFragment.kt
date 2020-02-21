package ru.jacobkant.githubapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.frag_login.*
import ru.jacobkant.githubapp.App
import ru.jacobkant.githubapp.R
import ru.jacobkant.githubapp.di.getViewModel

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

//    private val viewModel: MainViewModel by getViewModel(App.components.appComponent)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.frag_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        main_fragment_login_button.setOnClickListener {
//            viewModel.requestVkLogin(requireActivity())
        }
    }
}
