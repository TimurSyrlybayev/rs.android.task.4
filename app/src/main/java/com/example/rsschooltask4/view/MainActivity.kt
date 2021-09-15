package com.example.rsschooltask4.view

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.preference.PreferenceManager
import com.example.rsschooltask4.R
import com.example.rsschooltask4.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val listener =
        SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
            when (key) {
                "cursor-room_switch" -> setToolbarSubtitle(sharedPreferences)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        setToolbarSubtitle(sharedPrefs)
        sharedPrefs!!.registerOnSharedPreferenceChangeListener(listener)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val menuIcon = menu!!.findItem(R.id.icon_sort)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            menuIcon.isVisible =
                (destination.id == R.id.mainScreenFragment)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.icon_sort ->
                navController.navigate(R.id.action_mainScreenFragment_to_sortSettingScreenFragment)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun setToolbarSubtitle(prefs: SharedPreferences?) {
        binding.toolbar.subtitle = getString(
            if (prefs!!.getBoolean("cursor-room_switch", false))
                R.string.database_room_scheme
            else
                R.string.database_cursors_scheme
        )
    }
}