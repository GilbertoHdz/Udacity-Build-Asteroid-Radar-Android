package com.gilbertohdz.asteroidradar.ui.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.gilbertohdz.asteroidradar.R
import com.gilbertohdz.asteroidradar.databinding.FragmentMainBinding
import com.gilbertohdz.asteroidradar.repository.FilterBy

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this, MainViewModel.Factory(requireActivity().application))
                .get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.asteroidRecycler.adapter = MainAdapter(viewModel)

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.navigateToDetail.observe(viewLifecycleOwner, Observer { item ->
            findNavController().navigate(MainFragmentDirections.actionShowDetail(item))
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.show_week_menu -> viewModel.filterBy(FilterBy.WEEKLY)
            R.id.show_today_menu -> viewModel.filterBy(FilterBy.TODAY)
            R.id.show_local_menu -> viewModel.filterBy(FilterBy.LOCAL)
        }
        return true
    }
}
