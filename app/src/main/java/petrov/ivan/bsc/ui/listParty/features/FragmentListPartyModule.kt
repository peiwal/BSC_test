package petrov.ivan.bsc.ui.listParty.features

import dagger.Module
import dagger.Provides
import petrov.ivan.bsc.ui.adapters.PartyAdapter

@Module
class FragmentListPartyModule(private val itemClickListener: PartyAdapter.PartyListener) {

    @Provides
    @FragmentListPartyScope
    fun partyAdapter(): PartyAdapter {
        return PartyAdapter(itemClickListener)
    }
}
