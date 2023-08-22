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
import rs.raf.rma.nutritiontrackerrma.data.models.meals.listMeals.ListMeal
import rs.raf.rma.nutritiontrackerrma.data.repositories.filter.FilterRepository
import rs.raf.rma.nutritiontrackerrma.data.repositories.ingredients.IngredientsRepository
import rs.raf.rma.nutritiontrackerrma.data.repositories.meal.ListMealRepository
import rs.raf.rma.nutritiontrackerrma.presentation.contracts.FilterContract
import rs.raf.rma.nutritiontrackerrma.presentation.contracts.IngredientsContract
import rs.raf.rma.nutritiontrackerrma.presentation.contracts.MealsContract
import rs.raf.rma.nutritiontrackerrma.presentation.view.states.AddListMealState
import rs.raf.rma.nutritiontrackerrma.presentation.view.states.CategoryState
import rs.raf.rma.nutritiontrackerrma.presentation.view.states.FilterState
import rs.raf.rma.nutritiontrackerrma.presentation.view.states.IngredientsState
import rs.raf.rma.nutritiontrackerrma.presentation.view.states.MealsState

import timber.log.Timber
import java.util.concurrent.TimeUnit

class IngredientsViewModel(
    private val ingredientsRepository : IngredientsRepository,
) : ViewModel(), IngredientsContract.ViewModel {

    private val subscriptions = CompositeDisposable()
    //    override val moviesState: MutableLiveData<MoviesState> = MutableLiveData()
    private val publishSubject: PublishSubject<String> = PublishSubject.create()

    override val ingredientsState: MutableLiveData<IngredientsState> = MutableLiveData()

    init {
        val subscription = publishSubject
            .debounce(200, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .switchMap {
                ingredientsRepository
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
                    ingredientsState.value=IngredientsState.Success(it)
                },
                {
                    ingredientsState.value = IngredientsState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }
    override fun fetchAllIngredients() {
        val subscription = ingredientsRepository
            .fetchAllIngredients()
            .startWith(Resource.Loading()) //Kada se pokrene fetch hocemo da postavimo stanje na Loading
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    when(it) {
                        is Resource.Loading -> ingredientsState.value = IngredientsState.Loading
                        is Resource.Success -> ingredientsState.value = IngredientsState.DataFetched
                        is Resource.Error -> ingredientsState.value = IngredientsState.Error("Error happened while fetching data from the server")
                    }

                    //todo animacija? while state loading or datafetched
                },
                {
                    ingredientsState.value = IngredientsState.Error("Error happened while fetching data from the server")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }
    override fun getAllIngredients() {
        val subscription = ingredientsRepository
            .getAllIngredients()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    ingredientsState.value = IngredientsState.Success(it)
                },
                {
                    ingredientsState.value = IngredientsState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)    }

    override fun getAllIngredientsAscending() {
        val subscription = ingredientsRepository
            .getAllIngredientsAscending()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    ingredientsState.value = IngredientsState.Success(it)
                },
                {
                    ingredientsState.value = IngredientsState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }
    override fun getAllIngredientsDescending() {
        val subscription = ingredientsRepository
            .getAllIngredientsDescending()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    ingredientsState.value = IngredientsState.Success(it)
                },
                {
                    ingredientsState.value = IngredientsState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }
    override fun getItemByName(name: String) {
        publishSubject.onNext(name)
    }
    override fun onCleared() {
        super.onCleared()
        subscriptions.dispose()
    }

}