package com.example.sampleapp.ui.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.sampleapp.R
import com.example.sampleapp.databinding.FragmentDetailsBinding
import com.example.sampleapp.ui.ViewModelFactory

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: DetailsViewModel
    private val args: DetailsFragmentArgs by navArgs()
    
    private var isInitialLoad = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, ViewModelFactory(requireContext())).get(DetailsViewModel::class.java)

        val article = args.article
        
        // Display article details
        binding.tvArticleTitle.text = article.title
        binding.tvArticleAuthor.text = article.author ?: "Unknown Author"
        binding.tvArticleDate.text = article.publishedAt.toString()
        binding.tvArticleContent.text = article.content ?: article.description ?: "No content available"
        Glide.with(this).load(article.urlToImage).into(binding.ivArticleImage)

        // Show article URL and set up click to open in browser
        binding.tvArticleUrl.text = article.url
        binding.btnReadFullArticle.setOnClickListener {
            openArticleInBrowser(article.url)
        }
        binding.tvArticleUrl.setOnClickListener {
            openArticleInBrowser(article.url)
        }

        // Check initial favorite/read later status
        viewModel.checkArticleStatus(article.url)

        // Observe favorite state
        viewModel.isFavorite.observe(viewLifecycleOwner) { isFavorite ->
            updateFavoriteButton(isFavorite)
        }

        // Observe read later state
        viewModel.isReadLater.observe(viewLifecycleOwner) { isReadLater ->
            updateReadLaterButton(isReadLater)
        }
        
        // Mark initial load complete after a short delay
        binding.root.post { isInitialLoad = false }

        // Toggle favorite on click
        binding.fabFavorite.setOnClickListener {
            viewModel.toggleFavorite(article)
        }

        // Toggle read later on click
        binding.fabReadLater.setOnClickListener {
            viewModel.toggleReadLater(article)
        }
    }

    private fun updateFavoriteButton(isFavorite: Boolean) {
        if (isFavorite) {
            binding.fabFavorite.setImageResource(R.drawable.ic_favorite_filled)
            binding.fabFavorite.clearColorFilter()
            if (!isInitialLoad) {
                Toast.makeText(requireContext(), "Added to favorites", Toast.LENGTH_SHORT).show()
            }
        } else {
            binding.fabFavorite.setImageResource(R.drawable.ic_favorite_border)
            binding.fabFavorite.clearColorFilter()
            if (!isInitialLoad) {
                Toast.makeText(requireContext(), "Removed from favorites", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateReadLaterButton(isReadLater: Boolean) {
        if (isReadLater) {
            binding.fabReadLater.setColorFilter(ContextCompat.getColor(requireContext(), R.color.read_later_active))
            if (!isInitialLoad) {
                Toast.makeText(requireContext(), "Added to read later", Toast.LENGTH_SHORT).show()
            }
        } else {
            binding.fabReadLater.clearColorFilter()
            if (!isInitialLoad) {
                Toast.makeText(requireContext(), "Removed from read later", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun openArticleInBrowser(url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Unable to open link", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}