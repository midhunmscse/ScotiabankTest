package com.scotiabank.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.scotiabank.data.model.RepoEntryItem
import com.scotiabank.databinding.UserListItemBinding

class UserListAdapter(
    private val mListener: PodCastItemClickListener,
    private var userListItem: List<RepoEntryItem>,
) : RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            UserListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.viewBinding.userNameText.text = userListItem[position].name
        holder.viewBinding.userDescriptionText.text = userListItem[position].description
        holder.viewBinding.container.setOnClickListener {
            mListener.onItemSelected(userListItem[position])
        }
        }

    override fun getItemCount(): Int {
        return userListItem.size
    }

    inner class ViewHolder(val viewBinding: UserListItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

    }

    interface PodCastItemClickListener {
        fun onItemSelected(item: RepoEntryItem)
    }


}