package co.tiagoaguiar.fitnesstracker

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

//    private lateinit var btnImc: LinearLayout

    // 2
    private lateinit var rvMainView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //How to create dynamic data structure to pass to a XML
        //instantiate a list of mainItem, and fill the data class with your arguments
        //after that, pass mainItems to the adapter
        val mainItems = mutableListOf<MainItem>()
        mainItems.add(
            MainItem(
                id = 1,
                drawableId = R.drawable.ic_baseline_wb_sunny_24,
                textStringId = R.string.label_imc,
                color = Color.GREEN
            )
        )
        mainItems.add(
            MainItem(
                id = 2,
                drawableId = R.drawable.baseline_brightness_3_24,
                textStringId = R.string.label_imc,
                color = Color.RED
            )
        )



        //How to render a List
        // 1- create a XML of a view to be render multiple times (e.g main_item)
        // 2- indicate where the XML will be rendered by creating a RecyclerView (must be above the XML item)
        // 3- inform android system that the XML item must be inside RecyclerView
        //  3.1 - create adapter class to inflate the item and tell android how many times the item needs to be render
        //  3.2 - create layout manager that will configure the way the items will be displayed


        // 3.1
        val adapter = MainAdapter(mainItems)

        // 2
        rvMainView = findViewById(R.id.rv_main)

        // 3.2
        rvMainView.adapter = adapter
        rvMainView.layoutManager = GridLayoutManager(this, 2)



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

    private inner class MainAdapter(private val mainItems: List<MainItem>) : RecyclerView.Adapter<MainViewHolder>() {
        // 1 - Which is the XML to be created (item)
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
            val viewItem = layoutInflater.inflate(R.layout.main_item, parent, false)
            return MainViewHolder(viewItem)
        }

        // 2 - Will run everytime the screen be rolled up/down OR everytime the item's content be changed
        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
            val itemCurrent = mainItems[position] // get the current data item
            holder.bind(itemCurrent) // pass to the holder each data item
        }

        // 3 - Where to inform how many items must be displayed
        override fun getItemCount(): Int {
            return mainItems.size
        }

    }

    // is the item's class itself !
    private class MainViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: MainItem) {
            val img : ImageView = itemView.findViewById(R.id.item_img_icon)
            val name : TextView = itemView.findViewById(R.id.item_txt_name)
            val container : LinearLayout = itemView as LinearLayout

            img.setImageResource(item.drawableId)
            name.setText(item.textStringId)
            container.setBackgroundColor(item.color)
        }
    }
}


// Why the name is Recycler View?
// Because everytime you scroll it down/up, the recycler view will get the memory allocated of the cell
// not being shown and will transfer to a new cell that will appear at bottom/top,
// so it recycles the memory being used, only by changing the content inside it.