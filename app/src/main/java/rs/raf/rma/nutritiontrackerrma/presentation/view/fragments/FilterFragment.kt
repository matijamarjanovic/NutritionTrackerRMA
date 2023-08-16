package rs.raf.rma.nutritiontrackerrma.presentation.view.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.rma.nutritiontrackerrma.R
import rs.raf.rma.nutritiontrackerrma.databinding.FragmentFilterBinding
import rs.raf.rma.nutritiontrackerrma.databinding.FragmentHomepageBinding
import rs.raf.rma.nutritiontrackerrma.presentation.contracts.CategoryContract
import rs.raf.rma.nutritiontrackerrma.presentation.contracts.FilterContract
import rs.raf.rma.nutritiontrackerrma.presentation.view.recycler.adapter.CategoryAdapter
import rs.raf.rma.nutritiontrackerrma.presentation.view.recycler.adapter.FilterAdapter
import rs.raf.rma.nutritiontrackerrma.presentation.view.states.CategoryState
import rs.raf.rma.nutritiontrackerrma.presentation.view.states.FilterState
import rs.raf.rma.nutritiontrackerrma.presentation.viewmodels.CategoryViewModel
import rs.raf.rma.nutritiontrackerrma.presentation.viewmodels.FilterViewModel
import timber.log.Timber

class FilterFragment : Fragment(R.layout.fragment_filter) {

    private val filterViewModel : FilterContract.ViewModel by sharedViewModel<FilterViewModel>()

    private var _binding : FragmentFilterBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter : FilterAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initUi()
        initObservers()
    }
    private fun initUi() {
        initRecycler()
        initListeners()
    }

    private fun initRecycler() {
        binding.listRv.layoutManager = LinearLayoutManager(context)
        adapter = FilterAdapter()
        binding.listRv.adapter = adapter
    }

    private fun initListeners() {
        binding.inputEt.doAfterTextChanged {
            val filter = it.toString()
            filterViewModel.getItemByName(filter)
        }

        binding.sortDescendingBtn.visibility = View.GONE

        binding.sortAscendingBtn.setOnClickListener{
            binding.sortDescendingBtn.visibility = View.VISIBLE
            binding.sortAscendingBtn.visibility = View.GONE
        }

        binding.sortDescendingBtn.setOnClickListener{
            binding.sortDescendingBtn.visibility = View.GONE
            binding.sortAscendingBtn.visibility = View.VISIBLE
        }

        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.areaRb -> {
                    filterViewModel.getAllAreas(binding.sortDescendingBtn.visibility == View.VISIBLE)
                    filterViewModel.fetchAllAreas()
                }
                R.id.catRb -> {
                    filterViewModel.getAllCategories(binding.sortDescendingBtn.visibility == View.VISIBLE)
                    filterViewModel.fetchAllCategories()
                }
                R.id.ingRb -> {
                    filterViewModel.getAllIngredients(binding.sortDescendingBtn.visibility == View.VISIBLE)
                    filterViewModel.fetchAllIngredients()
                }
            }
        }
    }

    private fun initObservers() {
        filterViewModel.filterdItemsState.observe(viewLifecycleOwner, Observer {
            Timber.e(it.toString())
            renderState(it)
        })

        binding.areaRb.isChecked = true

    }

    private fun renderState(state: FilterState) {
        when (state) {
            is FilterState.Success -> {
                showLoadingState(false)
                adapter.submitList(state.filtredItems)
            }
            is FilterState.Error -> {
                showLoadingState(false)
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is FilterState.DataFetched -> {
                showLoadingState(false)
                //Toast.makeText(context, "Fresh data fetched from the server", Toast.LENGTH_LONG).show()
            }
            is FilterState.Loading -> {
                showLoadingState(true)
            }
        }
    }

    private fun showLoadingState(loading: Boolean) {
           binding.inputEt.isVisible = !loading
           binding.listRv.isVisible = !loading


           binding.loadingPb.isVisible = loading
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}