package rs.raf.rma.nutritiontrackerrma.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import rs.raf.rma.nutritiontrackerrma.data.models.Resource
import rs.raf.rma.nutritiontrackerrma.data.models.meals.Meal
import rs.raf.rma.nutritiontrackerrma.data.models.meals.SavedMeal
import rs.raf.rma.nutritiontrackerrma.data.models.meals.listMeals.ListMeal
import rs.raf.rma.nutritiontrackerrma.data.repositories.meal.ListMealRepository
import rs.raf.rma.nutritiontrackerrma.presentation.contracts.MealsContract
import rs.raf.rma.nutritiontrackerrma.presentation.view.states.*

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class MealsViewModel(
    private val listMealRepository : ListMealRepository,

) : ViewModel(), MealsContract.ViewModel {

    private val subscriptions = CompositeDisposable()
    override val mealsState: MutableLiveData<MealsState> = MutableLiveData()
    override val mealsState2: MutableLiveData<MealPageState> = MutableLiveData()
    override val mealsState3: MutableLiveData<MealPageState> = MutableLiveData()
    override val caloriesState: MutableLiveData<CaloriesState> = MutableLiveData()
    override val savedMealState : MutableLiveData<SavedMealsState> = MutableLiveData()
    override val savedMealState2: MutableLiveData<SavedMealPageState> = MutableLiveData()

    override val selectedSearchType : MutableLiveData<SearchType> = MutableLiveData()

    override val addDone: MutableLiveData<AddListMealState> = MutableLiveData()

    private val publishSubject: PublishSubject<String> = PublishSubject.create()


    init {
        val subscription = publishSubject
            .debounce(200, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .switchMap { searchQuery ->
                when (selectedSearchType.value) {
                    SearchType.BY_NAME -> {
                        listMealRepository.getAllByName(searchQuery)
                    }
                    SearchType.BY_TAGS -> {
                        listMealRepository.getAllByTags(searchQuery)
                    }
                    else -> Observable.empty() // Handle the default case appropriately
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
                Timber.e("Error in publish subject")
                Timber.e(it)
            }
            .subscribe(
                {
                    mealsState.value = MealsState.Success(it)
                },
                {
                    mealsState.value = MealsState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )

        subscriptions.add(subscription)
    }

    override  fun setSelectedSearchType(searchType: SearchType) {
        selectedSearchType.value = searchType
    }

    override fun fetchAllMealsByArea(area:String) {
        val subscription = listMealRepository
            .fetchAllByArea(area)
            .startWith(Resource.Loading()) //Kada se pokrene fetch hocemo da postavimo stanje na Loading
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    when(it) {
                        is Resource.Loading -> mealsState.value = MealsState.Loading
                        is Resource.Success -> mealsState.value = MealsState.DataFetched
                        is Resource.Error -> mealsState.value = MealsState.Error("Error happened while fetching data from the server")
                    }
                },
                {
                    mealsState.value = MealsState.Error("Error happened while fetching data from the server")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }
    override fun fetchAllMealsByCategory(category: String) {
        val subscription = listMealRepository
            .fetchAllByCategory(category)
            .startWith(Resource.Loading()) //Kada se pokrene fetch hocemo da postavimo stanje na Loading
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    when(it) {
                        is Resource.Loading -> mealsState.value = MealsState.Loading
                        is Resource.Success -> mealsState.value = MealsState.DataFetched
                        is Resource.Error -> mealsState.value = MealsState.Error("Error happened while fetching data from the server")

                    }
                },
                {
                    mealsState.value = MealsState.Error("Error happened while fetching data from the server")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun fetchAllMealsByIngridient(ingridient: String) {
        val subscription = listMealRepository
            .fetchAllByIngredient(ingridient)
            .startWith(Resource.Loading()) //Kada se pokrene fetch hocemo da postavimo stanje na Loading
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    when(it) {
                        is Resource.Loading -> mealsState.value = MealsState.Loading
                        is Resource.Success -> mealsState.value = MealsState.DataFetched
                        is Resource.Error -> mealsState.value = MealsState.Error("Error happened while fetching data from the server")
                    }
                },
                {
                    mealsState.value = MealsState.Error("Error happened while fetching data from the server")

                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getSingleMeal(mealId: String) {

        val subscription = listMealRepository
            .getSingleMeal(mealId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    var list : kotlin.collections.ArrayList<Meal> = ArrayList()
                    list.add(it)
                    mealsState2.value = MealPageState.Success(list)
                },
                {

                    mealsState2.value = MealPageState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getAllSingleMeals(mealList: ArrayList<ListMeal>){
        var list :  ArrayList<Meal> = ArrayList()
        var i = 0

        val subscription = listMealRepository
            .getAllSingleMeals(mealList)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(
                {
                    for (meal in mealList){
                        i++
                        list.add(it)
                    }

                },
                {

                    mealsState3.value = MealPageState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                },
                {
                    mealsState3.value = MealPageState.Success(list)
                }
            )

        if (subscription != null) {
            subscriptions.add(subscription)
        }
    }

    override fun getAllMeals() {
        val subscription = listMealRepository
            .clearListSingleMeals()
            .andThen(listMealRepository.getAllMeals())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    mealsState.value = MealsState.Success(it)
                },
                {
                    mealsState.value = MealsState.Error("Error happened while fetching data from db")
                    mealsState2.value = MealPageState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getAllMealsSortedByCal(min: Double, max: Double) {
        val subscription = listMealRepository
            .clearListSingleMeals()
            .andThen(listMealRepository.getAllMealsSortedCalories(min,max))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    mealsState.value = MealsState.Success(it)
                },
                {
                    mealsState.value = MealsState.Error("Error happened while fetching data from db")
                    mealsState2.value = MealPageState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getMealByName(name: String) {
        publishSubject.onNext(name)
    }

    override fun getMealByTags(tags: String) {
        publishSubject.onNext(tags)
    }

    override fun getAllSavedMeals(user:String) {
        val subscription = listMealRepository
            .getAllSavedMeals(user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { it ->
                    if (it.isNotEmpty()) {
                        savedMealState.value = SavedMealsState.Success(it)
                        savedMealState2.value = SavedMealPageState.Success(it[0], it)
                    } else {
                        savedMealState.value = SavedMealsState.Error("No saved meals found.")
                    }
//                    var list :  ArrayList<SavedMeal> = ArrayList(it)
//
//                    savedMealState.value = SavedMealsState.Success(it)
//                    savedMealState2.value = SavedMealPageState.Success(it[0], it)

                },
                {
                    savedMealState.value = SavedMealsState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getSingleSavedMeal(mealName: String) {
        val subscription = listMealRepository
            .getSingleSavedMeal(mealName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    var list :  ArrayList<SavedMeal> = ArrayList(it)

                    savedMealState.value = SavedMealsState.Success(it)
                    savedMealState2.value = SavedMealPageState.Success(it[0], it)
                },
                {
                    savedMealState.value = SavedMealsState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getSavedMealByName(name: String) {

        val subscription = publishSubject
            .debounce(200, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .switchMap {
                listMealRepository
                    .getAllSavedByName(it)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnError {
                        Timber.e("Error in publish subject")
                        Timber.e(it)
                    }
            }
            .subscribe(
                {
                    var list :  ArrayList<SavedMeal> = ArrayList(it)

                    savedMealState.value = SavedMealsState.Success(it)
                    savedMealState2.value = SavedMealPageState.Success(it[0], it)
                },
                {
                    savedMealState.value = SavedMealsState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )

        subscriptions.add(subscription)
    }

    override fun addMeal(meal: Meal, whichMeal :String, date: Date){
        val subscription = listMealRepository
            .insert(meal, whichMeal, date)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    addDone.value = AddListMealState.Success
                },
                {
                    addDone.value = AddListMealState.Error("Error happened while adding movie")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun updateMeal(meal: SavedMeal, whichMeal: String, date: Date) {
        val subscription = listMealRepository
            .update(meal, whichMeal, date)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    addDone.value = AddListMealState.Success
                },
                {
                    addDone.value = AddListMealState.Error("Error happened while adding movie")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun deleteMeal(id: String) {
        val subscription = listMealRepository
            .delete(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    addDone.value = AddListMealState.Success
                },
                {
                    addDone.value = AddListMealState.Error("Error happened while adding movie")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }


    override fun addKcalToMeal(meals : ArrayList<Meal>){

        val subscription = listMealRepository
            .getCalories(meals)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(
                {
                    for (meal in meals){
                        meal.calories = it
                    }
                },
                {
                    caloriesState.value = CaloriesState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                },
                {
                    caloriesState.value = CaloriesState.Success(meals)
                }
            )
        if (subscription != null) {
            subscriptions.add(subscription)
        }

    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.dispose()
    }
}