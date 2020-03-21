package com.udacity.asteroidradar.ui.main

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.di.Injectable
import javax.inject.Inject

class MainFragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: MainViewModel
    private lateinit var mainAdapter: MainAdapter
    lateinit var binding: FragmentMainBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }

    private fun updateUI() {
        this.viewModel.asteroidDailyDataList?.observe(viewLifecycleOwner, Observer { list ->
            mainAdapter.submitList(list)
        })
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo.isConnected
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        this.viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(MainViewModel::class.java)
        binding.lifecycleOwner = viewLifecycleOwner

        mainAdapter = MainAdapter {
            val bundle = bundleOf("selectedAsteroid" to it)
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_showDetail, bundle)
        }
        with(binding) {
            asteroidRecycler.adapter = mainAdapter
            asteroidRecycler.hasFixedSize()
        }
        val state = isNetworkAvailable(this.requireContext())
        when {
            state -> {
                this.viewModel.getDataFromNetwork()
            }
            else -> {
                this.viewModel.getDataFromDatabase(this.requireContext())
            }
        }
        updateUI()
        this.viewModel.mediaData.observe(viewLifecycleOwner, Observer { media ->
            if (media?.mediaType != "video") {
                Picasso.get().load(media?.url).into(binding.activityMainImageOfTheDay)
                binding.activityMainImageOfTheDay.contentDescription = media?.title
            }
        })
        setHasOptionsMenu(true)

    }
}
