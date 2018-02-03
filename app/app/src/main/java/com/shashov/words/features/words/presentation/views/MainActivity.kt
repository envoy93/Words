package com.shashov.words.features.words.presentation.views


import android.app.Activity
import android.arch.lifecycle.LifecycleActivity
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.ListView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.shashov.words.R
import com.shashov.words.app.WordsApp
import com.shashov.words.features.words.data.local.Category
import com.shashov.words.features.words.presentation.WordsViewModel
import kotlinx.android.synthetic.main.banner.*


class MainActivity : LifecycleActivity(), CategoriesFragment.OpenAnalogsListener {
    private val TAG = MainActivity::class.java.simpleName
    private lateinit var wordsViewModel: WordsViewModel
    private val mPlanetTitles: Array<String>? = Array<String>(2, { "$it" })
    private var mDrawerLayout: DrawerLayout? = null
    private var mDrawerList: ListView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as WordsApp).buildWordsComponent()
        setContentView(R.layout.activity_main)
        wordsViewModel = ViewModelProviders.of(this).get(WordsViewModel::class.java)

        initAd()

        mDrawerLayout = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        mDrawerList = findViewById<View>(R.id.leftDrawer) as ListView

        // Set the adapter for the list view
        mDrawerList!!.setAdapter(ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, mPlanetTitles))
        // Set the list's click listener
        //mDrawerList.setOnItemClickListener(DrawerItemClickListener())

        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.mainPanel, CategoriesFragment())
                    .addToBackStack(null)
                    .commit()
        }
    }

    private fun initAd() {
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-4753930175100613~1882454432")

        if (adView != null && adView.visibility == View.VISIBLE) {
            adView.loadAd(AdRequest.Builder()
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    .addTestDevice("33A2300990D02B93B1E241C13945C4BA") //tablet
                    .addTestDevice("123") //TODO phone
                    .build()
            )
        }
    }

    override fun openWords(category: Category) {
        wordsViewModel.loadAnalogs(category)
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.mainPanel, WordsFragment())
                .addToBackStack(null)
                .commit()
    }

    override fun hideSoftKeyboard(view: View?) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
        if (view != null) {
            inputMethodManager?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        (application as WordsApp).releaseDrugsComponent()
    }
}
