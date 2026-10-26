package com.example.lab1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.lab1.databinding.ActivityMainBinding
import com.example.lab1.databinding.NavHeaderBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("TENANT_LIFECYCLE", "MainActivity: onCreate")

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. Use our Toolbar as the App Bar
        setSupportActionBar(binding.toolbar)

        // 2. Get the NavController from the NavHostFragment in activity_main.xml
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // 3. Top-level destinations show the drawer icon (hamburger) instead of a back arrow
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.dashboardFragment, R.id.addTenantFragment, R.id.viewTenantsFragment),
            binding.drawerLayout
        )

        // 4. Title + hamburger/back arrow now follow each destination's android:label
        setupActionBarWithNavController(navController, appBarConfiguration)

        // 5. Drawer items navigate automatically because their IDs match the nav graph IDs
        binding.navView.setupWithNavController(navController)

        // 6. Show the email sent from LoginActivity in the drawer header
        val email = intent.getStringExtra("EMAIL")
        val headerView = binding.navView.getHeaderView(0)
        if (headerView != null) {
            // bind() attaches to a view that ALREADY exists (inflate() would create a new one)
            val headerBinding = NavHeaderBinding.bind(headerView)
            headerBinding.headerEmailTextView.text = email ?: "Signed in"
        }
    }

    // "Up" = the arrow/hamburger in the App Bar
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    // ── OPTIONS MENU (the three dots) ───────────────────────────
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                val intent = Intent(this, LoginActivity::class.java)
                // Wipe the whole task so Back after logout can't re-enter the app
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                true
            }
            // If the menu item's ID equals a destination ID, this navigates for us
            else -> item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
        }
    }

    // ── LIFECYCLE LOGS ──────────────────────────────────────────
    override fun onStart() { super.onStart(); Log.d("TENANT_LIFECYCLE", "MainActivity: onStart") }
    override fun onResume() { super.onResume(); Log.d("TENANT_LIFECYCLE", "MainActivity: onResume") }
    override fun onPause() { super.onPause(); Log.d("TENANT_LIFECYCLE", "MainActivity: onPause") }
    override fun onStop() { super.onStop(); Log.d("TENANT_LIFECYCLE", "MainActivity: onStop") }
    override fun onRestart() { super.onRestart(); Log.d("TENANT_LIFECYCLE", "MainActivity: onRestart") }
    override fun onDestroy() { super.onDestroy(); Log.d("TENANT_LIFECYCLE", "MainActivity: onDestroy") }
}