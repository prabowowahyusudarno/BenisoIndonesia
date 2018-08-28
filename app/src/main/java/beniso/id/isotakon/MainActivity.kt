package beniso.id.isotakon

import android.net.Uri
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import beniso.id.isotakon.modules.account.AccountFragment
import beniso.id.isotakon.modules.isojawab.IsoJawabFragment

class MainActivity : AppCompatActivity(), IsoJawabFragment.OnFragmentInteractionListener, AccountFragment.OnFragmentInteractionListener {


    private lateinit var toolbar: Toolbar
    private lateinit var bottomNavigation: BottomNavigationView

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_isojawab -> {
                val isoJawabFragment = IsoJawabFragment.newInstance()
                openFragment(isoJawabFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_isoles -> {

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_akun -> {
                val accountFragment = AccountFragment.newInstance()
                openFragment(accountFragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun openFragment(fragment: Fragment) {
        val transction = supportFragmentManager.beginTransaction()
        transction.replace(R.id.container, fragment)
        transction.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        bottomNavigation = findViewById(R.id.bottomNavigation)

        setSupportActionBar(toolbar)
        val isoJawabFragment = IsoJawabFragment.newInstance()
        openFragment(isoJawabFragment)
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    override fun onFragmentInteraction(uri: Uri) {

    }
}
