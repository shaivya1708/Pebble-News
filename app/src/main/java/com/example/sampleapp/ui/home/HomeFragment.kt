package com.example.sampleapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sampleapp.adapter.NewsAdapter
import com.example.sampleapp.databinding.FragmentHomeBinding
import com.example.sampleapp.ui.ViewModelFactory
import com.google.android.material.tabs.TabLayout

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: HomeViewModel
    private lateinit var topHeadlinesAdapter: NewsAdapter
    private lateinit var forYouAdapter: NewsAdapter

    private var currentTab = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, ViewModelFactory(requireContext())).get(HomeViewModel::class.java)

        setupRecyclerViews()
        setupTabLayout()
        observeViewModel()

        viewModel.loadAllNews()
    }

    private fun setupTabLayout() {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                currentTab = tab?.position ?: 0
                updateVisibleList()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Scroll to top when reselected
                when (tab?.position) {
                    0 -> binding.rvTopHeadlines.scrollToPosition(0)
                    1 -> binding.rvForYou.scrollToPosition(0)
                }
            }
        })
    }

    private fun updateVisibleList() {
        when (currentTab) {
            0 -> {
                binding.rvTopHeadlines.visibility = View.VISIBLE
                binding.rvForYou.visibility = View.GONE
            }
            1 -> {
                binding.rvTopHeadlines.visibility = View.GONE
                binding.rvForYou.visibility = View.VISIBLE
            }
        }
    }

    private fun setupRecyclerViews() {
        // Top Headlines RecyclerView
        topHeadlinesAdapter = NewsAdapter()
        binding.rvTopHeadlines.apply {
            adapter = topHeadlinesAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        topHeadlinesAdapter.setOnItemClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(it)
            findNavController().navigate(action)
        }

        // For You RecyclerView
        forYouAdapter = NewsAdapter()
        binding.rvForYou.apply {
            adapter = forYouAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        forYouAdapter.setOnItemClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(it)
            findNavController().navigate(action)
        }
    }

    private fun observeViewModel() {
        // Top Headlines
        viewModel.topHeadlines.observe(viewLifecycleOwner) {
            topHeadlinesAdapter.submitList(it)
        }

        viewModel.isLoadingHeadlines.observe(viewLifecycleOwner) { isLoading ->
            if (currentTab == 0) {
                binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            }
        }

        // For You
        viewModel.forYouArticles.observe(viewLifecycleOwner) {
            forYouAdapter.submitList(it)
        }

        viewModel.isLoadingForYou.observe(viewLifecycleOwner) { isLoading ->
            if (currentTab == 1) {
                binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}