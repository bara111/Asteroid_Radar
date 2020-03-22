package com.udacity.asteroidradar.ui.main

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.BaseApp
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.ActivityMainBinding
import com.udacity.asteroidradar.ui.detail.DetailsActivity
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: MainViewModel
    private lateinit var mainAdapter: MainAdapter
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        (application as BaseApp).appComponent.mainComponent().create().inject(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )
        binding.lifecycleOwner = this
        val viewModel by viewModels<MainViewModel> {
            viewModelFactory
        }
        this.viewModel = viewModel

        mainAdapter = MainAdapter {
            startActivity(DetailsActivity.newIntent(this@MainActivity, it))

        }
        with(binding) {
            asteroidRecycler.adapter = mainAdapter
            asteroidRecycler.hasFixedSize()
        }
        val state = isNetworkAvailable(applicationContext)
        when {
            state -> {
                this.viewModel.getDataFromNetwork()
            }
            else -> {
                this.viewModel.getDataFromDatabase(applicationContext)
            }
        }
        updateUI()
        this.viewModel.mediaData.observe(this, Observer { media ->
            if (media?.mediaType != "video") {
                Picasso.get().load(media?.url).into(binding.activityMainImageOfTheDay)
                binding.activityMainImageOfTheDay.contentDescription = media?.title
            }
        })
    }

    private fun updateUI() {
        this.viewModel.asteroidDailyDataList?.observe(this, Observer { list ->
            mainAdapter.submitList(list)
        })
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo.isConnected
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main_overflow_menu, menu)

    }

}

