package beniso.id.isotakon.modules.mentor

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.FragmentManager
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.ListView
import beniso.id.isotakon.R
import beniso.id.isotakon.models.IsoJawabQuestionModel
import beniso.id.isotakon.modules.ViewPagerAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_mentor.*

class MentorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mentor)
        val vPager = findViewById<ViewPager>(R.id.viewpager)
        val tabMatch = findViewById<TabLayout>(R.id.tab_mentor)
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.populateFragment(MentorIsoJawabFragment(), "Iso Jawab")
        adapter.populateFragment(MentorIsoLesFragment(), "Iso Les")
        vPager.adapter = adapter
        tabMatch.setupWithViewPager(vPager)
    }


}
