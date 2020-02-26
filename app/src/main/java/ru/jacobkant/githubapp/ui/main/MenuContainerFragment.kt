package ru.jacobkant.githubapp.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.frag_menu_container.*
import kotlinx.android.synthetic.main.navigation_header.view.*
import ru.jacobkant.githubapp.App
import ru.jacobkant.githubapp.R
import ru.jacobkant.githubapp.di.getViewModel
import ru.jacobkant.githubapp.ui.main.users.ParentSearchListener
import ru.jacobkant.githubapp.ui.main.users.UsersFragment
import ru.jacobkant.githubapp.utils.addTo
import java.util.concurrent.TimeUnit

class MenuContainerFragment : Fragment(R.layout.frag_menu_container) {

    private val compositeDisposable = CompositeDisposable()

    private val viewModel: MenuFragmentContainerViewModel by getViewModel(App.components.appComponent)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            childFragmentManager.beginTransaction()
                .replace(R.id.container, UsersFragment())
                .commitAllowingStateLoss()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        frag_menu_navigationView.menu.findItem(R.id.menu_item_exit).setOnMenuItemClickListener {
            frag_menu_container_drawer.closeDrawers()
            viewModel.clickLogout()
            true
        }
        viewModel.myProfileInfo.observe(viewLifecycleOwner, Observer {
            val headerView = frag_menu_navigationView.getHeaderView(0)
            headerView.user_tv_username.text = "${it.firstName} ${it.lastName}"
            headerView.user_tv_login.text = it.loginName
            Picasso.get()
                .load(it.photoUrl)
                .placeholder(R.color.image_loading_placeholder)
                .into(headerView.user_iv_avatar)
        })

        frag_menu_bottom_bar.setNavigationOnClickListener {
            if (frag_menu_container_drawer.isDrawerOpen(GravityCompat.START))
                frag_menu_container_drawer.closeDrawer(GravityCompat.START)
            else
                frag_menu_container_drawer.openDrawer(GravityCompat.START)
        }
        val searchView =
            frag_menu_bottom_bar.menu.findItem(R.id.menu_search).actionView as SearchView

        Observable.create(ObservableOnSubscribe<String> { subscriber ->
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String?): Boolean {
                    subscriber.onNext(newText!!)
                    return false
                }

                override fun onQueryTextSubmit(query: String?): Boolean {
                    subscriber.onNext(query!!)
                    return false
                }
            })
        })
            .debounce(600, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                (childFragmentManager.fragments.firstOrNull() as? ParentSearchListener)
                    ?.apply {
                        onSearchChanged(it ?: "")
                    }
            }.addTo(compositeDisposable)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
    }
}