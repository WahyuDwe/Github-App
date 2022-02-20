package com.wahyudwi.githubapp.ui.followers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.wahyudwi.githubapp.databinding.FragmentFollowersBinding
import com.wahyudwi.githubapp.ui.detail.DetailActivity
import com.wahyudwi.githubapp.ui.main.MainAdapter
import com.wahyudwi.githubapp.utils.ViewModelFactory

class FollowersFragment : Fragment() {
    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding
    private lateinit var viewModel: FollowersViewModel
    private lateinit var detailActivity: DetailActivity
    private lateinit var username: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailActivity = activity as DetailActivity
        username = detailActivity.getData()

        val adapter = MainAdapter()
        binding?.rvUserFollowers?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            this.adapter = adapter
        }

        isLoading(true)
        viewModel = obtainViewModel(context as AppCompatActivity)
        viewModel.setListFollowers(username).observe(viewLifecycleOwner) { list ->
            if (list != null && list.isNotEmpty()) {
                isLoading(false)
                isShowIllustration(false)
                adapter.setData(list)
            } else {
                isLoading(false)
                isShowIllustration(true)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun obtainViewModel(activity: AppCompatActivity): FollowersViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FollowersViewModel::class.java]
    }

    private fun isLoading(state: Boolean) {
        binding?.progressbar?.isVisible = state
        binding?.rvUserFollowers?.isGone = state
    }

    private fun isShowIllustration(state: Boolean) {
        binding?.tvNoneFollowers?.isVisible = state
        binding?.ivNoneFollowers?.isVisible = state
    }
}