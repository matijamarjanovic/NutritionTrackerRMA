package rs.raf.rma.nutritiontrackerrma.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import rs.raf.rma.nutritiontrackerrma.data.models.user.User
import rs.raf.rma.nutritiontrackerrma.data.repositories.user.UserRepository
import rs.raf.rma.nutritiontrackerrma.presentation.contracts.UserContract
import rs.raf.rma.nutritiontrackerrma.presentation.view.states.user.AddUserState
import rs.raf.rma.nutritiontrackerrma.presentation.view.states.user.UserState

import timber.log.Timber
import java.util.concurrent.TimeUnit

class LoginViewModel(
    private val userRepository:UserRepository,

) : ViewModel(), UserContract.ViewModel {

    private val subscriptions = CompositeDisposable()
    private val publishSubject: PublishSubject<String> = PublishSubject.create()
    override val userState: MutableLiveData<UserState> = MutableLiveData()
    override val addDone: MutableLiveData<AddUserState> = MutableLiveData()
    init {
        val subscription = publishSubject
            .debounce(200, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .switchMap {
                userRepository
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
                    userState.value = UserState.Success(it)
                },
                {
                    userState.value = UserState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )

        subscriptions.add(subscription)
    }

    override fun addUser(user: User) {
        val subscription = userRepository
            .insert(user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    addDone.value = AddUserState.Success
                },
                {
                    addDone.value = AddUserState.Error("Error happened while adding movie")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun checkUser(username: String): Int {
        return userRepository.checkUser(username)
            .observeOn(Schedulers.io())
            .blockingFirst()
    }

    override fun getUser(username: String):User {
        return userRepository.getUser(username)
            .map { response ->
                User(response.username, response.password)
            }
            .blockingFirst()
    }

}