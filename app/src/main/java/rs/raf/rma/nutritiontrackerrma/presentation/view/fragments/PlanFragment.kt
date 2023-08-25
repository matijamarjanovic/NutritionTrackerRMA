package rs.raf.rma.nutritiontrackerrma.presentation.view.fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.rma.nutritiontrackerrma.R
import rs.raf.rma.nutritiontrackerrma.data.models.meals.SavedMeal
import rs.raf.rma.nutritiontrackerrma.data.models.meals.listMeals.ListMeal
import rs.raf.rma.nutritiontrackerrma.databinding.FragmentListMealBinding
import rs.raf.rma.nutritiontrackerrma.databinding.FragmentPlanBinding
import rs.raf.rma.nutritiontrackerrma.presentation.contracts.MealsContract
import rs.raf.rma.nutritiontrackerrma.presentation.view.states.FilterState
import rs.raf.rma.nutritiontrackerrma.presentation.view.states.MealsState
import rs.raf.rma.nutritiontrackerrma.presentation.view.states.SavedMealsState
import rs.raf.rma.nutritiontrackerrma.presentation.viewmodels.MealsViewModel
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList

class PlanFragment : Fragment(R.layout.fragment_plan) {

    private val mealsViewModel: MealsContract.ViewModel by sharedViewModel<MealsViewModel>()

    private var _binding: FragmentPlanBinding? = null
    private val binding get() = _binding!!


    var mealList : ArrayList<String> = ArrayList()
    var kcalList : ArrayList<Double> = ArrayList()
    var mealListFinal : ArrayList<String> = ArrayList(Collections.nCopies(21, ""))

    var savedMeals : ArrayList<SavedMeal> = ArrayList()
    var meals : ArrayList<ListMeal> = ArrayList()

