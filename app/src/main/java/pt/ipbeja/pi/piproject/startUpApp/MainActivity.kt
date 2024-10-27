package pt.ipbeja.pi.piproject.startUpApp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.room.Room
import pt.ipbeja.pi.piproject.R
import pt.ipbeja.pi.piproject.credits.Credits
import pt.ipbeja.pi.piproject.identificationInsect.Classificacao
import pt.ipbeja.pi.piproject.listSavedInsects.MyIdentifications
import pt.ipbeja.pi.piproject.persistence.MyIdentificationsDb

class MainActivity : AppCompatActivity() {
    // @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_main)
        setInitialButtonState()
    }

    override fun onResume() {
        super.onResume()
        setInitialButtonState()
    }

    private fun setInitialButtonState() {
        Thread {
            val db = Room.databaseBuilder(
                this@MainActivity.applicationContext,
                MyIdentificationsDb::class.java,
                "myidentifications.db"
            )
                .build()
            val idCount = db.identificationDao()!!.identificationCount

            //System.out.println(Integer.toString(idCount) + "Entries");
            runOnUiThread {
                val myIdsButton =
                    findViewById<View>(R.id.button) as Button
                myIdsButton.isEnabled = idCount > 0
            }
        }.start()
    }

    fun onClickToClassification(view: View?) {
        val intent = Intent(this, Classificacao::class.java)
        intent.putExtra("fragmentID", "Q1")
        startActivity(intent)
    }

    fun onClickToMyInsects(view: View?) {
        if (ActivityCompat.checkSelfPermission(
                this.baseContext,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                PERMISSIONS_READ_EXTERNAL_STORAGE
            )
        } else {
            val intent = Intent(this, MyIdentifications::class.java)
            startActivity(intent)
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSIONS_READ_EXTERNAL_STORAGE -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.size > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    val intent = Intent(this, MyIdentifications::class.java)
                    startActivity(intent)
                } else {
                    // finish();
                }
                return
            }
        }
    }

    fun onClickToCredits(view: View?) {
        val intent = Intent(this, Credits::class.java)
        startActivity(intent)
        //        correcao de navegação creditos
//        finish();
    }


    companion object {
        private const val PERMISSIONS_READ_EXTERNAL_STORAGE = 1
    }
}