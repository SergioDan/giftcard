package com.accretiond.styletransfer

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.accretiond.giftcard.styletransfer.R
import com.accretiond.giftcard.styletransfer.databinding.TransformationMainBinding
import java.io.File

class TransformationActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: TransformationMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = TransformationMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        // Config action bar
        setSupportActionBar(activityMainBinding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        appBarConfiguration = AppBarConfiguration.Builder(
           R.id.transformation_fragment
        ).build()

        setViewModelInputModel()
    }

    private fun setViewModelInputModel() {
        intent.getStringExtra("path")?.let {uri ->
            val file = File(uri)
            val bitmap: Bitmap = BitmapFactory.decodeFile(file.absolutePath)
            val rotateDegree = if (file.name.contains("selected_image")) {
                0f
            } else {
                -90f
            }
            viewModel.inputBitmap.postValue(bitmap.rotate(rotateDegree))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController =
            findNavController(R.id.fragment_container)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}