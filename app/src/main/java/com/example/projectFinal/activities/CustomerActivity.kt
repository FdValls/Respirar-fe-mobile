package com.example.projectFinal.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.projectFinal.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class CustomerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer)

        setupBottomNavigationView()

    }

    private fun setupBottomNavigationView() {
        // Busco los componentes en la View generada por su id
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_customer) as NavHostFragment
        val bottomNavView = findViewById<BottomNavigationView>(R.id.bottom_nav_view_customer)

        // Relaciono mi Bottom Nav View con mi nav graph
        bottomNavView.setupWithNavController(navHostFragment.navController)

        navHostFragment.navController.addOnDestinationChangedListener { _, destination, arguments ->

            // Si mi destino es el expensasDetailFragment o fragment_edit_customer2 oculto la barra inferior. Caso contrario la muestro
            if (destination.id == R.id.expensasDetailFragment || destination.id == R.id.fragment_edit_customer2) {
                bottomNavView.visibility = View.GONE
            } else {
                bottomNavView.visibility = View.VISIBLE
            }
        }

    }

}