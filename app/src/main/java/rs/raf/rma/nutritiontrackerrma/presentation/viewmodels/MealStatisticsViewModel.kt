package rs.raf.rma.nutritiontrackerrma.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import rs.raf.rma.nutritiontrackerrma.data.repositories.meal.ListMealRepository
import rs.raf.rma.nutritiontrackerrma.data.repositories.mealStatistics.MealStatisticsRepository
import rs.raf.rma.nutritiontrackerrma.presentation.contracts.MealStatisticsContract
import rs.raf.rma.nutritiontrackerrma.presentation.view.states.GraphState
import rs.raf.rma.nutritiontrackerrma.presentation.view.states.MealsState
import rs.raf.rma.nutritiontrackerrma.presentation.view.states.SavedMealsState
import timber.log.Timber
import java.util.concurrent.TimeUnit

class MealStatisticsViewModel(
    private val mealStatisticsRepository: MealStatisticsRepository,
) : ViewModel(), MealStatisticsContract.ViewModel {

    override val graphState: MutableLiveData<GraphState> = MutableLiveData()


    private val subscriptions = CompositeDisposable()
    private val publishSubject: PublishSubject<String> = PublishSubject.create()

    override fun getMealsIn7DaysByNumbers(user:String,days: List<String>) {
        val subscription = mealStatisticsRepository
            .getMealsIn7Days(user,days)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    graphState.value = GraphState.Success(it)
                },
                {
                    graphState.value = GraphState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }
    override fun getMealsIn7DaysByCalories(user:String,days: List<String>) {
        val subscription = mealStatisticsRepository
            .getMealsIn7DaysByCalories(user,days)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    graphState.value = GraphState.Success(it)
                },
                {
                    graphState.value = GraphState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)    }
    override fun onCleared() {
        super.onCleared()
        subscriptions.dispose()
    }
}