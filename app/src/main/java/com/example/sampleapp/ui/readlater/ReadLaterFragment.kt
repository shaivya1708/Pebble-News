package com.example.sampleapp.ui.readlater

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sampleapp.adapter.NewsAdapter
import com.example.sampleapp.databinding.FragmentReadLaterBinding
import com.example.sampleapp.ui.ViewModelFactory

class ReadLaterFragment : Fragment() {

    private var _binding: FragmentReadLaterBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ReadLaterViewModel
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReadLaterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, ViewModelFactory(requireContext())).get(ReadLaterViewModel::class.java)

        setupRecyclerView()
        observeViewModel()

        viewModel.getReadLaterArticles()
    }

    override fun onResume() {
        super.onResume()
        // Refresh list when returning from details (in case read later was removed)
        viewModel.getReadLaterArticles()
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        binding.rvReadLater.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        newsAdapter.setOnItemClickListener { article ->
            val action = ReadLaterFragmentDirections.actionReadLaterFragmentToDetailsFragment(article)
            findNavController().navigate(action)
        }
    }

    private fun observeViewModel() {
        viewModel.articles.observe(viewLifecycleOwner) {
            newsAdapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}