package com.wahyudwi.githubapp.ui.followers

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
import com.wahyudwi.githubapp.databinding.FragmentFollowersBinding
import com.wahyudwi.githubapp.utils.Adapter

class FollowersFragment : Fragment() {
    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding
    private val viewModel: FollowersViewModel by viewModels()
    private lateinit var username: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        username = arguments?.getString(USER_NAME) ?: ""

        val adapter = Adapter()
        binding?.rvUserFollowers?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            this.adapter = adapter
        }

        isLoading(true)

        viewModel.getListFollowers(username).observe(viewLifecycleOwner) { list ->
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
            rvUserFollowers.isGone = state
        }
    }

    private fun isShowIllustration(state: Boolean) {
        binding?.apply {
            tvNoneFollowers.isVisible = state
            ivNoneFollowers.isVisible = state
        }
    }

    companion object {
        private const val USER_NAME = "user_name"

        fun newInstance(username: String) = FollowersFragment().apply {
            arguments = bundleOf(
                USER_NAME to username
            )
        }
    }
}