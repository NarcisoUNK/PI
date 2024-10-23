//package pt.ipbeja.pi.piproject.identificationInsect;
//
//import android.Manifest;
//import android.annotation.SuppressLint;
//import androidx.room.Room;
//import android.content.Context;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Matrix;
//import android.location.Location;
//import android.location.LocationManager;
//import android.media.ExifInterface;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Environment;
//import android.provider.MediaStore;
//import androidx.annotation.RequiresApi;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.FileProvider;
//import androidx.appcompat.app.AppCompatActivity;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.InputStream;
//import java.text.DateFormat;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//
//import pt.ipbeja.pi.piproject.startUpApp.MainActivity;
//import pt.ipbeja.pi.piproject.R;
//import pt.ipbeja.pi.piproject.persistence.Identification;
//import pt.ipbeja.pi.piproject.persistence.MyIdentificationsDb;
//import pt.ipbeja.pi.piproject.util.Coordinates;
//
//public class SaveIdentification extends AppCompatActivity {
//
//    private static final int PERMISSIONS_REQUEST_READ_LOCATION = 1;
//
//    private static final int REQUEST_LOAD_IMAGE= 1;
//    private static final int REQUEST_TAKE_PHOTO = 2;
//
//    private String resultId;
//    private String order;
//    private double latitude;
//    private double longitude;
//    private Date timestamp;
//    private Uri currentPhotoURI;
//    private Button maps;
//    private Button myIdsButton;
//    private ImageView img;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_save_identification);
//
//        Intent intent = getIntent();
//        this.resultId = intent.getStringExtra("fragmentID");
//
//        this.order = intent.getStringExtra("order");
//        TextView orderTxt = findViewById(R.id.orderTxt);
//        orderTxt.setText(this.order);
//
//        this.timestamp = new Date();
//        TextView timestampTxt = findViewById(R.id.timestampTxt);
//        timestampTxt.setText((new SimpleDateFormat()).format(timestamp));
//
//        this.img = (ImageView) findViewById(R.id.pictureView);
//        this.myIdsButton = (Button) findViewById(R.id.saveButton);
//
//        if (ActivityCompat.checkSelfPermission(this.getBaseContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getBaseContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                    PERMISSIONS_REQUEST_READ_LOCATION);
//        }
//        else
//        {
//            this.updateLocation();
//        }
//
//        maps = (Button) findViewById(R.id.maps);
//
//        maps.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(SaveIdentification.this, MapsActivity.class);
//                intent.putExtra("picture", currentPhotoURI);
//                intent.putExtra("resultid", resultId);
//                intent.putExtra("ordem", order);
//                intent.putExtra("latitude2", latitude);
//                intent.putExtra("longitude2", longitude);
//                startActivity(intent);
//            }
//        });
//
//        newCoordinates();
//    }
//
//    @SuppressLint("MissingPermission")
//    private void updateLocation()
//    {
//        LocationManager lm;
//        TextView coordsTxt = findViewById(R.id.coordsTxt);
//        Location lastLoc = null;
//
//        lm = (LocationManager)
//                this.getBaseContext().getSystemService(Context.LOCATION_SERVICE);
//
//        lastLoc = getLastKnownLocation();
//
//        if (lastLoc == null)
//        {
//            coordsTxt.setText("No location services enabled");
//            return;
//        }
//
//        this.latitude = lastLoc.getLatitude();
//        this.longitude = lastLoc.getLongitude();
//        coordsTxt.setText(Coordinates.anglesToDMS(this.latitude, this.longitude));
//    }
//
//    public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                                              int[] grantResults)
//    {
//        switch (requestCode) {
//            case PERMISSIONS_REQUEST_READ_LOCATION: {
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                    this.updateLocation();
//
//                } else {
//
//                    // finish();
//                }
//                return;
//            }
//        }
//    }
//
////    private void setInitialButtonState() {
////
////        new Thread(new Runnable() {
////            @Override
////            public void run() {
////
////                runOnUiThread(new Runnable() {
////                    @Override
////                    public void run() {
////                        if(img != null){
////                            myIdsButton.setEnabled(true);
////                        }
////                        else {
////                            myIdsButton.setEnabled(false);
////                        }
////                    }
////                });
////            }
////        }).start();
////    }
//
//    public void onSaveClick(View view)
//    {
//        new Thread()
//        {
//            @Override
//            public void run()
//            {
//                MyIdentificationsDb db = Room.databaseBuilder(SaveIdentification.this.getApplicationContext(), MyIdentificationsDb.class, "myidentifications.db").build();
//
//                Identification ident = new Identification(
//                        SaveIdentification.this.resultId,
//                        SaveIdentification.this.order,
//                        SaveIdentification.this.latitude,
//                        SaveIdentification.this.longitude,
//                        SaveIdentification.this.timestamp,
//                        SaveIdentification.this.currentPhotoURI.toString());
//
//                db.identificationDao().insertIdentification(ident);
//            }
//        }.start();
//
////        this.setResult(RESULT_OK);
////        this.finish();
//        /*Intent backIntent = new Intent(SaveIdentification.this, MainActivity.class);
//        startActivity(backIntent);*/
//
//        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//    }
//
//    /**
//     * Handles the button for taking a picture
//     *
//     * @param nextView not used
//     */
//    public void onClickTakePicture(View nextView) {
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        // Ensure that there's a camera activity to handle the intent
//
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//
//            // Create the File where the photo should go
//            File photoFile = null;
//            try {
//                photoFile = createTmpImageFile();
//
//                // Continue only if the File was successfully created
//                if (photoFile != null) {
//                    Uri photoURI = FileProvider.getUriForFile(this,
//                            "pt.ipbeja.pi.piproject.fileprovider",
//                            photoFile);
//                    //Store the image's URI to be retrieved later to display
//                    this.currentPhotoURI = photoURI;
//                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
//                }
//            } catch (IOException ex) {
//                // Error occurred while creating the File
//
//            }
//        }
//    }
//
//    public void onClickLoadPicture(View view)
//    {
//        //Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
//        Intent photoPickerIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
//        photoPickerIntent.setType("image/*");
//        startActivityForResult(photoPickerIntent, REQUEST_LOAD_IMAGE);
//    }
//
//    /**
//     * Handles the result of starting activities with results
//     *
//     * @param requestCode
//     * @param resultCode
//     * @param data
//     */
//    @RequiresApi(api = Build.VERSION_CODES.N)
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        // Check which request we're responding to
//        if (requestCode == REQUEST_TAKE_PHOTO) {
//            // The request to take a picture
//            if (resultCode == RESULT_OK) {
//                try{
//                    InputStream is = getContentResolver().openInputStream(this.currentPhotoURI);
//                    Bitmap bitmap = BitmapFactory.decodeStream(is);
//                    Bitmap resized = resizeBitmap(bitmap, 300, 400);
//                    bitmap.recycle();
//                    img.setImageBitmap(resized);
//                    myIdsButton.setEnabled(true);
////                    this.setInitialButtonState();
//                }
//                catch(FileNotFoundException e)
//                {
//                    e.printStackTrace();
//                }
//                // this.listingImage = resized;
//            }
//        }
//        else if (requestCode == REQUEST_LOAD_IMAGE)
//        {
//            if (resultCode == RESULT_OK) {
//                try {
//                    final Uri imageUri = data.getData();
//                    this.currentPhotoURI = imageUri;
//                    final InputStream imageStream = getContentResolver().openInputStream(imageUri);
//                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
//
//                    ImageView img = (ImageView) findViewById(R.id.pictureView);
//                    img.setImageBitmap(selectedImage);
//
//                    //Prepare EXIF Interface
//                    final InputStream is = getContentResolver().openInputStream(imageUri);
//                    ExifInterface exif = new ExifInterface(is);
//
//                    //Retrieve location
//                    float[] latlng = {360.0f, 360.0f};
//                    exif.getLatLong(latlng);
//
//                    myIdsButton.setEnabled(true);
////                    this.setInitialButtonState();
//                    //check if the image contains a location
//                    if(Float.compare(latlng[0], 360f) != 0 && Float.compare(latlng[1], 360f) != 0)
//                    {
//                        Toast.makeText(this, R.string.coords_found, Toast.LENGTH_LONG).show();
//
//                        this.latitude = latlng[0];
//                        this.longitude = latlng[1];
//
//                        TextView coordsTxt = findViewById(R.id.coordsTxt);
//                        coordsTxt.setText(Coordinates.anglesToDMS(this.latitude, this.longitude));
//
//                        //Retrieve the image's date
//                        String date = exif.getAttribute(ExifInterface.TAG_DATETIME);
//                        DateFormat df = new SimpleDateFormat("yyyy:MM:dd hh:mm:ss");
//
//                        this.timestamp = df.parse(date);
//                        TextView dateTxt = findViewById(R.id.timestampTxt);
//                        dateTxt.setText((new SimpleDateFormat()).format(timestamp));
//
//
//                    }
//                    else
//                    {
//                        Toast.makeText(this, R.string.no_coords, Toast.LENGTH_LONG).show();
//                    }
//
//                } catch (IOException | ParseException e) {
//                    e.printStackTrace();
//                    Toast.makeText(this, R.string.io_exception, Toast.LENGTH_LONG).show();
//                }
//
//            }else {
//                Toast.makeText(this, R.string.no_image_picked,Toast.LENGTH_LONG).show();
//            }
//        }
//
//    }
//
//    /**
//     * Resize the image taken by the camera
//     *
//     * @param bitmap bitmap of the image
//     * @param newWidth new width
//     * @param newHeight new height
//     * @return new resized bitmap
//     */
//    private Bitmap resizeBitmap(Bitmap bitmap, int newWidth, int newHeight) {
//        int width = bitmap.getWidth();
//        int height = bitmap.getHeight();
//        float scaleWidth = ((float) newWidth) / width;
//        float scaleHeight = ((float) newHeight) / height;
//        // CREATE A MATRIX FOR THE MANIPULATION
//        Matrix matrix = new Matrix();
//        // RESIZE THE BIT MAP
//        matrix.postScale(scaleWidth, scaleHeight);
//
//        // "RECREATE" THE NEW BITMAP
//        Bitmap resizedBitmap = Bitmap.createBitmap(
//                bitmap, 0, 0, width, height, matrix, false);
//
//        return resizedBitmap;
//    }
//
//    /**
//     * Creates an a file ready to receive a picture
//     *
//     * @return the created file
//     * @throws IOException
//     */
//    private File createTmpImageFile() throws IOException {
//        // Create an image file name
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = "tmp";
//        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//
//        // If the parent dir doesn't exist, create it
//        if (!storageDir.exists()) {
//            if (storageDir.getParentFile().mkdirs()) {
//                //Log.d(TAG, "Successfully created the parent dir:" + parentDir.getName());
//            } else {
//                //Log.d(TAG, "Failed to create the parent dir:" + parentDir.getName());
//            }
//        }
//
//        File image = File.createTempFile(
//                imageFileName,  /* prefix */
//                ".jpg",    /* suffix */
//                storageDir      /* directory */
//        );
//
//        return image;
//    }
//
//    /**
//     * reference: https://stackoverflow.com/questions/20438627/getlastknownlocation-returns-null/20465781#20465781
//     *
//     * @return
//     */
//    @SuppressLint("MissingPermission")
//    private Location getLastKnownLocation() {
//        LocationManager mLocationManager = (LocationManager)getApplicationContext().getSystemService(LOCATION_SERVICE);
//        List<String> providers = mLocationManager.getProviders(true);
//        Location bestLocation = null;
//        for (String provider : providers) {
//            Location l = mLocationManager.getLastKnownLocation(provider);
//            if (l == null) {
//                continue;
//            }
//            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
//                // Found best last known location: %s", l);
//                bestLocation = l;
//            }
//        }
//        return bestLocation;
//    }
//
//    public void newCoordinates() {
//
//        if(getIntent().getExtras().getInt("extra1") == 1){
//
//            double newLatitude = (double) getIntent().getExtras().get("novaLatitude");
//            double newLongitude = (double) getIntent().getExtras().get("novaLongitude");
//            this.currentPhotoURI = (Uri) getIntent().getExtras().get("picturefinal");
//            this.order = (String) getIntent().getExtras().get("ordemfinal");
//            this.resultId = (String) getIntent().getExtras().get("resultidfinal");
//
//            TextView coordsTxt2 = findViewById(R.id.coordsTxt);
//            coordsTxt2.setText(Coordinates.anglesToDMS(newLatitude, newLongitude));
//
//            TextView orderTxt2 = findViewById(R.id.orderTxt);
//            orderTxt2.setText(order);
//
//            ImageView img2 = findViewById(R.id.pictureView);
//            img2.setImageURI(currentPhotoURI);
//
//            if(currentPhotoURI != null){
//                myIdsButton.setEnabled(true);
//            }
////            this.setInitialButtonState();
//
//
//            this.latitude = newLatitude;
//            this.longitude = newLongitude;
//        }
//    }
//}
