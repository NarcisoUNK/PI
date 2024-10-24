//package pt.ipbeja.pi.piproject.identificationInsect;
//
//import android.app.Activity;
//import android.app.Dialog;
//import android.content.Intent;
//import android.net.Uri;
//import androidx.fragment.app.FragmentActivity;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//
//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.GooglePlayServicesUtil;
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.UiSettings;
//import com.google.android.gms.maps.model.BitmapDescriptorFactory;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.Marker;
//import com.google.android.gms.maps.model.MarkerOptions;
//
//import pt.ipbeja.pi.piproject.R;
//import pt.ipbeja.pi.piproject.persistence.Identification;
//
//public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
//
//    private GoogleMap mMap;
//    private Button save;
//    private int counter = 1;
//    // Creating a marker
//    public Marker markerFixed;
//    public Marker markerNew;
//    MarkerOptions markerOptions2 = new MarkerOptions();
//    private String order;
//    private String resultid2;
//    private Uri picture2;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_maps);
//        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
//
//        if(status == ConnectionResult.SUCCESS){
//            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
//            mapFragment.getMapAsync(this);
//        }
//        else {
//            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, (Activity)getApplicationContext(), 10);
//            dialog.show();
//        }
//
//        save = (Button) findViewById(R.id.savebtn);
//
//        save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MapsActivity.this, SaveIdentification.class);
//                intent.putExtra("picturefinal", picture2);
//                intent.putExtra("resultidfinal", resultid2);
//                intent.putExtra("ordemfinal",order);
//                intent.putExtra("novaLatitude", markerNew.getPosition().latitude);
//                intent.putExtra("novaLongitude", markerNew.getPosition().longitude);
//                intent.putExtra("extra1", 1);
//                //finish();
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//
//            }
//        });
//
//    }
//
//
//    /**
//     * Manipulates the map once available.
//     * This callback is triggered when the map is ready to be used.
//     * This is where we can add markers or lines, add listeners or move the camera. In this case,
//     * we just add a marker near Sydney, Australia.
//     * If Google Play services is not installed on the device, the user will be prompted to install
//     * it inside the SupportMapFragment. This method will only be triggered once the user has
//     * installed Google Play services and returned to the app.
//     */
//    @Override
//    public void onMapReady(final GoogleMap googleMap) {
//
//        this.picture2 = (Uri)getIntent().getExtras().get("picture");
//        this.resultid2 = (String)getIntent().getExtras().get("resultid");
//        this.order = (String)getIntent().getExtras().get("ordem");
//
//
//        mMap = googleMap;
//
//        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
//
//        UiSettings uiSettings = mMap.getUiSettings();
//        uiSettings.setZoomControlsEnabled(true);
//
//        // Add a marker in insect location and move the camera
//        LatLng beja = new LatLng((double)getIntent().getExtras().get("latitude2"), (double)getIntent().getExtras().get("longitude2") );
//
//
//        markerFixed = mMap.addMarker(new MarkerOptions().position(beja).title("Localização Foto!"));
//
//        float zoomlevel = 16;
//
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(beja, zoomlevel));
//
//
//
//        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//
//            @Override
//            public void onMapClick(LatLng latLng) {
//
//
//                if(markerNew != null){
//                    markerNew.remove();
//                }
//
//                googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
//
//
//                markerNew = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)).position(latLng).title(latLng.latitude + " : " + latLng.longitude));
//
////
////                markerOptions2.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
////
////                // Setting the position for the marker
////                markerOptions2.position(latLng);
////
////                // Setting the title for the marker.
////                // This will be displayed on taping the marker
////                markerOptions2.title(latLng.latitude + " : " + latLng.longitude);
////
////
//////                 Clears the previously touched position
////
////
//////                 Animating to the touched position
////                googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
////
//////                 Placing a marker on the touched position
////                googleMap.addMarker(markerOptions2);
//            }
//        });
//    }
//
//    public void onClickchangeView(View view)
//    {
//        if(counter%2 == 0){
//            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
//            counter++;
//        }
//        else{
//            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//            counter++;
//        }
//    }
//
//    public void onClickBack(View view) {
//        Intent intent = new Intent(MapsActivity.this, SaveIdentification.class);
//        startActivity(intent);
//    }
//}
