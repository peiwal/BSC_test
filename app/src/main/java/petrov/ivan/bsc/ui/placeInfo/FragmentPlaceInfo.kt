package petrov.ivan.bsc.ui.placeInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import petrov.ivan.bsc.R
import petrov.ivan.bsc.data.Party
import petrov.ivan.bsc.databinding.FragmentPlaceInfoBinding
import petrov.ivan.bsc.ui.base.BaseFragmentViewModel
import timber.log.Timber


class FragmentPlaceInfo : BaseFragmentViewModel(), OnMapReadyCallback {
    private lateinit var party: Party
    private lateinit var placeInfoViewModel: PlaceInfoViewModel
    private lateinit var binding: FragmentPlaceInfoBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_place_info, container, false)

        arguments?.let {
            party = FragmentPlaceInfoArgs.fromBundle(it).party
        } ?: Timber.e("incorrect state: arguments not found")

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setMapFragment()
    }

    override fun createViewModel() {
        val viewModelFactory = PlaceInfoViewModelFactory(application, party)

        placeInfoViewModel = ViewModelProviders.of(this, viewModelFactory).get(PlaceInfoViewModel::class.java)
        binding.viewModel = placeInfoViewModel
    }

    override fun registerObservers() {
    }

    override fun getTitle(): String {
        return application.getString(R.string.frmItemTitle)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val latLng = with(party.place.coord) { LatLng(this.lat, this.lon) }
        googleMap.apply {
            addMarker(
                MarkerOptions()
                    .position(latLng)
            )
            moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, CAMERA_ZOOM))
        }
    }

    private fun setMapFragment() {
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    companion object {
        private const val CAMERA_ZOOM = 15f
    }
}