package rs.raf.rma.nutritiontrackerrma.presentation.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.rma.nutritiontrackerrma.R
import rs.raf.rma.nutritiontrackerrma.databinding.FragmentListMealBinding
import rs.raf.rma.nutritiontrackerrma.databinding.FragmentPlanBinding
import rs.raf.rma.nutritiontrackerrma.presentation.contracts.MealsContract
import rs.raf.rma.nutritiontrackerrma.presentation.viewmodels.MealsViewModel

class PlanFragment : Fragment(R.layout.fragment_plan) {

    private val mealsViewModel: MealsContract.ViewModel by sharedViewModel<MealsViewModel>()

    private var _binding: FragmentPlanBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlanBinding.inflate(inflater, container, false)
        return binding.root
    }


}