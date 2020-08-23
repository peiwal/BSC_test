package petrov.ivan.bsc.ui.listParty

import android.Manifest.permission.READ_CONTACTS
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import petrov.ivan.bsc.R
import petrov.ivan.bsc.databinding.FragmentListPartyBinding
import petrov.ivan.bsc.service.ApiService
import petrov.ivan.bsc.ui.adapters.PartyAdapter
import petrov.ivan.bsc.ui.base.BaseFragmentViewModel
import petrov.ivan.bsc.ui.listParty.features.DaggerFragmentListPartyComponent
import petrov.ivan.bsc.ui.listParty.features.FragmentListPartyComponent
import petrov.ivan.bsc.ui.listParty.features.FragmentListPartyModule
import petrov.ivan.bsc.utils.RuntimePermissionHelper
import petrov.ivan.bsc.utils.RuntimePermissionHelper.Companion.PERMISSIONS_REQUEST_CODE


class FragmentListParty: BaseFragmentViewModel() {

    private val runtimePermissionHelper: RuntimePermissionHelper by lazy(mode = LazyThreadSafetyMode.NONE) {
        RuntimePermissionHelper(this)
    }
    private lateinit var binding: FragmentListPartyBinding
    private lateinit var viewModel: ListPartyViewModel
    private val itemClickListener = PartyAdapter.PartyListener { party ->
        this.findNavController().navigate(
            FragmentListPartyDirections.actionFragmentListPartyToFragmentPlaceInfo(party)
        )
    }
    private val fragmentListPartyComponent: FragmentListPartyComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        DaggerFragmentListPartyComponent.builder()
            .fragmentListPartyModule(FragmentListPartyModule(itemClickListener))
            .build()
    }
    private val apiService: ApiService by lazy(mode = LazyThreadSafetyMode.NONE) {
        applicationComponents.getApiService()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_list_party, container, false)

        return binding.root
    }

    override fun createViewModel() {
        val viewModelFactory = ListPartyViewModelFactory(apiService, application)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(ListPartyViewModel::class.java)

        binding.viewModel = viewModel
    }

    override fun registerObservers() {
        viewModel.let {
            it.eventLoadComplete.observe(this, Observer { value ->
                if (value) onLoadDataComplete()
            })

            it.eventErrorLoadData.observe(this, Observer { value ->
                if (value) {
                    setRefreshShowing(false)
                    Snackbar.make(binding.root, R.string.error_load_data, Snackbar.LENGTH_LONG).show()
                    viewModel.eventErrorLoadData.value = false
                }
            })

            it.listParty.observe(this, Observer {
                (binding.recyclerView.adapter as PartyAdapter).items = ArrayList(it)
            })
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.swipeRefreshLayout.setOnRefreshListener() {
            viewModel.recyclerViewPosition.value = 0
            loadData()
        }

        binding.recyclerView.adapter = fragmentListPartyComponent.getPartyAdapter()
        viewModel.listParty.value ?:  run {
            if (runtimePermissionHelper.isPermissionGranted(READ_CONTACTS)) {
                loadData()
            } else {
                runtimePermissionHelper.requestPermissions(PERMISSIONS_REQUEST_CODE, arrayOf(READ_CONTACTS))
            }
        }
    }

    private fun loadData() {
        setRefreshShowing(true)
        viewModel.loadData()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            permissions.forEach { permission ->
                val grandResult = grantResults[permissions.indexOf(permission)]
                // granted read contacts
                if (grandResult == PackageManager.PERMISSION_GRANTED && permission == READ_CONTACTS) {
                    loadData()
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        saveScrollPosition()
    }


    private fun onLoadDataComplete() {
        restoreScrollPostion()
        setRefreshShowing(false)
    }

    private fun setRefreshShowing(isShowing: Boolean) {
        binding.swipeRefreshLayout.setRefreshing(isShowing)
    }

    private fun saveScrollPosition() {
        if (isAdded) {
            viewModel.recyclerViewPosition.value =
                (binding.recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        }
    }

    private fun restoreScrollPostion() {
        viewModel.recyclerViewPosition.value?.let { position ->
            binding.recyclerView.layoutManager?.scrollToPosition(position)
        }
    }
}