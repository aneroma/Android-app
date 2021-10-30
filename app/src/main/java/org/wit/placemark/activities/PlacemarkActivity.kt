package org.wit.placemark.activities

import android.content.Intent
import android.location.Location
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.wit.placemark.R
import org.wit.placemark.databinding.ActivityPlacemarkBinding
import org.wit.placemark.helpers.showImagePicker
import org.wit.placemark.main.MainApp
import org.wit.placemark.models.PlacemarkModel
import timber.log.Timber
import timber.log.Timber.i

class PlacemarkActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlacemarkBinding
    var placemark = PlacemarkModel()
    lateinit var app: MainApp
    private lateinit var imageIntentLauncher: ActivityResultLauncher<Intent>

    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    //var location = Location(52.245696, -7.139102, 15f)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var edit = false
        binding = ActivityPlacemarkBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        //Timber.plant(Timber.DebugTree())
        //i("Placemark Activity started...")
        setSupportActionBar(binding.toolbarAdd)
        //binding.btnAdd.setOnClickListener() {
        //how it is initialised
        app = application as MainApp
        //i("Placemark Activity started...")

        if (intent.hasExtra("placemark_edit")) {
            placemark = intent.extras?.getParcelable("placemark_edit")!!
            binding.placemarkTitle.setText(placemark.title)
            binding.description.setText(placemark.description)
            binding.btnAdd.setText(R.string.save_placemark)
            Picasso.get()
                .load(placemark.image)
                .into(binding.placemarkImage)
            if (placemark.image != Uri.EMPTY) {
                binding.chooseImage.setText(R.string.change_placemark_image)
            }
        }

        binding.btnAdd.setOnClickListener() {
            placemark.title = binding.placemarkTitle.text.toString()
            placemark.description = binding.description.text.toString()
            if (placemark.title.isEmpty()) {
                Snackbar
                    .make(it, R.string.enter_placemark_title, Snackbar.LENGTH_LONG)
                    .show()
            }
            //and how it is used
            //app!!.placemarks.add(placemark.copy())
            //app.placemarks.create(placemark.copy())
            //setResult(RESULT_OK)
            //finish()
            //}
            //placemarks.add(placemark.copy())
            //i("add Button Pressed: ${placemark}")
            //for (i in app!!.placemarks.indices)
            //{ i("Placemark[$i]:${this.app!!.placemarks[i]}") }
            //}
            //for (i in placemarks.indices)
            //{ i("Placemark[$i]:${this.placemarks[i]}") }
            //}
            else {
                if (edit) {
                    app.placemarks.update(placemark.copy())
                } else {
                    app.placemarks.create(placemark.copy())
                }
            }
            i("add Button Pressed: $placemark")
            setResult(RESULT_OK)
            finish()
        }
        binding.placemarkLocation.setOnClickListener {
            i ("Set Location Pressed")
        }

        binding.chooseImage.setOnClickListener {
            showImagePicker(imageIntentLauncher)
        }

        binding.placemarkLocation.setOnClickListener {

            val launcherIntent = Intent(this, MapActivity::class.java)
            mapIntentLauncher.launch(launcherIntent)
        }
        registerImagePickerCallback()
        registerMapCallback()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_placemark, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_delete -> {
                app.placemarks.delete(placemark)
                finish()
            }
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Result ${result.data!!.data}")
                            placemark.image = result.data!!.data!!
                            Picasso.get()
                                .load(placemark.image)
                                .into(binding.placemarkImage)
                            binding.chooseImage.setText(R.string.change_placemark_image)
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
        }
    private fun registerMapCallback() {
        mapIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { i("Map Loaded") }
    }
}
