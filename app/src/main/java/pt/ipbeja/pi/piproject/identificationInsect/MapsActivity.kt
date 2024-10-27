package pt.ipbeja.pi.piproject.identificationInsect

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GooglePlayServicesUtil
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import pt.ipbeja.pi.piproject.R

class MapsActivity : FragmentActivity(), OnMapReadyCallback {
    private var mMap: GoogleMap? = null
    private var save: Button? = null
    private var counter = 1

    // Creating a marker
    var markerFixed: Marker? = null
    var markerNew: Marker? = null
    var markerOptions2: MarkerOptions = MarkerOptions()
    private var order: String? = null
    private var resultid2: String? = null
    private var picture2: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(applicationContext)

        if (status == ConnectionResult.SUCCESS) {
            val mapFragment =
                supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
            mapFragment!!.getMapAsync(this)
        } else {
            val dialog =
                GooglePlayServicesUtil.getErrorDialog(status, applicationContext as Activity, 10)
            dialog?.show()
        }

        save = findViewById<View>(R.id.savebtn) as Button

        save!!.setOnClickListener {
            val intent = Intent(this@MapsActivity, SaveIdentification::class.java)
            intent.putExtra("picturefinal", picture2)
            intent.putExtra("resultidfinal", resultid2)
            intent.putExtra("ordemfinal", order)
            intent.putExtra("novaLatitude", markerNew!!.position.latitude)
            intent.putExtra("novaLongitude", markerNew!!.position.longitude)
            intent.putExtra("extra1", 1)
            //finish();
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        this.picture2 = intent.extras!!["picture"] as Uri?
        this.resultid2 = intent.extras!!["resultid"] as String?
        this.order = intent.extras!!["ordem"] as String?


        mMap = googleMap

        mMap!!.mapType = GoogleMap.MAP_TYPE_SATELLITE

        val uiSettings = mMap!!.uiSettings
        uiSettings.isZoomControlsEnabled = true

        // Add a marker in insect location and move the camera
        val beja = LatLng(
            intent.extras!!["latitude2"] as Double, intent.extras!!["longitude2"] as Double
        )


        markerFixed = mMap!!.addMarker(MarkerOptions().position(beja).title("Localização Foto!"))

        val zoomlevel = 16f

        mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(beja, zoomlevel))



        googleMap.setOnMapClickListener { latLng ->
            if (markerNew != null) {
                markerNew!!.remove()
            }
            googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))


            markerNew = mMap!!.addMarker(
                MarkerOptions().icon(
                    BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)
                ).position(latLng).title(latLng.latitude.toString() + " : " + latLng.longitude)
            )

            //
            //                markerOptions2.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
            //
            //                // Setting the position for the marker
            //                markerOptions2.position(latLng);
            //
            //                // Setting the title for the marker.
            //                // This will be displayed on taping the marker
            //                markerOptions2.title(latLng.latitude + " : " + latLng.longitude);
            //
            //
            ////                 Clears the previously touched position
            //
            //
            ////                 Animating to the touched position
            //                googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            //
            ////                 Placing a marker on the touched position
            //                googleMap.addMarker(markerOptions2);
        }
    }

    fun onClickchangeView(view: View?) {
        if (counter % 2 == 0) {
            mMap!!.mapType = GoogleMap.MAP_TYPE_SATELLITE
            counter++
        } else {
            mMap!!.mapType = GoogleMap.MAP_TYPE_NORMAL
            counter++
        }
    }

    fun onClickBack(view: View?) {
        val intent = Intent(this@MapsActivity, SaveIdentification::class.java)
        startActivity(intent)
    }
}