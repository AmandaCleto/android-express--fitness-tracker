package co.tiagoaguiar.fitnesstracker

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.constraintlayout.motion.widget.TransitionBuilder.validate

class ImcActivity : AppCompatActivity() {
    private lateinit var editWeight: EditText
    private lateinit var editHeight: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imc)

        editWeight = findViewById(R.id.edit_imc_weight)
        editHeight = findViewById(R.id.edit_imc_height)

        var btn: Button = findViewById(R.id.btn_imc_send);
        btn.setOnClickListener {
            if (!validate()) {
                Toast.makeText(this, R.string.fields_message, Toast.LENGTH_SHORT).show()
                return@setOnClickListener //this means that this bloc of code will be stopped
            }

            val weight = editWeight.text.toString().toInt();
            val height = editHeight.text.toString().toInt();

            val resultImc = calculateImc(weight, height)
            val imcResponseId = imcResponse(resultImc);

            Toast.makeText(this, imcResponseId, Toast.LENGTH_SHORT).show()
        }
    }

    /*
    * when returning a R class, you must return a Int
    * because R class returns the reference of whatever you are returning,
    * and that reference is an int
    * */

    @StringRes //means to developer that this function shall return a R.string
    private fun imcResponse(imc: Double): Int {
       return when {
           imc < 15.0 -> R.string.imc_severely_low_weight
           imc < 16.0 -> R.string.imc_very_low_weight
           imc < 18.5 -> R.string.imc_low_weight
           imc < 25.0 -> R.string.normal
           imc < 30.0 -> R.string.imc_high_weight
           imc < 35.0 -> R.string.imc_so_high_weight
           imc < 40.0 -> R.string.imc_severely_high_weight
           else -> R.string.imc_extreme_weight
       }
    }

    private fun validate(): Boolean {
        return (editWeight.text.toString().isNotEmpty() && editHeight.text.toString().isNotEmpty()
                && !editWeight.text.toString().startsWith("0") && !editHeight.text.toString()
            .startsWith("0")
                )
    }

    private fun calculateImc(weight: Int, height: Int): Double {
        return weight / ((height / 100.0) * (height / 100.0))

    }
}