package pt.ipbeja.pi.piproject.identificationInsect

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.location.Location
import android.location.LocationManager
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.room.Room
import pt.ipbeja.pi.piproject.R
import pt.ipbeja.pi.piproject.persistence.Identification
import pt.ipbeja.pi.piproject.persistence.MyIdentificationsDb
import pt.ipbeja.pi.piproject.startUpApp.MainActivity
import pt.ipbeja.pi.piproject.util.Coordinates
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.lang.Float
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date

class SaveIdentification : AppCompatActivity() {
    private var resultId: String? = null
    private var order: String? = null
    private var latitude = 0.0
    private var longitude = 0.0
    private var timestamp: Date? = null
    private var currentPhotoURI: Uri? = null
    private var maps: Button? = null
    private var myIdsButton: Button? = null
    private var img: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save_identification)

        val intent = intent
        this.resultId = intent.getStringExtra("fragmentID")

        this.order = intent.getStringExtra("order")
        val orderTxt = findViewById<TextView>(R.id.orderTxt)
        orderTxt.text = order

        this.timestamp = Date()
        val timestampTxt = findViewById<TextView>(R.id.timestampTxt)
        timestampTxt.text = (SimpleDateFormat()).format(timestamp)

        this.img = findViewById<View>(R.id.pictureView) as ImageView
        this.myIdsButton = findViewById<View>(R.id.saveButton) as Button

        if (ActivityCompat.checkSelfPermission(
                this.baseContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this.baseContext, Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_READ_LOCATION
            )
        } else {
            this.updateLocation()
        }

        maps = findViewById<View>(R.id.maps) as Button

        maps!!.setOnClickListener {
            val intent = Intent(this@SaveIdentification, MapsActivity::class.java)
            intent.putExtra("picture", currentPhotoURI)
            intent.putExtra("resultid", resultId)
            intent.putExtra("ordem", order)
            intent.putExtra("latitude2", latitude)
            intent.putExtra("longitude2", longitude)
            startActivity(intent)
        }

        newCoordinates()
    }

    @SuppressLint("MissingPermission")
    private fun updateLocation() {
        val coordsTxt = findViewById<TextView>(R.id.coordsTxt)
        var lastLoc: Location? = null

        val lm = this.baseContext.getSystemService(LOCATION_SERVICE) as LocationManager

        lastLoc = lastKnownLocation

        if (lastLoc == null) {
            coordsTxt.text = "No location services enabled"
            return
        }

        this.latitude = lastLoc.latitude
        this.longitude = lastLoc.longitude
        coordsTxt.text = Coordinates.anglesToDMS(this.latitude, this.longitude)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSIONS_REQUEST_READ_LOCATION -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.size > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    this.updateLocation()
                } else {
                    // finish();
                }
                return
            }
        }
    }

    //    private void setInitialButtonState() {
    //
    //        new Thread(new Runnable() {
    //            @Override
    //            public void run() {
    //
    //                runOnUiThread(new Runnable() {
    //                    @Override
    //                    public void run() {
    //                        if(img != null){
    //                            myIdsButton.setEnabled(true);
    //                        }
    //                        else {
    //                            myIdsButton.setEnabled(false);
    //                        }
    //                    }
    //                });
    //            }
    //        }).start();
    //    }
    fun onSaveClick(view: View?) {
        object : Thread() {
            override fun run() {
                val db = Room.databaseBuilder(
                    this@SaveIdentification.applicationContext,
                    MyIdentificationsDb::class.java, "myidentifications.db"
                ).build()

                val ident = this@SaveIdentification.resultId?.let {
                    this@SaveIdentification.order?.let { it1 ->
                        this@SaveIdentification.timestamp?.let { it2 ->
                            Identification(
                                it,
                                it1,
                                this@SaveIdentification.latitude,
                                this@SaveIdentification.longitude,
                                it2,
                                currentPhotoURI.toString()
                            )
                        }
                    }
                }

                if (ident != null) {
                    db.identificationDao()?.insertIdentification(ident)
                }
            }
        }.start()

        //        this.setResult(RESULT_OK);
//        this.finish();
        /*Intent backIntent = new Intent(SaveIdentification.this, MainActivity.class);
        startActivity(backIntent);*/
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    /**
     * Handles the button for taking a picture
     *
     * @param nextView not used
     */
    fun onClickTakePicture(nextView: View?) {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            // Create the File where the photo should go

            var photoFile: File? = null
            try {
                photoFile = createTmpImageFile()

                // Continue only if the File was successfully created
                if (photoFile != null) {
                    val photoURI = FileProvider.getUriForFile(
                        this,
                        "pt.ipbeja.pi.piproject.fileprovider",
                        photoFile
                    )
                    //Store the image's URI to be retrieved later to display
                    this.currentPhotoURI = photoURI
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
                }
            } catch (ex: IOException) {
                // Error occurred while creating the File
            }
        }
    }

    fun onClickLoadPicture(view: View?) {
        //Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        val photoPickerIntent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        photoPickerIntent.setType("image/*")
        startActivityForResult(photoPickerIntent, REQUEST_LOAD_IMAGE)
    }

    /**
     * Handles the result of starting activities with results
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Check which request we're responding to
        if (requestCode == REQUEST_TAKE_PHOTO) {
            // The request to take a picture
            if (resultCode == RESULT_OK) {
                try {
                    val `is` = contentResolver.openInputStream(
                        currentPhotoURI!!
                    )
                    val bitmap = BitmapFactory.decodeStream(`is`)
                    val resized = resizeBitmap(bitmap, 300, 400)
                    bitmap.recycle()
                    img!!.setImageBitmap(resized)
                    myIdsButton!!.isEnabled = true
                    //                    this.setInitialButtonState();
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                }
                // this.listingImage = resized;
            }
        } else if (requestCode == REQUEST_LOAD_IMAGE) {
            if (resultCode == RESULT_OK) {
                try {
                    val imageUri = data?.data
                    this.currentPhotoURI = imageUri
                    val imageStream = contentResolver.openInputStream(
                        imageUri!!
                    )
                    val selectedImage = BitmapFactory.decodeStream(imageStream)

                    val img = findViewById<View>(R.id.pictureView) as ImageView
                    img.setImageBitmap(selectedImage)

                    //Prepare EXIF Interface
                    val `is` = contentResolver.openInputStream(imageUri)
                    val exif = ExifInterface(`is`!!)

                    //Retrieve location
                    val latlng = floatArrayOf(360.0f, 360.0f)
                    exif.getLatLong(latlng)

                    myIdsButton!!.isEnabled = true
                    //                    this.setInitialButtonState();
                    //check if the image contains a location
                    if (Float.compare(latlng[0], 360f) != 0 && Float.compare(
                            latlng[1], 360f
                        ) != 0
                    ) {
                        Toast.makeText(this, R.string.coords_found, Toast.LENGTH_LONG).show()

                        this.latitude = latlng[0].toDouble()
                        this.longitude = latlng[1].toDouble()

                        val coordsTxt = findViewById<TextView>(R.id.coordsTxt)
                        coordsTxt.text =
                            Coordinates.anglesToDMS(this.latitude, this.longitude)

                        //Retrieve the image's date
                        val date = exif.getAttribute(ExifInterface.TAG_DATETIME)
                        val df: DateFormat = SimpleDateFormat("yyyy:MM:dd hh:mm:ss")

                        this.timestamp = df.parse(date)
                        val dateTxt = findViewById<TextView>(R.id.timestampTxt)
                        dateTxt.text = (SimpleDateFormat()).format(timestamp)
                    } else {
                        Toast.makeText(this, R.string.no_coords, Toast.LENGTH_LONG).show()
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(this, R.string.io_exception, Toast.LENGTH_LONG).show()
                } catch (e: ParseException) {
                    e.printStackTrace()
                    Toast.makeText(this, R.string.io_exception, Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, R.string.no_image_picked, Toast.LENGTH_LONG).show()
            }
        }
    }

    /**
     * Resize the image taken by the camera
     *
     * @param bitmap bitmap of the image
     * @param newWidth new width
     * @param newHeight new height
     * @return new resized bitmap
     */
    private fun resizeBitmap(bitmap: Bitmap, newWidth: Int, newHeight: Int): Bitmap {
        val width = bitmap.width
        val height = bitmap.height
        val scaleWidth = (newWidth.toFloat()) / width
        val scaleHeight = (newHeight.toFloat()) / height
        // CREATE A MATRIX FOR THE MANIPULATION
        val matrix = Matrix()
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight)

        // "RECREATE" THE NEW BITMAP
        val resizedBitmap = Bitmap.createBitmap(
            bitmap, 0, 0, width, height, matrix, false
        )

        return resizedBitmap
    }

    /**
     * Creates an a file ready to receive a picture
     *
     * @return the created file
     * @throws IOException
     */
    @Throws(IOException::class)
    private fun createTmpImageFile(): File {
        // Create an image file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "tmp"
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        // If the parent dir doesn't exist, create it
        if (!storageDir!!.exists()) {
            if (storageDir.parentFile.mkdirs()) {
                //Log.d(TAG, "Successfully created the parent dir:" + parentDir.getName());
            } else {
                //Log.d(TAG, "Failed to create the parent dir:" + parentDir.getName());
            }
        }

        val image = File.createTempFile(
            imageFileName,  /* prefix */
            ".jpg",  /* suffix */
            storageDir /* directory */
        )

        return image
    }

    @get:SuppressLint("MissingPermission")
    private val lastKnownLocation: Location?
        /**
         * reference: https://stackoverflow.com/questions/20438627/getlastknownlocation-returns-null/20465781#20465781
         *
         * @return
         */
        get() {
            val mLocationManager =
                applicationContext.getSystemService(LOCATION_SERVICE) as LocationManager
            val providers = mLocationManager.getProviders(true)
            var bestLocation: Location? = null
            for (provider in providers) {
                val l = mLocationManager.getLastKnownLocation(provider) ?: continue
                if (bestLocation == null || l.accuracy < bestLocation.accuracy) {
                    // Found best last known location: %s", l);
                    bestLocation = l
                }
            }
            return bestLocation
        }

    fun newCoordinates() {
        if (intent.extras!!.getInt("extra1") == 1) {
            val newLatitude = intent.extras!!["novaLatitude"] as Double
            val newLongitude = intent.extras!!["novaLongitude"] as Double
            this.currentPhotoURI = intent.extras!!["picturefinal"] as Uri?
            this.order = intent.extras!!["ordemfinal"] as String?
            this.resultId = intent.extras!!["resultidfinal"] as String?

            val coordsTxt2 = findViewById<TextView>(R.id.coordsTxt)
            coordsTxt2.text = Coordinates.anglesToDMS(newLatitude, newLongitude)

            val orderTxt2 = findViewById<TextView>(R.id.orderTxt)
            orderTxt2.text = order

            val img2 = findViewById<ImageView>(R.id.pictureView)
            img2.setImageURI(currentPhotoURI)

            if (currentPhotoURI != null) {
                myIdsButton!!.isEnabled = true
            }


            //            this.setInitialButtonState();
            this.latitude = newLatitude
            this.longitude = newLongitude
        }
    }

    companion object {
        private const val PERMISSIONS_REQUEST_READ_LOCATION = 1

        private const val REQUEST_LOAD_IMAGE = 1
        private const val REQUEST_TAKE_PHOTO = 2
    }
}