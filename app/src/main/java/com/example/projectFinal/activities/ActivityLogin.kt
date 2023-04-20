package com.example.projectFinal.activities

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projectFinal.databinding.ActivityLoginBinding
import com.example.projectFinal.entities.User
import com.example.projectFinal.utils.GoogleClientGso
import com.example.projectFinal.utils.UserSession
import com.example.projectFinal.utils.WeatherSession
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.json.JSONObject
import java.io.Serializable
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*


class ActivityLogin : AppCompatActivity(), Serializable {

    val CITY: String = "Buenos Aires,arg"
    val API: String = "442f3276441db123786da753bda41aa6" // Use your own API key

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityLoginBinding
    private lateinit var userEditText: EditText
    private lateinit var userEditPasswd: EditText
    private lateinit var email: String
    private lateinit var passwd: String
    private lateinit var person: User
    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        userEditText = binding.editTextEmail
        userEditPasswd = binding.editTextPasswod

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("430285401875-qal3lud0i9ep0jnqjlrhsr87voa3949p.apps.googleusercontent.com")

            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        GoogleClientGso.googleSignInClient = googleSignInClient

        weatherTask().execute()

        //Seteo los colores de los botones

        binding.btnContinuar.setBackgroundColor(Color.BLACK)
        binding.btnRegistrar.setBackgroundColor(Color.BLACK)

        binding.signInButton.setOnClickListener {
            signIn()
        }

        binding.btnContinuar.setOnClickListener {

            email = userEditText.text.toString()
            passwd = userEditPasswd.text.toString()
            //Agregamos validación
            when {
                email.isEmpty() || passwd.isEmpty() -> {
                    Toast.makeText(
                        this,
                        "Los campos son obligatorios, caso contrario loguearse con GOOGLE!",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
                else -> {
                    UserSession.userEmail = userEditText.text.toString()
                    person = User(userEditText.text.toString())

                    val docRef = db.collection("Usuarios").document(email)
                    docRef.get()
                        .addOnSuccessListener { document ->
                            if (document.data != null && document.data!!["passwd"].toString() == passwd) {
                                Toast.makeText(this, "Access Success", Toast.LENGTH_LONG)
                                    .show()
                                Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                                UserSession.rol = document.data!!["rol"].toString()
                                UserSession.userName = document.data!!["name"].toString()
                                UserSession.userPhoto = document.data!!["photo"].toString()
                                if (UserSession.rol == "admin") {
                                    val intent =
                                        Intent(applicationContext, GoogleSignInActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    val intent =
                                        Intent(applicationContext, CustomerActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                            } else {
                                Toast.makeText(
                                    this,
                                    "El email/passwd son incorectos o no estan registrados",
                                    Toast.LENGTH_LONG
                                )
                                    .show()
                                Log.d(TAG, "No such document")
                            }
                        }
                        .addOnFailureListener { exception ->
                            Log.d(TAG, "get failed with ", exception)
                        }
                }
            }

        }
        binding.btnRegistrar.setOnClickListener {

            email = userEditText.text.toString()
            passwd = userEditPasswd.text.toString()

            if (isValidEmail(email) && !isValidPasswd(passwd)) {

                val docRef = db.collection("Usuarios").document(email)

                docRef.get()
                    .addOnSuccessListener { document ->
                        if (document.data == null) {
                            Toast.makeText(this, "Creado con éxito", Toast.LENGTH_LONG)
                                .show()
                            person = User(email, passwd)
                            UserSession.userPhoto = person.photo
                            db.collection("Usuarios").document(email)
                                .set(person)
                                .addOnSuccessListener {
                                    Toast.makeText(this, "Creado con éxito", Toast.LENGTH_LONG)
                                        .show()
                                    val intent =
                                        Intent(applicationContext, CustomerActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                        } else {
                            Toast.makeText(
                                this,
                                "El email ya se encuentra registrado en nuestra base de datos!",
                                Toast.LENGTH_LONG
                            )
                                .show()
                            Log.d(TAG, "No such document")
                        }
                    }
                    .addOnFailureListener { exception ->
                        Log.d(TAG, "get failed with ", exception)
                    }
            } else {
                Toast.makeText(this, "Introduzca datos válidos", Toast.LENGTH_LONG)
                    .show()
            }


        }
    }

    private fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPasswd(passwd: String): Boolean {
        return passwd.isBlank() || passwd.isEmpty()
    }

    private fun logout() {
        googleSignInClient.signOut()
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    companion object {
        const val RC_SIGN_IN = 1001
        const val EXTRA_NAME = "EXTRA NAME"
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                Log.d(ContentValues.TAG, "firebaseAuthWithGoogle " + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Log.w(ContentValues.TAG, "Google sign in failed ", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        Log.d(ContentValues.TAG, "CREDENTIAL " + credential.provider)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(ContentValues.TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Log.d(ContentValues.TAG, "signInWithCredential:failed", task.exception)
                    updateUI(null)
                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {

            val docRef = db.collection("Usuarios").document(user.email.toString())
            docRef.get()
                .addOnSuccessListener { document ->
                    if (document.data != null) {
                        Toast.makeText(this, "Access Success", Toast.LENGTH_LONG)
                            .show()
                        Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                        UserSession.rol = document.data!!["rol"].toString()
                        UserSession.userName = document.data!!["name"].toString()
                        UserSession.userPhoto = document.data!!["photo"].toString()
                    } else {
                        Toast.makeText(
                            this,
                            "El email no esta registrado, se creo usuario default!",
                            Toast.LENGTH_LONG
                        )
                            .show()
                        Log.d(TAG, "No such document")
                        person = User(user.email.toString())
                        person.name = user.displayName.toString()
                        person.email = user.email.toString()
                        person.photo = user.photoUrl.toString()
                        person.phone = user.phoneNumber.toString()
                        docRef.set(person)
                    }
                    if (UserSession.rol == "admin") {
                        val intent = Intent(applicationContext, GoogleSignInActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        val intent = Intent(applicationContext, CustomerActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }

        }
    }

    //Api para consultar la hora y el clima
    inner class weatherTask() : AsyncTask<String, Void, String>() {

        override fun doInBackground(vararg params: String?): String? {
            var response: String?
            try {
                response =
                    URL("https://api.openweathermap.org/data/2.5/weather?q=$CITY&units=metric&appid=$API").readText(
                        Charsets.UTF_8
                    )
            } catch (e: Exception) {
                response = null
            }
            return response
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            /* Extracting JSON returns from the API */
            val jsonObj = JSONObject(result)
            val main = jsonObj.getJSONObject("main")
            WeatherSession.temperatura = main.getString("temp")
            val sys = jsonObj.getJSONObject("sys")
            val updatedAt: Long = jsonObj.getLong("dt")
            WeatherSession.hora = SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(
                Date(updatedAt * 1000)
            )
            WeatherSession.pais = jsonObj.getString("name") + ", " + sys.getString("country")
            WeatherSession.temperatura = main.getString("temp") + "°C"

        }
    }
}




