package org.wit.placemark.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
//import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
//import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
import org.wit.placemark.R
import org.wit.placemark.adapters.PlacemarkAdapter
import org.wit.placemark.adapters.PlacemarkListener
import org.wit.placemark.databinding.ActivityPlacemarkListBinding
//import org.wit.placemark.databinding.CardPlacemarkBinding
import org.wit.placemark.main.MainApp
import org.wit.placemark.models.PlacemarkModel

//import org.wit.placemark.models.PlacemarkModel

class PlacemarkListActivity : AppCompatActivity(), PlacemarkListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityPlacemarkListBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityPlacemarkListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        //binding.recyclerView.adapter = PlacemarkAdapter(app.placemarks)
        binding.recyclerView.adapter = PlacemarkAdapter(app.placemarks.findAll(),this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, PlacemarkActivity::class.java)
                startActivityForResult(launcherIntent,0)
            }
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onPlacemarkClick(placemark: PlacemarkModel) {
        val launcherIntent = Intent(this, PlacemarkActivity::class.java)
        launcherIntent.putExtra("placemark_edit", placemark)
        startActivityForResult(launcherIntent,0)
    }
}

