package petrov.ivan.bsc.ui.placeInfo

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import petrov.ivan.bsc.data.Party

class PlaceInfoViewModelFactory(private val application: Application,
                                private val party: Party
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlaceInfoViewModel::class.java)) {
            return PlaceInfoViewModel(application, party) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}