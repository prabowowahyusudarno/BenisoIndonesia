package beniso.id.isotakon


import android.net.Uri
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import beniso.id.isotakon.modules.account.AccountFragment
import beniso.id.isotakon.modules.isojawab.IsoJawabFragment
import beniso.id.isotakon.modules.isoles.IsoLesFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), IsoJawabFragment.OnFragmentInteractionListener, IsoLesFragment.OnFragmentInteractionListener, AccountFragment.OnFragmentInteractionListener {




    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_isojawab -> {
                val isoJawabFragment = IsoJawabFragment.newInstance()
                openFragment(isoJawabFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_isoles -> {
                val isoLesFragment = IsoLesFragment.newInstance()
                openFragment(isoLesFragment)
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
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        setSupportActionBar(toolbar)
        val isoJawabFragment = IsoJawabFragment.newInstance()
        openFragment(isoJawabFragment)
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    override fun onFragmentInteraction(uri: Uri) {

    }

    override fun onBackPressed() {
        if (bottomNavigation.selectedItemId == R.id.navigation_isojawab) {
            super.onBackPressed()
        } else {
            bottomNavigation.selectedItemId = R.id.navigation_isojawab
        }
    }
}
