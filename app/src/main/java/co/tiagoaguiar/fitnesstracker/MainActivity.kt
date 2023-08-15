package co.tiagoaguiar.fitnesstracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout

class MainActivity : AppCompatActivity() {

    private lateinit var btnImc: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnImc = findViewById(R.id.btn_imc)
        btnImc.setOnClickListener {
            // NAVEGATION
            // intent is class that you use when you want to use a service from Android
            // it's very used for navigation

            //when we want to pass the class [page] -someClass- will instantiate for us, we use ::class.java, passing the reference to it.
            val intent = Intent(this, ImcActivity::class.java)
            startActivity(intent) //opens the second page
        }
    }
}