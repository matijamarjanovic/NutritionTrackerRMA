package rs.raf.rma.nutritiontrackerrma.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import rs.raf.rma.nutritiontrackerrma.data.models.ListMealResource
import rs.raf.rma.nutritiontrackerrma.data.models.Resource
import rs.raf.rma.nutritiontrackerrma.data.models.meals.Meal
import rs.raf.rma.nutritiontrackerrma.data.models.meals.SavedMeal
import rs.raf.rma.nutritiontrackerrma.data.models.meals.listMeals.ListMeal
import rs.raf.rma.nutritiontrackerrma.data.repositories.meal.ListMealRepository
import rs.raf.rma.nutritiontrackerrma.presentation.contracts.MealsContract
import rs.raf.rma.nutritiontrackerrma.presentation.view.states.*

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
    override val caloriesState: MutableLiveData<CaloriesState> = MutableLiveData()
    override val savedMealState : MutableLiveData<SavedMealsState> = MutableLiveData()
    override val savedMealState2: MutableLiveData<SavedMealPageState> = MutableLiveData()

    override val addDone: MutableLiveData<AddListMealState> = MutableLiveData()

    private val publishSubject: PublishSubject<String> = PublishSubject.create()

    init {
        val subscription = publishSubject
            .debounce(200, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .switchMap {
                listMealRepository
                    .getAllByName(it)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnError {
                        Timber.e("Error in publish subject")
                        Timber.e(it)
                    }
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

/*    override fun fetchAllMeals() {


        val subscription = listMealRepository
            .fetchAllByCategory(area)
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
    }*/

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
                    var list :  ArrayList<Meal> = ArrayList()
                    list.add(it)
                    mealsState2.value = MealPageState.Success(it, list)
                },
                {

                    mealsState2.value = MealPageState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }





    override fun getAllMeals() {
        val subscription = listMealRepository
            .getAllMeals()
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

    override fun getAllSavedMeals() {
        val subscription = listMealRepository
            .getAllSavedMeals()
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


    override fun addKcalToMeal(meal : Meal) {

            val subscription = listMealRepository
                .getCalories(meal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        meal.calories = it
                        caloriesState.value = CaloriesState.Success(meal)
                    },
                    {
                        caloriesState.value = CaloriesState.Error("Error happened while fetching data from db")
                        Timber.e(it)
                    }
                )
            subscriptions.add(subscription)

    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.dispose()
    }
}