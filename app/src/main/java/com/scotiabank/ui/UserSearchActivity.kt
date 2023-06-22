package com.scotiabank.ui

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.scotiabank.R
import com.scotiabank.adapter.UserListAdapter
import com.scotiabank.data.model.RepoEntryItem
import com.scotiabank.ui.base.ScopedActivity
import dagger.hilt.android.AndroidEntryPoint
import com.scotiabank.databinding.UserSearchLayoutBinding
import com.scotiabank.ui.detail.UserDetailsActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

@AndroidEntryPoint
class UserSearchActivity : ScopedActivity(), UserListAdapter.PodCastItemClickListener {
    lateinit var dataBinding: UserSearchLayoutBinding
    private var mAdapter: UserListAdapter? = null
    private val viewModel: UserSearchViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.user_search_layout)

        dataBinding.searchButton.setOnClickListener {
//            "octocat"
            if(!TextUtils.isEmpty(dataBinding.editUserSearch.text.toString())){
                viewModel.fetchUserData(dataBinding.editUserSearch.text.toString())
            }else{
                Toast.makeText(this, getString(R.string.enter_a_valid_user_id), Toast.LENGTH_LONG).show()
            }
        }

    }

    override fun bindUI() = launch(Dispatchers.Main) {
        viewModel.userData.observe(this@UserSearchActivity) { mData ->

            if(mData.isNotEmpty()){
                // When data is available recyclerView will be visible and userSearchEmptyText will invisible
                dataBinding.recyclerView.visibility = View.VISIBLE
                dataBinding.userSearchEmptyText.visibility = View.GONE
                dataBinding.userSearchEmptyText.text = getString(R.string.search_github_user)
                dataBinding.recyclerView.apply {
                    mAdapter = UserListAdapter(this@UserSearchActivity,mData)
                    adapter = mAdapter
                    layoutManager =
                        LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
                }
            }else{
                // When data is not available recyclerView will be in visible and userSearchEmptyText will visible
                dataBinding.recyclerView.visibility = View.GONE
                dataBinding.userSearchEmptyText.visibility = View.VISIBLE
                dataBinding.userSearchEmptyText.text = getString(R.string.user_not_found)
            }

        }

        viewModel.errorData.observe(this@UserSearchActivity) { mError ->

                dataBinding.recyclerView.visibility = View.GONE
                dataBinding.userSearchEmptyText.visibility = View.VISIBLE
                dataBinding.userSearchEmptyText.text = mError


        }
    }

    override fun onItemSelected(item: RepoEntryItem) {
        val mIntent = Intent(this, UserDetailsActivity::class.java)
        mIntent.putExtra("DETAIL_VIEW", item)
        val options = ActivityOptions.makeSceneTransitionAnimation(this, dataBinding.searchButton, "userImageView")
        startActivity(mIntent,options.toBundle())
    }
}
