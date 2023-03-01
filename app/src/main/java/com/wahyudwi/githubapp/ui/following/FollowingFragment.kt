package com.wahyudwi.githubapp.ui.following

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.wahyudwi.githubapp.databinding.FragmentFollowingBinding
import com.wahyudwi.githubapp.utils.Adapter

class FollowingFragment : Fragment() {
    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding
    private val viewModel: FollowingViewModel by viewModels()
    private lateinit var username: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        username = arguments?.getString(USER_NAME) ?: ""

        isLoading(true)
        val adapter = Adapter()
        binding?.rvUserFollowing?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            this.adapter = adapter
        }

        viewModel.getListFollowing(username).observe(viewLifecycleOwner) { list ->
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

    companion object {
        private const val USER_NAME = "user_name"

        fun instance(username: String) = FollowingFragment().apply {
            arguments = bundleOf(
                USER_NAME to username
            )
        }

    }
}