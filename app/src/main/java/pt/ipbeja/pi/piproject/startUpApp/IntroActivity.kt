package pt.ipbeja.pi.piproject.startUpApp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import pt.ipbeja.pi.piproject.R

class IntroActivity : AppCompatActivity() {
    private lateinit var screenPager: ViewPager
    private lateinit var introViewPagerAdapter: IntroViewPagerAdapter
    private lateinit var tabIndicator: TabLayout
    lateinit var btnNext :Button
    var position: Int = 0
    lateinit var btnGetStarted: Button
    lateinit var btnAnim: Animation
    lateinit var tvSkip: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // make the activity on full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        // Verificar se já foi iniciado antes
        val prefs = getSharedPreferences("myPrefs", MODE_PRIVATE)
        val isFirstStart = prefs.getBoolean("isFirstStart", true)

        if (!isFirstStart) {
            val mainActivity = Intent(applicationContext, MainActivity::class.java)
            startActivity(mainActivity)
            finish()
            return
        }

        setContentView(R.layout.activity_intro)

        // hide the action bar
        //getSupportActionBar().hide();

        // ini views
        btnNext = findViewById(R.id.btn_next)
        btnGetStarted = findViewById(R.id.btn_get_started)
        tabIndicator = findViewById(R.id.tab_indicator)
        btnAnim = AnimationUtils.loadAnimation(applicationContext, R.anim.button_animation)
        tvSkip = findViewById(R.id.tv_skip)

        // fill list screen
        val mList: MutableList<ScreenItem> = ArrayList()
        mList.add(
            ScreenItem(
                "Sobre a aplicação / About app",
                """
                (PT) O objectivo deste aplicação é dar a conhecer a diversidade de insetos através da experiência de identificação.
                (EN) The purpose of this application is to make known the diversity of insects through the identification experience.
                
                
                """.trimIndent(), R.drawable.intro_2
            )
        )
        mList.add(
            ScreenItem(
                "Sobre a aplicação / About app",
                """
                (PT) Para iniciar a experiência de identificação tem que ter a certeza absoluta que está perante um inseto.
                (EN) To start the identification experiment you have to be absolutely certain that you are facing an insect.
                
                
                """.trimIndent(), R.drawable.intro_2
            )
        )
        mList.add(
            ScreenItem(
                "Sobre a aplicação / About app",
                """
                (PT) Na página de Créditos encontra-se a informação completa sobre o funcionamento da aplicação.
                (EN) On the Credits page you will find the complete information on the operation of the application.
                
                
                """.trimIndent(), R.drawable.intro_2
            )
        )

        // setup viewpager
        screenPager = findViewById(R.id.screen_viewpager)
        introViewPagerAdapter = IntroViewPagerAdapter(this, mList)
        screenPager.setAdapter(introViewPagerAdapter)

        // setup tablayout with viewpager
        tabIndicator.setupWithViewPager(screenPager)

        // next button click Listener
        btnNext.setOnClickListener(View.OnClickListener {
            position = screenPager.currentItem
            if (position < mList.size) {
                position++
                screenPager.setCurrentItem(position)
            }
            if (position == mList.size - 1) { // when we reach to the last screen

                loaddLastScreen()
            }
        })

        // tablayout add change listener
        tabIndicator.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (tab.position == mList.size - 1) {
                    loaddLastScreen()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        // Get Started button click listener
        btnGetStarted.setOnClickListener(View.OnClickListener {
            val mainActivity = Intent(applicationContext, MainActivity::class.java)
            startActivity(mainActivity)

            // Salvar a preferência para que o tutorial não seja exibido novamente
            val editor = prefs.edit()
            editor.putBoolean("isFirstStart", false)
            editor.apply()

            finish()
        })

        // skip button click listener
        tvSkip.setOnClickListener(View.OnClickListener { screenPager.setCurrentItem(mList.size) })

        // Exibir o diálogo de política de privacidade na primeira execução
        showPrivacyDialog()
    }

    private fun showPrivacyDialog() {
        AlertDialog.Builder(this)
            .setIcon(R.drawable.intro_2)
            .setTitle(R.string.Privacy_Policy)
            .setMessage(R.string.Privacy_Policy_INFO_MAIN)
            .setPositiveButton(
                R.string.agree
            ) { dialog, _ -> dialog.dismiss() }
            .create().show()
    }

    // show the GETSTARTED Button and hide the indicator and the next button
    private fun loaddLastScreen() {
        btnNext.visibility = View.INVISIBLE
        btnGetStarted.visibility = View.VISIBLE
        tvSkip.visibility = View.INVISIBLE
        tabIndicator.visibility = View.VISIBLE

        btnGetStarted.animation = btnAnim
    }
}
