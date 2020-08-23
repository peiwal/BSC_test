package petrov.ivan.bsc.ui.placeInfo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import petrov.ivan.bsc.data.Party

class PlaceInfoViewModel(application: Application, party: Party) : AndroidViewModel(application) {
    val party = MutableLiveData<Party>()

    init {
        this.party.value = party
    }

    override fun onCleared() {
        super.onCleared()
    }
}