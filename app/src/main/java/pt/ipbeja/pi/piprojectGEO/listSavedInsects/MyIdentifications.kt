package pt.ipbeja.pi.piprojectGEO.listSavedInsects

import android.content.Intent
import android.graphics.Color
import android.graphics.text.LineBreaker
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.room.Room
import pt.ipbeja.pi.piproject.R
import pt.ipbeja.pi.piproject.idkey.IdentificationKey
import pt.ipbeja.pi.piproject.persistence.Identification
import pt.ipbeja.pi.piproject.persistence.MyIdentificationsDb
import pt.ipbeja.pi.piproject.util.Util
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Calendar

//import android.media.ExifInterface;
class MyIdentifications : AppCompatActivity() {
    private lateinit var go: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_identifications)

        this.loadList()

        go = findViewById(R.id.button12)

        go.setOnClickListener(View.OnClickListener { finish() })
    }

    private fun loadList() {
        object : Thread() {
            override fun run() {
                val db = Room.databaseBuilder(
                    this@MyIdentifications.applicationContext,
                    MyIdentificationsDb::class.java,
                    "myidentifications.db"
                )
                    .build()

                val idents = db.identificationDao()?.allIdentifications

                // ArrayAdapter adapter = new ArrayAdapter<String>(this,R.id.identsLst , idents);

                // Construct the data source
                val arrayOfListings = ArrayList<Identification?>()
                // Create the adapter to convert the array to views
                val adapter = IdentificationAdapter(this@MyIdentifications, arrayOfListings)

                class ListAdderRunnable(
                    var toAdd: Array<Identification?>?,
                    var adapter: IdentificationAdapter
                ) :
                    Runnable {
                    override fun run() {
                        // Attach the adapter to a ListView
                        val identsLst = findViewById<View>(R.id.identsLst) as ListView
                        identsLst.adapter = adapter

                        for (ident in toAdd!!) {
                            adapter.add(ident)
                        }

                        setListener(identsLst)
                    }

                    // https://stackoverflow.com/questions/17851687/how-to-handle-the-click-event-in-listview-in-android
                    fun setListener(insectsListView: ListView) {
                        insectsListView.onItemClickListener =
                            OnItemClickListener { parent, view, position, id ->
                                val item =
                                    insectsListView.getItemAtPosition(position) as Identification
                                //changeColor(view, Color.GRAY);
                                view.setBackgroundColor(Color.LTGRAY)

                                //showDialogDeleteSend(identsLst, item);
                                showPopupMenu(insectsListView, view, item)
                                //changeColor(view, Color.WHITE);
                            }
                    }


                    fun showPopupMenu(
                        insectsListView: ListView,
                        view: View,
                        identification: Identification
                    ) {
                        // https://developer.android.com/guide/topics/ui/menus#PopupMenu

//                        String[] mobileArray = {"Android", "IPhone", "WindowsMobile", "Blackberry",
//                                "WebOS", "Ubuntu", "Windows7", "Max OS X"};
//
//
//                        ArrayAdapter adapter = new ArrayAdapter<String>(this,
//                                R.layout.itemsActions_listview, mobileArray);
//
//                        list = (ListView) findViewById(R.id.mobile_list);
//                        list.setAdapter(adapter);

                        val popup = PopupMenu(this@MyIdentifications, view)

                        popup.menuInflater.inflate(R.menu.myinsects_action_menu, popup.menu)
                        popup.setOnDismissListener { view.setBackgroundColor(Color.RED) }
                        popup.setOnMenuItemClickListener(object :
                            PopupMenu.OnMenuItemClickListener {
                            override fun onMenuItemClick(item: MenuItem): Boolean {
                                when (item.itemId) {
                                    R.id.idmoreinfo -> {
                                        showMoreInformation(identification)
                                        return true
                                    }

                                    R.id.idorder -> {
                                        changeOrder()
                                        return true
                                    }

                                    R.id.idsend -> {
                                        sendEmailWithImageFromList(identification)
                                        return true
                                    }

                                    R.id.iddelete -> {
                                        deleteItem(item, identification)
                                        return true
                                    }

                                    R.id.idcancel -> return true
                                    else -> return false
                                }
                            }

                            fun showMoreInformation(identification: Identification) {
                                val builder = AlertDialog.Builder(this@MyIdentifications)

                                builder.setTitle(R.string.moreinfotext)
                                val resultId = identification.keyId
                                try {
                                    val description: String = IdentificationKey.getInstance(
                                        this@MyIdentifications
                                    )!!
                                        .getResult(resultId)!!.description
                                    builder.setMessage(Util.removeSpaces(description))
                                    builder.setNeutralButton(
                                        "Close"
                                    ) { dialog, which ->
                                        // nothing to do. Just close
                                    }
                                    builder.setPositiveButton(
                                        "+Info"
                                    ) { dialogInterface, wich ->
                                        val browserIntent = Intent(
                                            Intent.ACTION_VIEW,
                                            Uri.parse("https://drive.google.com/file/d/0B1REHtVuyBybX1BRcGRrWnc5S28/view")
                                        )
                                        startActivity(browserIntent)
                                    }
                                    val alert = builder.create()
                                    alert.show()
                                    val messageText =
                                        checkNotNull(alert.findViewById<View>(android.R.id.message) as TextView?)
                                    messageText.justificationMode =
                                        LineBreaker.JUSTIFICATION_MODE_INTER_WORD
                                } catch (e: IOException) {
                                    e.printStackTrace()
                                }
                            }

                            fun changeOrder() {
                                val m = OrderPopupMenu(
                                    this@MyIdentifications,
                                    view, identification, db, idents,
                                    adapter, insectsListView
                                )
                                m.show()
                            }

                            fun deleteItem(item: MenuItem?, identification: Identification?) {
                                object : Thread() {
                                    override fun run() {
                                        if (identification != null) {
                                            db.identificationDao()?.deleteIdentification(identification)
                                        }
                                        //arrayOfListings.remove(item);
                                        adapter.notifyDataSetChanged()
                                        insectsListView.invalidate()
                                    }
                                }.start()
                                finish()
                                startActivity(intent)
                            }
                        })
                        popup.show()
                    } //                    private void showDialogDeleteSend(final ListView identsLst, final Identification item) {
                    //                        AlertDialog.Builder builder = new AlertDialog.Builder(MyIdentifications.this);
                    //
                    //                        builder.setTitle("Apagar ou Enviar");
                    //
                    //                        builder.setMessage(item.getOrder());
                    //                        builder.setPositiveButton("Apagar", new DialogInterface.OnClickListener() {
                    //                                    @Override
                    //                                    public void onClick(DialogInterface dialog, int which) {
                    //                                        Toast.makeText(MyIdentifications.this, "Apagar", 1000).show();
                    //
                    //                                        //https://stackoverflow.com/questions/2558591/remove-listview-items-in-android
                    //
                    //                                        new Thread() {
                    //                                            @Override
                    //                                            public void run() {
                    //                                                db.identificationDao().deleteIdentification(item);
                    //                                                arrayOfListings.remove(item);
                    //                                                adapter.notifyDataSetChanged();
                    //                                                identsLst.invalidate();
                    //                                            }
                    //                                        }.start();
                    //                                    }
                    //                                }
                    //                        );
                    //                        builder.setNeutralButton("Enviar", new DialogInterface.OnClickListener() {
                    //                                    @Override
                    //                                    public void onClick(DialogInterface dialog, int which) {
                    //                                       // Toast.makeText(MyIdentifications.this, item.getPhotoURI(), 1000).show();
                    //                                        sendEmailWithImageFromList(item);
                    //                                    }
                    //                                }
                    //                        );
                    //                        builder.setNegativeButton("Cancelar", null /* nothing to do*/);
                    //                        AlertDialog alert = builder.create();
                    //                        alert.show();
                    //                    }
                }
                runOnUiThread(ListAdderRunnable(idents, adapter))
            } /*// Attach the adapter to a ListView
                ListView identsLst = (ListView) findViewById(R.id.identsLst);
                identsLst.setAdapter(adapter);

                for (Identification ident : idents){
                    adapter.add(ident);

                    Log.d("XXX", ident.toString());
                }*/
        }.start()
    }


    private fun sendEmailWithImageFromList(item: Identification) {
        val imageURI = Uri.parse(item.photoURI)
        val latitude = item.latitude
        val longitude = item.longitude


        val calendar = Calendar.getInstance()
        calendar.time = item.timestamp
        val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val timeAndDate = df.format(calendar.time)


        // https://www.google.com/maps/search/?api=1&query=58.698017,-152.52206

        //String emailAddress = getResources().getString(R.string.emailAddress);
        val emailAddress = "info@plataforma.edu.pt"


        //String emailSubject = getResources().getString(R.string.emailSubject) + " - " + timeAndDate;
        val emailSubject = item.order + " - " + timeAndDate
        //String emailSubject = getResources().getString(R.string.emailSubject) + " - " + timeAndDate;
        val emailBody =
            """${resources.getString(R.string.emailBody)}Latitude: $latitude Longitude: $longitude

 https://www.google.com/maps/search/?api=1&query=$latitude,$longitude"""

        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.setType("plain/text")
        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(emailAddress))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, emailSubject)
        emailIntent.putExtra(Intent.EXTRA_TEXT, emailBody)

        if (imageURI != null) {
            emailIntent.putExtra(Intent.EXTRA_STREAM, imageURI)
        }

        this.startActivity(Intent.createChooser(emailIntent, "Sending email..."))
    } //Bottom Send Email
    /*public void onSendEmailClick(View view) {
        Intent intent = new Intent();

        intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(Intent.createChooser(intent, "Complete action using"), PICK_FROM_GALLERY);
    }*/
    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Check wich request we're responding to
        if (requestCode == SAVE_IDENT) {
            //Make sure the request was successful
            if (resultCode == RESULT_OK) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("finish", true);
                setResult(RESULT_OK, resultIntent);
                finish();
            }

        } else if (requestCode == PICK_FROM_GALLERY && resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();

            sendEmailWithImage(selectedImage);
        }
    }*/


    companion object {
        private const val SAVE_IDENT = 1
        private const val PICK_FROM_GALLERY = 101
    }
}