package org.wit.placemark.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import org.wit.placemark.databinding.ActivityPlacemarkBinding
import org.wit.placemark.main.MainApp
import org.wit.placemark.models.PlacemarkModel
//import timber.log.Timber
import timber.log.Timber.i

class PlacemarkActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlacemarkBinding
    var placemark = PlacemarkModel()
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlacemarkBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Timber.plant(Timber.DebugTree())
        //i("Placemark Activity started...")

        //binding.btnAdd.setOnClickListener() {
        //how it is initialised
        app = application as MainApp
        i("Placemark Activity started...")
        binding.btnAdd.setOnClickListener() {
            placemark.title = binding.placemarkTitle.text.toString()
            placemark.description = binding.description.text.toString()
            if (placemark.title.isNotEmpty()) {
                //and how it is used
                app!!.placemarks.add(placemark.copy())
                //placemarks.add(placemark.copy())
                i("add Button Pressed: ${placemark}")
                for (i in app!!.placemarks.indices)
                { i("Placemark[$i]:${this.app!!.placemarks[i]}") }
            }
                //for (i in placemarks.indices)
                //{ i("Placemark[$i]:${this.placemarks[i]}") }
            //}
            else {
                Snackbar.make(it,"Please Enter a title", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }
}