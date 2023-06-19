package com.example.projectFinal.activities

import android.os.Bundle
import android.view.Menu
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.projectFinal.R
import com.example.projectFinal.data.GlobalVariables
import com.example.projectFinal.databinding.ActivityNavBinding
import com.example.projectFinal.endPoints.RequestUsers.RequestReadInfoUser
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import org.json.JSONObject

class NavActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityNavBinding
    private lateinit var image: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNavBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarNav.toolbar)

//        binding.appBarNav.fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_nav)

        // SE FIJA QUE ROL TIENE Y EN BASE A ESO LE DA ACCESOS A LOS DISTINTOS MENUS
        if (!isAdmin()){
            ocultarItemsMenu()
        }

        // SETEA EL USUARIO Y EL EMAIL EN EL NAV_HEADER
//        setDataUser()

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
//                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_organization, R.id.nav_administration,
//                R.id.nav_notification, R.id.nav_users
//                SE DEJO SOLO EL NAV_HOME PARA QUE TENGA EL MENU HAMBURGUESA, DESPUES TODOS VUELVEN CON LA FLECHA
                R.id.nav_home
            ), drawerLayout
        )


        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    fun ocultarItemsMenu() {
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        val menu = navigationView.menu

//        val navAdministration = menu.findItem(R.id.nav_administration)
//        navAdministration.isVisible = false

        val navUsers = menu.findItem(R.id.nav_users)
        navUsers.isVisible = false
    }

    fun isAdmin() : Boolean {
        val userDataJson = JSONObject(GlobalVariables.getInstance().userData)
        val userObject = userDataJson.getJSONObject("User")
        val admin = userObject.getBoolean("admin")

        return admin
    }

    override fun onStart() {
        super.onStart()

        lifecycleScope.launch {

            val userDataJson = JSONObject(GlobalVariables.getInstance().userData)

            val userObject = userDataJson.getJSONObject("User")
            val username = userObject.getString("username")
            val email = userObject.getString("email")
            val id = userObject.getString("id")

            val requestInfo = RequestReadInfoUser.sendRequest(id)
            image = requestInfo?.get("image")?.asString.toString()
            image = getImage(image)

            getUserData(username, email, image)
        }
    }
    fun getUserData(username : String, email : String, image : String ) {
            val json = JSONObject()

        json.put("username", username)
        json.put("email", email)
        json.put("image", image)

        setDataUser(json)
    }

    fun getImage(image : String) : String {
        return image
    }

    fun setDataUser(json : JSONObject) {
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        val navHeaderView = navigationView.getHeaderView(0)

        val textUserName = navHeaderView.findViewById<TextView>(R.id.textUserName)
        textUserName.text = json.getString("username")

        val textEmail = navHeaderView.findViewById<TextView>(R.id.textEmail)
        textEmail.text = json.getString("email")

        val ImageViewNav = navHeaderView.findViewById<ImageView>(R.id.imageView)
        val image = json.getString("image")

        if (image != "default") {
            val imageUrl = GlobalVariables.getInstance().url +"img/users/" + image
            Picasso.get()
                .load(imageUrl)
                .resize(200, 200)
                .into(ImageViewNav)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.nav, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_nav)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}