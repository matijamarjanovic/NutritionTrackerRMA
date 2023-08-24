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
import rs.raf.rma.nutritiontrackerrma.data.repositories.meal.ListMealRepository
import rs.raf.rma.nutritiontrackerrma.presentation.contracts.FilterContract
import rs.raf.rma.nutritiontrackerrma.presentation.contracts.MealsContract
import rs.raf.rma.nutritiontrackerrma.presentation.view.states.AddListMealState
import rs.raf.rma.nutritiontrackerrma.presentation.view.states.CategoryState
import rs.raf.rma.nutritiontrackerrma.presentation.view.states.FilterState
import rs.raf.rma.nutritiontrackerrma.presentation.view.states.MealsState

import timber.log.Timber
import java.util.concurrent.TimeUnit

class FilterViewModel(
    private val filterRepository : FilterRepository,
) : ViewModel(), FilterContract.ViewModel {

    private val subscriptions = CompositeDisposable()
    //    override val moviesState: MutableLiveData<MoviesState> = MutableLiveData()
    private val publishSubject: PublishSubject<String> = PublishSubject.create()

    override val filterdItemsState: MutableLiveData<FilterState> = MutableLiveData()


    init {
        val subscription = publishSubject
            .debounce(200, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .switchMap {
                filterRepository
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
                    filterdItemsState.value=FilterState.Success(it)
                },
                {
                    filterdItemsState.value = FilterState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }



    override fun fetchAllCategories() {
        val subscription = filterRepository
            .fetchAllCategories()
            .startWith(Resource.Loading()) //Kada se pokrene fetch hocemo da postavimo stanje na Loading
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    when(it) {
                        is Resource.Loading -> filterdItemsState.value = FilterState.Loading
                        is Resource.Success -> filterdItemsState.value = FilterState.DataFetched
                        is Resource.Error -> filterdItemsState.value = FilterState.Error("Error happened while fetching data from the server")
                    }

                    //todo animacija? while state loading or datafetched
                },
                {
                    filterdItemsState.value = FilterState.Error("Error happened while fetching data from the server")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun fetchAllAreas() {
        val subscription = filterRepository
            .fetchAllAreas()
            .startWith(Resource.Loading()) //Kada se pokrene fetch hocemo da postavimo stanje na Loading
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    when(it) {
                        is Resource.Loading -> filterdItemsState.value = FilterState.Loading
                        is Resource.Success -> filterdItemsState.value = FilterState.DataFetched
                        is Resource.Error -> filterdItemsState.value = FilterState.Error("Error happened while fetching data from the server")
                    }

                    //todo animacija? while state loading or datafetched
                },
                {
                    filterdItemsState.value = FilterState.Error("Error happened while fetching data from the server")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun fetchAllIngredients() {
        val subscription = filterRepository
            .fetchAllIngredients()
            .startWith(Resource.Loading()) //Kada se pokrene fetch hocemo da postavimo stanje na Loading
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    when(it) {
                        is Resource.Loading -> filterdItemsState.value = FilterState.Loading
                        is Resource.Success -> filterdItemsState.value = FilterState.DataFetched
                        is Resource.Error -> filterdItemsState.value = FilterState.Error("Error happened while fetching data from the server")
                    }

                    //todo animacija? while state loading or datafetched
                },
                {
                    filterdItemsState.value = FilterState.Error("Error happened while fetching data from the server")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun insertIngredientsIntoDatabase() {
        filterRepository.insertIngredientsIntoDatabase()
    }

    override fun getAllCategories() {

        val subscription = filterRepository
            .getAllCategories()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    filterdItemsState.value = FilterState.Success(it)
                },
                {
                    filterdItemsState.value = FilterState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getAllAreas() {
        val subscription = filterRepository
            .getAllAreas()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    filterdItemsState.value = FilterState.Success(it)
                },
                {
                    filterdItemsState.value = FilterState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)    }

    override fun getAllIngredients() {
        val subscription = filterRepository
            .getAllIngredients()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    filterdItemsState.value = FilterState.Success(it)
                },
                {
                    filterdItemsState.value = FilterState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)    }

    override fun getAllCategoriesAscending() {
        val subscription = filterRepository
            .getAllCategoriesAscending()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    filterdItemsState.value = FilterState.Success(it)
                },
                {
                    filterdItemsState.value = FilterState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getAllAreasAscending() {
        val subscription = filterRepository
            .getAllAreasAscending()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    filterdItemsState.value = FilterState.Success(it)
                },
                {
                    filterdItemsState.value = FilterState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getAllIngredientsAscending() {
        val subscription = filterRepository
            .getAllIngredientsAscending()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    filterdItemsState.value = FilterState.Success(it)
                },
                {
                    filterdItemsState.value = FilterState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getAllCategoriesDescending() {
        val subscription = filterRepository
            .getAllCategoriesDescending()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    filterdItemsState.value = FilterState.Success(it)
                },
                {
                    filterdItemsState.value = FilterState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getAllAreasDescending() {
        val subscription = filterRepository
            .getAllAreasDescending()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    filterdItemsState.value = FilterState.Success(it)
                },
                {
                    filterdItemsState.value = FilterState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getAllIngredientsDescending() {
        val subscription = filterRepository
            .getAllIngredientsDescending()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    filterdItemsState.value = FilterState.Success(it)
                },
                {
                    filterdItemsState.value = FilterState.Error("Error happened while fetching data from db")
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