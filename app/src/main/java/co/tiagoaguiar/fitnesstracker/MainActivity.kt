package co.tiagoaguiar.fitnesstracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

//    private lateinit var btnImc: LinearLayout

    // 2
    private lateinit var rvMainView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //How to render a List
        // 1- create a XML of a view to be render multiple times (e.g main_item)
        // 2- indicate where the XML will be rendered by creating a RecyclerView (must be above the XML item)
        // 3- inform android system that the XML item must be inside RecyclerView
        //  3.1 - create adapter class to inflate the item and tell android how many times the item needs to be render
        //  3.2 - create layout manager that will configure the way the items will be displayed


        // 3.1
        val adapter = MainAdapter()

        // 2
        rvMainView = findViewById(R.id.rv_main)

        // 3.2
        rvMainView.adapter = adapter
        rvMainView.layoutManager = LinearLayoutManager(this)



        // ADD functionality to the one button instantiated at screen
//        btnImc = findViewById(R.id.btn_imc)
//        btnImc.setOnClickListener {
//            // NAVEGATION
//            // intent is class that you use when you want to use a service from Android
//            // it's very used for navigation
//
//            //when we want to pass the class [page] -someClass- will instantiate for us, we use ::class.java, passing the reference to it.
//            val intent = Intent(this, ImcActivity::class.java)
//            startActivity(intent) //opens the second page
//        }
    }

    private inner class MainAdapter : RecyclerView.Adapter<MainViewHolder>() {
        // 1 - Which is the XML to be created (item)
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
            val viewItem = layoutInflater.inflate(R.layout.main_item, parent, false)
            return MainViewHolder(viewItem)
        }

        // 2 - Will run everytime the screen be rolled up/down OR everytime the item's content be changed
        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {}

        // 3 - Where to inform how many items must be displayed
        override fun getItemCount(): Int {
            return 15
        }

    }

    // is the item's class itself !
    private class MainViewHolder(view : View) : RecyclerView.ViewHolder(view) {}
}


// Why the name is Recycler View?
// Because everytime you scroll it down/up, the recycler view will get the memory allocated of the cell
// not being shown and will transfer to a new cell that will appear at bottom/top,
// so it recycles the memory being used, only by changing the content inside it.