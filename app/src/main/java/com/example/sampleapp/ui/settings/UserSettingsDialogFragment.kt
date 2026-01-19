package com.example.sampleapp.ui.settings

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.CheckBox
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.example.sampleapp.R
import com.example.sampleapp.data.LocalStorageManager
import com.example.sampleapp.data.NewsCategory
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class UserSettingsDialogFragment : DialogFragment() {

    private lateinit var localStorageManager: LocalStorageManager

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        localStorageManager = LocalStorageManager.getInstance(requireContext())

        val view = LayoutInflater.from(context).inflate(R.layout.dialog_user_settings, null)

        // User email
        val tvUserEmail = view.findViewById<TextView>(R.id.tv_user_email)
        tvUserEmail.text = localStorageManager.getCurrentUserEmail() ?: "Unknown"

        // Checkboxes
        val cbTechnology = view.findViewById<CheckBox>(R.id.cb_technology)
        val cbBusiness = view.findViewById<CheckBox>(R.id.cb_business)
        val cbWorld = view.findViewById<CheckBox>(R.id.cb_world)
        val cbEconomy = view.findViewById<CheckBox>(R.id.cb_economy)

        // Load saved preferences
        val savedPreferences = localStorageManager.getUserPreferences()
        cbTechnology.isChecked = savedPreferences.contains(NewsCategory.TECHNOLOGY)
        cbBusiness.isChecked = savedPreferences.contains(NewsCategory.BUSINESS)
        cbWorld.isChecked = savedPreferences.contains(NewsCategory.WORLD)
        cbEconomy.isChecked = savedPreferences.contains(NewsCategory.ECONOMY)

        // Read Later button
        val btnReadLater = view.findViewById<MaterialButton>(R.id.btn_read_later)
        btnReadLater.setOnClickListener {
            dismiss()
            findNavController().navigate(R.id.readLaterFragment)
        }

        // Logout button
        val btnLogout = view.findViewById<MaterialButton>(R.id.btn_logout)
        btnLogout.setOnClickListener {
            localStorageManager.logoutUser()
            dismiss()
            // Navigate to login and clear back stack
            findNavController().navigate(R.id.loginFragment, null, 
                androidx.navigation.NavOptions.Builder()
                    .setPopUpTo(R.id.nav_graph, true)
                    .build()
            )
        }

        return MaterialAlertDialogBuilder(requireContext())
            .setTitle("User Settings")
            .setView(view)
            .setPositiveButton("Save") { _, _ ->
                // Save preferences
                val selectedCategories = mutableSetOf<NewsCategory>()
                if (cbTechnology.isChecked) selectedCategories.add(NewsCategory.TECHNOLOGY)
                if (cbBusiness.isChecked) selectedCategories.add(NewsCategory.BUSINESS)
                if (cbWorld.isChecked) selectedCategories.add(NewsCategory.WORLD)
                if (cbEconomy.isChecked) selectedCategories.add(NewsCategory.ECONOMY)
                
                localStorageManager.saveUserPreferences(selectedCategories)
            }
            .setNegativeButton("Cancel", null)
            .create()
    }
}
