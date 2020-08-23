package petrov.ivan.bsc.ui.listParty.features

import dagger.Component
import petrov.ivan.bsc.ui.adapters.PartyAdapter

@Component(modules = arrayOf(FragmentListPartyModule::class))
@FragmentListPartyScope
interface FragmentListPartyComponent {
    fun getPartyAdapter(): PartyAdapter
}