package com.scotiabank.ui.detail

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.scotiabank.R
import com.scotiabank.data.model.RepoEntryItem
import com.scotiabank.databinding.UserRepoDetailsActivityBinding
import com.scotiabank.ui.base.ScopedActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailsActivity : ScopedActivity() {
    lateinit var dataBinding: UserRepoDetailsActivityBinding
    private lateinit var  viewModel: UserDetailViewModel
    private lateinit var mRepoUserDetails: RepoEntryItem
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mRepoUserDetails = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.extras?.getParcelable("DETAIL_VIEW", RepoEntryItem::class.java)!!
        } else {
            intent.extras?.getParcelable("DETAIL_VIEW")!!
        }
        dataBinding = DataBindingUtil.setContentView(this, R.layout.user_repo_details_activity)
        viewModel = ViewModelProvider(this)[UserDetailViewModel::class.java]

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel.setupData(mRepoUserDetails)
        viewModel.mUserData.observe(this, Observer { mItem ->
            Log.e("","")
            dataBinding.userImageView.setImageURI(mItem.owner?.avatar_url)
            dataBinding.userNameText.text = mItem.owner?.login
            dataBinding.repoDescriptionText.text = mItem.description
            dataBinding.repoFullNameText.text = mItem.full_name
            dataBinding.forksCountText.text = mItem.forks.toString()

            if(mItem.forks>5000){
                dataBinding.userNameText.setCompoundDrawablesWithIntrinsicBounds(0,
                    0,
                    R.drawable.ic_star,
                    0
                )
            }
        })


    }


    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

}
