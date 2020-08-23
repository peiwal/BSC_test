package petrov.ivan.bsc.ui.listParty

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import petrov.ivan.bsc.data.Party
import petrov.ivan.bsc.providers.ContactsProvider
import petrov.ivan.bsc.service.ApiService

class ListPartyViewModel(private val apiSerivice: ApiService, application: Application) : AndroidViewModel(application) {
    val recyclerViewPosition = MutableLiveData<Int>()
    val listParty = MutableLiveData<List<Party>>()
    val eventLoadComplete = MutableLiveData<Boolean>()
    val eventErrorLoadData = MutableLiveData<Boolean>()
    val compositeDisposable = CompositeDisposable()

    fun loadData() {
        val dispose = apiSerivice.getParties()
            .subscribeOn(Schedulers.io())
            .map {
                ContactsProvider(getApplication()).updatePersonInfo(it)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    listParty.value = it
                    eventLoadComplete.value = true
                },
                {
                    eventErrorLoadData.value = true
                })
        compositeDisposable.add(dispose)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}