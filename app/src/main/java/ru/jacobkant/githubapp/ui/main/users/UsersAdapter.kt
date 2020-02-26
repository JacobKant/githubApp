package ru.jacobkant.githubapp.ui.main.users

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.github.UsersQuery
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_progress.view.*
import kotlinx.android.synthetic.main.item_user.view.*
import ru.jacobkant.githubapp.R

enum class State {
    DONE, LOADING
}

private const val FOOTER_VIEW_TYPE = 1

class UsersAdapter(
    var onLoadMore: (() -> Unit)? = null,
    private val prefetchDistance: Int = 10
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var isFullData = false

    var state = State.DONE
        set(value) {
            if (field == value) return
            val oldValue = field
            field = value
            if (value != State.DONE) {
                if (oldValue == State.DONE)
                    notifyItemInserted(itemCount)
                else
                    notifyItemChanged(differ.currentList.size)
            } else notifyItemRemoved(itemCount)
        }

    private val differ = AsyncListDiffer<UsersQuery.AsUser>(
        this,
        object : DiffUtil.ItemCallback<UsersQuery.AsUser>() {
            override fun areItemsTheSame(
                oldItem: UsersQuery.AsUser,
                newItem: UsersQuery.AsUser
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: UsersQuery.AsUser,
                newItem: UsersQuery.AsUser
            ): Boolean {
                return oldItem == newItem
            }
        })

    fun submitList(newList: List<UsersQuery.AsUser>) = differ.submitList(newList)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == FOOTER_VIEW_TYPE) FooterViewHolder.create(parent)
        else {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
            UserVH(view)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == differ.currentList.size) FOOTER_VIEW_TYPE
        else super.getItemViewType(position)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size + if (hasFooter()) 1 else 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is FooterViewHolder) holder.bind(state)
        else {
            if (!isFullData && position + prefetchDistance == differ.currentList.size) onLoadMore?.invoke()
            val item = differ.currentList[position]
            val userVH = holder as UserVH
            userVH.bind(item)
        }
    }

    private fun hasFooter(): Boolean {
        return state == State.LOADING
    }

    class UserVH(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        fun bind(user: UsersQuery.AsUser) {
            Picasso.get().load(user.avatarUrl.toString())
                .placeholder(R.color.image_loading_placeholder)
                .into(containerView.item_user_avatar)
            containerView.item_user_name.text = user.name
            containerView.item_user_email.text = user.email
        }
    }

    class FooterViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        fun bind(status: State) {
            containerView.item_footer_pb.isVisible = status != State.DONE
        }

        companion object {
            fun create(parent: ViewGroup): FooterViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_progress, parent, false)
                return FooterViewHolder(view)
            }
        }
    }
}