    private val sharedPreferencesManager = SharedPreferencesManager.getInstance()
    val username = sharedPreferencesManager.getUsername()?:""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }



    private fun init() {
        initObservers()
        initListeners()
        initSpinners()
    }

    lateinit var adapter : ArrayAdapter<String>
    private fun initSpinners() {

        if (adapter != null) {
            initSpinner(binding.monSpinnerB, 0, adapter!!, binding.monBTv)
            initSpinner(binding.monSpinnerL, 1, adapter!!, binding.monLTv)
            initSpinner(binding.monSpinnerD, 2, adapter!!, binding.monDTv)
            initSpinner(binding.tueSpinnerB, 3, adapter!!, binding.tueBTv)
            initSpinner(binding.tueSpinnerL, 4, adapter!!, binding.tueLTv)
            initSpinner(binding.tueSpinnerD, 5, adapter!!, binding.tueDTv)
            initSpinner(binding.wedSpinnerB, 6, adapter!!, binding.wedBTv)
            initSpinner(binding.wedSpinnerL, 7, adapter!!, binding.wedLTv)
            initSpinner(binding.wedSpinnerD, 8, adapter!!, binding.wedDTv)
            initSpinner(binding.thurSpinnerB, 9, adapter!!, binding.thurBTv)
            initSpinner(binding.thurSpinnerL, 10, adapter!!, binding.thurLTv)
            initSpinner(binding.thurSpinnerD, 11, adapter!!, binding.thurDTv)
            initSpinner(binding.friSpinnerB, 12, adapter!!, binding.friBTv)
            initSpinner(binding.friSpinnerL, 13, adapter!!, binding.friLTv)
            initSpinner(binding.friSpinnerD, 14, adapter!!, binding.friDTv)
            initSpinner(binding.satSpinnerB, 15, adapter!!, binding.satBTv)
            initSpinner(binding.satSpinnerL, 16, adapter!!, binding.satLTv)
            initSpinner(binding.satSpinnerD, 17, adapter!!, binding.satDTv)
            initSpinner(binding.sunSpinnerB, 18, adapter!!, binding.sunBTv)
            initSpinner(binding.sunSpinnerL, 19, adapter!!, binding.sunLTv)
            initSpinner(binding.satSpinnerD, 20, adapter!!, binding.sunDTv)
        }


    }

    private fun initSpinner(spinner: Spinner, mealNum : Int, adapter : ArrayAdapter<String>, kcalTv : TextView) {

        adapter?.setDropDownViewResource(R.layout.spinner_tv)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selected = mealList[position]

                val formattedValue = String.format("%.2f", kcalList[position])
                kcalTv.text = formattedValue

                if ((kcalTv.text.toString() == "0" || kcalTv.text.toString() == "0.0") && position != 0){
                    showDialogue("Please choose another item in order to proceed. This item does not have a calorie value.")
                }

                if (selected != null) {
                    mealListFinal[mealNum] = selected
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                mealListFinal[mealNum] = ""

            }
        }


    }
    private fun setAdapters(adapter: ArrayAdapter<String>){
        binding.monSpinnerB.adapter = adapter
        binding.monSpinnerL.adapter = adapter
        binding.monSpinnerD.adapter = adapter
        binding.tueSpinnerB.adapter = adapter
        binding.tueSpinnerL.adapter = adapter
        binding.tueSpinnerD.adapter = adapter
        binding.wedSpinnerB.adapter = adapter
        binding.wedSpinnerL.adapter = adapter
        binding.wedSpinnerD.adapter = adapter
        binding.thurSpinnerB.adapter = adapter
        binding.thurSpinnerL.adapter = adapter
        binding.thurSpinnerD.adapter = adapter
        binding.friSpinnerB.adapter = adapter
        binding.friSpinnerL.adapter = adapter
        binding.friSpinnerD.adapter = adapter
        binding.satSpinnerB.adapter = adapter
        binding.satSpinnerL.adapter = adapter
        binding.satSpinnerD.adapter = adapter
        binding.sunSpinnerB.adapter = adapter
        binding.sunSpinnerL.adapter = adapter
        binding.sunSpinnerD.adapter = adapter

    }

    private fun setNothingSelectedToAll(){
        binding.monSpinnerB.setSelection(-1, false)
        binding.monSpinnerL.setSelection(-1, false)
        binding.monSpinnerD.setSelection(-1, false)
        binding.tueSpinnerB.setSelection(-1, false)
        binding.tueSpinnerL.setSelection(-1, false)
        binding.tueSpinnerD.setSelection(-1, false)
        binding.wedSpinnerB.setSelection(-1, false)
        binding.wedSpinnerL.setSelection(-1, false)
        binding.wedSpinnerD.setSelection(-1, false)
        binding.thurSpinnerB.setSelection(-1, false)
        binding.thurSpinnerL.setSelection(-1, false)
        binding.thurSpinnerD.setSelection(-1, false)
        binding.friSpinnerB.setSelection(-1, false)
        binding.friSpinnerL.setSelection(-1, false)
        binding.friSpinnerD.setSelection(-1, false)
        binding.satSpinnerB.setSelection(-1, false)
        binding.satSpinnerL.setSelection(-1, false)
        binding.satSpinnerD.setSelection(-1, false)
        binding.sunSpinnerB.setSelection(-1, false)
        binding.sunSpinnerL.setSelection(-1, false)
        binding.sunSpinnerD.setSelection(-1, false)

    }

    private fun initListeners() {


        binding.sourceRg.setOnCheckedChangeListener { group, checkedId ->


            when(checkedId){
                R.id.radioApi -> {
                    mealsViewModel.getAllMeals()
                    binding.scrollView.smoothScrollTo(0,0)

                }
                R.id.radioDb -> {
                    binding.scrollView.smoothScrollTo(0,0)
                    val sharedPreferencesManager=SharedPreferencesManager.getInstance()
                    val username=sharedPreferencesManager.getUsername()?:""
                    mealsViewModel.getAllSavedMeals(username)


                }
            }
        }
    }

    private fun renderState(state: MealsState) {
        when (state) {
            is MealsState.Success -> {
                showLoadingState(false)

                mealList.clear()
                meals = ArrayList(state.meals)
                for (m in state.meals){
                    mealList.add(m.strMeal)
                    kcalList.add(m.calories)
                }
                adapter = context?.let { ArrayAdapter(it, R.layout.spinner_tv, mealList) }!!
                adapter?.let { setAdapters(it) }
                setNothingSelectedToAll()

            }
            is MealsState.Error -> {
                showLoadingState(false)
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is MealsState.DataFetched -> {
                showLoadingState(false)
                //Toast.makeText(context, "Fresh data fetched from the server", Toast.LENGTH_LONG).show()
            }
            is MealsState.Loading -> {
                showLoadingState(true)
            }
        }



        binding.sendEmailBtn.setOnClickListener{

            val body = """
                    Monday:
                    
                    Breakfast: ${mealListFinal[0]}
                    Lunch: ${mealListFinal[1]}
                    Dinner: ${mealListFinal[2]}
                    ---------------------------------
                    Tuesday:
                    
                    Breakfast: ${mealListFinal[3]}
                    Lunch: ${mealListFinal[4]}
                    Dinner: ${mealListFinal[5]}
                    ---------------------------------
                    Wednesday:
                    
                    Breakfast: ${mealListFinal[6]}
                    Lunch: ${mealListFinal[7]}
                    Dinner: ${mealListFinal[8]}
                    ---------------------------------
                    Thursday:
                    
                    Breakfast: ${mealListFinal[9]}
                    Lunch: ${mealListFinal[10]}
                    Dinner: ${mealListFinal[11]}
                    ---------------------------------
                    Friday:
                    
                    Breakfast: ${mealListFinal[12]}
                    Lunch: ${mealListFinal[13]}
                    Dinner: ${mealListFinal[14]}
                    ---------------------------------
                    Saturday:
                    
                    Breakfast: ${mealListFinal[15]}
                    Lunch: ${mealListFinal[16]}
                    Dinner: ${mealListFinal[17]}
                    ---------------------------------
                    Sunday:
                    
                    Breakfast: ${mealListFinal[18]}
                    Lunch: ${mealListFinal[19]}
                    Dinner: ${mealListFinal[20]}
                """.trimIndent()

            if (!isValidEmail(binding.emailEt.toString().trim())){
                showDialogue("Please enter a valid email.")
            }else if(username == ""){
                showDialogue("Cannot register the logged in user.")
            }else{
                sendEmail(binding.emailEt.toString(), "Plan ishrane $username", body)
            }

        }
    }



    private fun renderStateSaved(state: SavedMealsState) {
        when (state) {
            is SavedMealsState.Success -> {
                showLoadingState(false)
                savedMeals = ArrayList(state.meals)

                mealList.clear()
                for (m in state.meals){
                    m.strMeal?.let { mealList.add(it) }
                }
                adapter = context?.let { ArrayAdapter(it, R.layout.spinner_tv, mealList) }!!
                adapter?.let { setAdapters(it) }
                setNothingSelectedToAll()
            }
            is SavedMealsState.Error -> {
                showLoadingState(false)
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is SavedMealsState.DataFetched -> {
                showLoadingState(false)
                //Toast.makeText(context, "Fresh data fetched from the server", Toast.LENGTH_LONG).show()
            }
            is SavedMealsState.Loading -> {
                showLoadingState(true)
            }
        }
    }

    private fun initObservers() {
        mealsViewModel.mealsState.observe(viewLifecycleOwner, Observer {
            Timber.e(it.toString())
            renderState(it)
        })

        mealsViewModel.savedMealState.observe(viewLifecycleOwner, Observer {
            Timber.e(it.toString())
            renderStateSaved(it)
        })

        binding.radioApi.isChecked = true
        binding.emailEt.clearFocus()
        binding.scrollView.smoothScrollTo(0,0)

        mealsViewModel.getAllMeals()

        adapter = context?.let { ArrayAdapter(it, R.layout.spinner_tv, mealList) }!!
        adapter?.let { setAdapters(it) }


    }
    private fun showLoadingState(loading: Boolean) {
        binding.monLay.isVisible = !loading
        binding.tueLay.isVisible = !loading
        binding.wedLay.isVisible = !loading
        binding.thurLay.isVisible = !loading
        binding.friLay.isVisible = !loading
        binding.satLay.isVisible = !loading
        binding.sunLay.isVisible = !loading

        binding.loadingPb.isVisible = loading
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showDialogue(text: String) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_layout, null)
        val dialogEditText: TextView = dialogView.findViewById(R.id.dialogEditText)

        dialogEditText.text = text
        dialogEditText.isFocusable = false
        dialogEditText.isClickable = false
        dialogEditText.isLongClickable = false

        val dialogBuilder = AlertDialog.Builder(context)
            .setView(dialogView)
            .setPositiveButton("OK") { dialog, _ ->
                // Handle the OK button click if needed
                dialog.dismiss()
            }

        val dialog = dialogBuilder.create()
        dialog.show()
    }

    private fun sendEmail(email : String, subject : String, body : String) {
        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.type = "text/plain"
        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email)) // Recipient's email address
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        emailIntent.putExtra(Intent.EXTRA_TEXT, body)

        startActivity(Intent.createChooser(emailIntent, "Send Email"))
    }

    fun isValidEmail(email: String): Boolean {
        val emailRegex = Regex("^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,3})+\$")
        return email.matches(emailRegex)
    }

    fun hasEmptyElement(list: ArrayList<String>): Boolean {
        return list.any { it.isEmpty() }
    }

}