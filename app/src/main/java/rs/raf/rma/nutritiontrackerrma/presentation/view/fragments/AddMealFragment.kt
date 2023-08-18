package rs.raf.rma.nutritiontrackerrma.presentation.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.rma.nutritiontrackerrma.R
import rs.raf.rma.nutritiontrackerrma.databinding.FragmentAddMealBinding
import rs.raf.rma.nutritiontrackerrma.presentation.contracts.MealsContract
import rs.raf.rma.nutritiontrackerrma.presentation.viewmodels.MealsViewModel


class AddMealFragment : Fragment(R.layout.fragment_add_meal) {

    private val mealsViewModel : MealsContract.ViewModel by sharedViewModel<MealsViewModel> ()

    private var _binding : FragmentAddMealBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddMealBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initListeners()
    }

    private fun initListeners() {
        binding.submitBtn.setOnClickListener{

        }
    }
}