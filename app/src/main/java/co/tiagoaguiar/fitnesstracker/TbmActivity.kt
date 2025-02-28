package co.tiagoaguiar.fitnesstracker

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import co.tiagoaguiar.fitnesstracker.model.Calc

class TbmActivity : AppCompatActivity() {
    lateinit var lifestyle : AutoCompleteTextView

    private lateinit var editWeight: EditText
    private lateinit var editHeight: EditText
    private lateinit var editAge: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tmb)

        editWeight = findViewById(R.id.edit_tbm_weight)
        editHeight = findViewById(R.id.edit_tbm_height)
        editAge = findViewById(R.id.edit_tbm_age)

        lifestyle = findViewById(R.id.auto_lifestyle)
        val items = resources.getStringArray(R.array.tbm_lifestyle);
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        // seta por default um valor
        lifestyle.setText(items.first())

        lifestyle.setAdapter(adapter)

        var btn: Button = findViewById(R.id.btn_tmb_send);

        btn.setOnClickListener {
            if(!validate()) {
                Toast.makeText(this, R.string.fields_message, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }



            val weight = editWeight.text.toString().toInt();
            val height = editHeight.text.toString().toInt();
            val age = editAge.text.toString().toInt();

            val resultTmb = calculateTmb(weight, height, age)
            val response = tbmRequest(resultTmb);

            AlertDialog.Builder(this)
                .setMessage(getString(R.string.tmb_response, response))
                .setPositiveButton( android.R.string.ok) { dialog, which ->
                    // when click Ok from dialog
                }
                .setNegativeButton(R.string.save) { dialog, which ->
                    Thread {
                        val app = application as App
                        val dao = app.db.calcDao()
                        dao.insert(Calc(type = "TMB", res = resultTmb))

                        runOnUiThread {
                            openListCalcActivity()
                        }
                    }.start()
                }
                .create()
                .show()
        }
    }

    private fun openListCalcActivity () {
        val intent = Intent(this@TbmActivity, ListCalcActivity::class.java)
        intent.putExtra("type", "TMB")
        startActivity(intent)
    }

    //toda atividade tem essa funcao que cria menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

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

    private fun tbmRequest(tbm: Double) : Double {
        val items = resources.getStringArray(R.array.tbm_lifestyle);
        return when {
            lifestyle.text.toString() == items[0] -> tbm * 1.2;
            lifestyle.text.toString() == items[1] -> tbm * 1.375;
            lifestyle.text.toString() == items[2] -> tbm * 1.55;
            lifestyle.text.toString() == items[3] -> tbm * 1.725;
            lifestyle.text.toString() == items[4] -> tbm * 1.9;
            else -> 0.0;
        }
    }

    private fun calculateTmb(weight: Int, height: Int, age: Int) : Double {
        return 66 + (13.8 * weight) + (5 * height) - (6.8 * age)
    }

    private fun validate(): Boolean {
        return (editWeight.text.toString().isNotEmpty() && editAge.text.toString().isNotEmpty() && editHeight.text.toString().isNotEmpty()
                && !editWeight.text.toString().startsWith("0") && !editAge.text.toString()
            .startsWith("0") && !editHeight.text.toString()
            .startsWith("0")
                )
    }

}