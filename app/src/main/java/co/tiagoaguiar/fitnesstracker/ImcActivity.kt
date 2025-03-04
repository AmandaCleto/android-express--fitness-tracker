package co.tiagoaguiar.fitnesstracker

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.hardware.input.InputManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.motion.widget.TransitionBuilder.validate
import co.tiagoaguiar.fitnesstracker.model.Calc

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

            AlertDialog.Builder(this)
                .setTitle(getString(R.string.imc_response, resultImc))
                .setMessage(imcResponseId)
                .setPositiveButton( android.R.string.ok) { dialog, which ->  // option 2: using lambda
                    // when click Ok from dialog
                }
                .setNegativeButton(R.string.save) { dialog, which ->
                    Thread {
                        val app = application as App
                        val dao = app.db.calcDao()
                        dao.insert(Calc(type = "IMC", res = resultImc))

                        //call UI items inside this method so it will be executed after the lines above
                        //if Toast was called after start(), it could have been showed before the insertion
                        runOnUiThread {
                            openListCalcActivity()
                        }
                    }.start()
                }
                .create()
                .show()

            // option 1
//            dialog.setPositiveButton(android.R.string.ok, object : DialogInterface.OnClickListener {
//                override fun onClick(dialog: DialogInterface?, which: Int) {
//                    // when click Ok from dialog
//                }
//            })

            val service = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            service.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }
    }

    private fun openListCalcActivity () {
        val intent = Intent(this@ImcActivity, ListCalcActivity::class.java)
        intent.putExtra("type", "IMC")
        startActivity(intent)
    }

    //toda atividade tem essa funcao que cria menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        Log.i("teste", "veio aqui")
        return true
    }


    //toda atividade tem essa funcao que da interatividade ao menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_search) {
            finish() //destroys the current activity
            openListCalcActivity()
        }
        return super.onOptionsItemSelected(item)
    }

    /*
    * when returning a R class, you must return a Int
    * because R class returns the reference of whatever you are returning,
    * and that reference is an int
    * */

    @StringRes //means to desveloper that this function shall return a R.string
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