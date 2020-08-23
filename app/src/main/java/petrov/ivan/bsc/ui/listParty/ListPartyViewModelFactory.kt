package petrov.ivan.bsc.ui.listParty

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import petrov.ivan.bsc.service.ApiService

class ListPartyViewModelFactory(private val apiService: ApiService,
                                private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListPartyViewModel::class.java)) {
            return ListPartyViewModel(apiService, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}