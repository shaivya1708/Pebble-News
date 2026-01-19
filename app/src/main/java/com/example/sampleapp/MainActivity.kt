package com.example.sampleapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.sampleapp.databinding.ActivityMainBinding
import com.example.sampleapp.ui.settings.UserSettingsDialogFragment

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    // Fragments where menu should be hidden (login/register)
    private val authFragments = setOf(R.id.loginFragment, R.id.registerFragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        
        // Define top-level destinations (no back button shown)
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.loginFragment, R.id.homeFragment)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

        // Listen for destination changes to show/hide menu
        navController.addOnDestinationChangedListener { _, destination, _ ->
            invalidateOptionsMenu()
            // Hide toolbar on auth screens
            if (destination.id in authFragments) {
                supportActionBar?.hide()
            } else {
                supportActionBar?.show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        val currentDestination = navController.currentDestination?.id
        
        // Don't show menu on auth screens
        if (currentDestination in authFragments) {
            return false
        }
        
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return when (item.itemId) {
            R.id.action_search -> {
                navController.navigate(R.id.searchFragment)
                true
            }
            R.id.action_settings -> {
                val dialog = UserSettingsDialogFragment()
                dialog.show(supportFragmentManager, "UserSettingsDialog")
                true
            }
            R.id.action_favorites -> {
                navController.navigate(R.id.favoritesFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}