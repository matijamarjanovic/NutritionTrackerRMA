package rs.raf.rma.nutritiontrackerrma.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import rs.raf.rma.nutritiontrackerrma.data.models.Resource
import rs.raf.rma.nutritiontrackerrma.data.repositories.categoryrepositories.CategoryRepository
import rs.raf.rma.nutritiontrackerrma.presentation.contracts.CategoryContract
import rs.raf.rma.nutritiontrackerrma.presentation.view.states.CategoryState
import timber.log.Timber
import java.util.concurrent.TimeUnit

class CategoryViewModel(
    private val categoryRepository: CategoryRepository
) : ViewModel(), CategoryContract.ViewModel {

    private val subscriptions = CompositeDisposable()
    override val categoryState: MutableLiveData<CategoryState> = MutableLiveData()

    private val publishSubject: PublishSubject<String> = PublishSubject.create()

    init {
        val subscription = publishSubject
            .debounce(200, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .switchMap { it ->
                categoryRepository
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
                    categoryState.value = CategoryState.Success(it)
                },
                {
                    categoryState.value = CategoryState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun fetchAllCategories() {
        val subscription = categoryRepository
            .fetchAll()
            .startWith(Resource.Loading()) //Kada se pokrene fetch hocemo da postavimo stanje na Loading
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    when(it) {
                        is Resource.Loading -> categoryState.value = CategoryState.Loading
                        is Resource.Success -> categoryState.value = CategoryState.DataFetched
                        is Resource.Error -> categoryState.value = CategoryState.Error("Error happened while fetching data from the server")
                    }

                    //todo animacija? while state loading or datafetched
                },
                {
                    categoryState.value = CategoryState.Error("Error happened while fetching data from the server")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getCategoryByName(name: String) {
        publishSubject.onNext(name)
    }

    override fun getAllCategories() {
        val subscription = categoryRepository
            .getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    categoryState.value = CategoryState.Success(it)
                },
                {
                    categoryState.value = CategoryState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getAllCategoriesByPage(pageNumber: Int, itemNumber: Int) {
        val subscription = categoryRepository
            .getAllByPage(pageNumber,itemNumber)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    categoryState.value = CategoryState.Success(it)
                },
                {
                    categoryState.value = CategoryState.Error("Error happened while fetching data from db")
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