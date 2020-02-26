package ru.jacobkant.githubapp.ui.main.users

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.frag_users.*
import ru.jacobkant.githubapp.App
import ru.jacobkant.githubapp.R
import ru.jacobkant.githubapp.di.getViewModel

interface ParentSearchListener {
    fun onSearchChanged(query: String)
}

class UsersFragment : Fragment(R.layout.frag_users), ParentSearchListener {

    private val viewModel: UsersViewModel by getViewModel(App.components.appComponent)

    private val adapter: UsersAdapter = UsersAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        frag_users_recycler.layoutManager = LinearLayoutManager(context)
        frag_users_recycler.adapter = adapter

        adapter.onLoadMore = {
            viewModel.onLoadMore()
        }

        frag_users_srl.setOnRefreshListener {
            viewModel.onSwipeRefresh()
        }

        viewModel.paginatorLiveData.observe(viewLifecycleOwner, Observer { state ->
            adapter.submitList(state.data)
            val isEmpty = state.data.isEmpty()
            recycler_placeholder_container.isVisible = isEmpty
            frag_users_recycler.isVisible = !isEmpty


            when (state.status) {
                StateStatus.Empty -> {
                    adapter.state = State.DONE
                    adapter.isFullData = false
                    frag_users_srl.isRefreshing = false
                    showEmptyPlaceholder(null)
                    adapter.submitList(state.data)
                }
                StateStatus.EmptyProgress -> {
                    adapter.state = State.DONE
                    adapter.isFullData = false
                    frag_users_srl.isRefreshing = false
                    showEmptyLoading()
                }
                StateStatus.NewPageProgress -> {
                    adapter.state = State.LOADING
                    adapter.isFullData = false
                    frag_users_srl.isRefreshing = false
                    showList()
                }
                StateStatus.Refreshing -> {
                    frag_users_srl.isRefreshing = true
                }
                StateStatus.Data -> {
                    adapter.state = State.DONE
                    adapter.isFullData = false
                    adapter.submitList(state.data)
                    showList()

                    if (state.lastError != null && state.data.isNotEmpty()) {
                        showNextPageError()
                    }

                    frag_users_srl.isRefreshing = false
                }
                StateStatus.EmptyError -> {
                    adapter.state = State.DONE
                    adapter.isFullData = false
                    adapter.submitList(state.data)
                    showEmptyPlaceholder(getString(R.string.list_loading_error_message))
                    frag_users_srl.isRefreshing = false
                }
                StateStatus.FullDataLoaded -> {
                    adapter.state = State.DONE
                    adapter.isFullData = true
                    adapter.submitList(state.data)
                    if (state.data.isEmpty()) {
                        showEmptyPlaceholder(getString(R.string.search_empty_results))
                    } else {
                        showList()
                    }
                    frag_users_srl.isRefreshing = false
                }
            }
        })
    }

    private fun showNextPageError() {
        Toast.makeText(requireContext(), getString(R.string.list_loading_error_message), Toast.LENGTH_LONG).show()
    }

    private fun showEmptyLoading() {
        recycler_progress_container.isVisible = true
        recycler_placeholder_container.isVisible = false
        frag_users_recycler.isVisible = false
    }

    private fun showList() {
        recycler_progress_container.isVisible = false
        recycler_placeholder_container.isVisible = false
        frag_users_recycler.isVisible = true
    }

    private fun showEmptyPlaceholder(errorText: String?) {
        recycler_placeholder_container.isVisible = true
        recycler_progress_container.isVisible = false
        frag_users_recycler.isVisible = false
        if (errorText != null) {
            recycler_placeholder_message.text = errorText
        } else {
            recycler_placeholder_message.setText(R.string.users_empty_placeholder)
        }
    }

    override fun onSearchChanged(query: String) {
        viewModel.onChangeQuery(query)
    }


}