package rs.raf.rma.nutritiontrackerrma.presentation.view.fragments

import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.sharedStateViewModel
import rs.raf.rma.nutritiontrackerrma.R
import rs.raf.rma.nutritiontrackerrma.presentation.contracts.CategoryContract
import rs.raf.rma.nutritiontrackerrma.presentation.viewmodels.CategoryViewModel

class HomepageFragment : Fragment(R.layout.fragment_homepage) {

    private val categoryViewModel : CategoryContract.ViewModel by sharedStateViewModel<CategoryViewModel>()


}