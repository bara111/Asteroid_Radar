package com.udacity.asteroidradar.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.BaseApp
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.ActivityDetailsBinding
import javax.inject.Inject

class DetailsActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: DetailsViewModel
    lateinit var binding: ActivityDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        (application as BaseApp).appComponent.detailsComponent().create().inject(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details)
        binding.lifecycleOwner = this
        val viewModel by viewModels<DetailsViewModel> {
            viewModelFactory
        }
        this.viewModel = viewModel
        binding.asteroid = intent.getParcelableExtra(EXTRA_DETAILS)
        binding.helpButton.setOnClickListener {
            displayAstronomicalUnitExplanationDialog()
        }
    }

    private fun displayAstronomicalUnitExplanationDialog() {
        val builder = AlertDialog.Builder(this)
            .setMessage(getString(R.string.astronomica_unit_explanation))
            .setPositiveButton(android.R.string.ok, null)
        builder.create().show()
    }

    companion object {
        private val EXTRA_DETAILS: String = "${DetailsActivity::class.java.name}_DETAILS_EXTRA"
        fun newIntent(context: Context, data: Asteroid?): Intent {
            return Intent(context, DetailsActivity::class.java).putExtra(
                EXTRA_DETAILS,
                data
            )
        }
    }
}
