package com.wahyudwi.githubapp.ui.following

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
import com.wahyudwi.githubapp.databinding.FragmentFollowingBinding
import com.wahyudwi.githubapp.ui.detail.DetailActivity
import com.wahyudwi.githubapp.ui.main.MainAdapter
import com.wahyudwi.githubapp.utils.ViewModelFactory

class FollowingFragment : Fragment() {
    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding
    private lateinit var viewModel: FollowingViewModel
    private lateinit var detailActivity: DetailActivity
    private lateinit var username: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailActivity = activity as DetailActivity
        username = detailActivity.getData()

        isLoading(true)
        val adapter = MainAdapter()
        binding?.rvUserFollowing?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            this.adapter = adapter
        }
        viewModel = obtainViewModel(context as AppCompatActivity)
        viewModel.setListFollowing(username).observe(viewLifecycleOwner) { list ->
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

    private fun obtainViewModel(activity: AppCompatActivity): FollowingViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FollowingViewModel::class.java]
    }

    private fun isLoading(state: Boolean) {
        binding?.apply {
            progressbar.isVisible = state
            rvUserFollowing.isGone = state
        }
    }

    private fun isShowIllustration(state: Boolean) {
        binding?.apply {
            ivNoneFollowing.isVisible = state
            tvNoneFollowing.isVisible = state
        }
    }
}