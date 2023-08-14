package rs.raf.rma.nutritiontrackerrma.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.rma.nutritiontrackerrma.R
import rs.raf.rma.nutritiontrackerrma.databinding.FragmentHomepageBinding
import rs.raf.rma.nutritiontrackerrma.presentation.contracts.CategoryContract
import rs.raf.rma.nutritiontrackerrma.presentation.view.recycler.adapter.CategoryAdapter
import rs.raf.rma.nutritiontrackerrma.presentation.view.states.CategoryState
import rs.raf.rma.nutritiontrackerrma.presentation.viewmodels.CategoryViewModel
import timber.log.Timber

class HomepageFragment : Fragment(R.layout.fragment_homepage) {

    private val categoryViewModel : CategoryContract.ViewModel by sharedViewModel<CategoryViewModel>()

    private var _binding : FragmentHomepageBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter : CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomepageBinding.inflate(inflater, container, false)
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
        adapter = CategoryAdapter()
        binding.listRv.adapter = adapter
    }

    private fun initListeners() {
        binding.searchBar.doAfterTextChanged {
            val filter = it.toString()
            categoryViewModel.getCategoryByName(filter)
        }
    }

    private fun initObservers() {
        categoryViewModel.categoryState.observe(viewLifecycleOwner, Observer {
            Timber.e(it.toString())
            renderState(it)
        })


        categoryViewModel.getAllCategories()
        categoryViewModel.fetchAllCategories()
    }

    private fun renderState(state: CategoryState) {
        when (state) {
            is CategoryState.Success -> {
                showLoadingState(false)
                adapter.submitList(state.categories)
            }
            is CategoryState.Error -> {
                showLoadingState(false)
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is CategoryState.DataFetched -> {
                showLoadingState(false)
                Toast.makeText(context, "Fresh data fetched from the server", Toast.LENGTH_LONG).show()
            }
            is CategoryState.Loading -> {
                showLoadingState(true)
            }
        }
    }

    private fun showLoadingState(loading: Boolean) {
        /*       binding.searchBar.isVisible = !loading
               binding.listRv.isVisible = !loading

               binding.loadingPb.isVisible = loading
       */
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}