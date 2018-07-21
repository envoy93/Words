package com.shashov.words.features.words.presentation.views

import android.arch.lifecycle.LifecycleFragment
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.shashov.words.R
import com.shashov.words.features.words.data.local.Category
import com.shashov.words.features.words.data.local.Word
import com.shashov.words.features.words.presentation.WordsViewModel
import com.shashov.words.features.words.presentation.adapters.WordsListAdapter
import com.shashov.words.features.words.presentation.hide
import com.shashov.words.features.words.presentation.show
import kotlinx.android.synthetic.main.fragment_words.*


class WordsFragment : LifecycleFragment() {
    private val TAG = WordsFragment::class.java.simpleName
    private lateinit var wordsViewModel: WordsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_words, container, false)
    }

    fun init(){
        ViewCompat.setNestedScrollingEnabled(list, false)
        collapsing_toolbar.setExpandedTitleTextAppearance(R.style.ExpandedAppBar)
        collapsing_toolbar.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar)
        list.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
        list.isNestedScrollingEnabled = false
        list.setHasFixedSize(true)
        list.setItemViewCacheSize(20)
        list.isDrawingCacheEnabled = true
        list.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        wordsViewModel = ViewModelProviders.of(activity!!).get(WordsViewModel::class.java)

        init()

        wordsViewModel.getCategory().observe(this, Observer<Category> { c ->
            if (c != null) collapsing_toolbar.title = c.title
        })

        wordsViewModel.getLoading().observe(this, Observer<Boolean> { isloading ->
            if (isloading!!) progressBar.show() else progressBar.hide()
        })

        wordsViewModel.getAnalogs().observe(this, Observer<List<Word>> { analogs ->
            Log.d(TAG, "received update for words")
            if (list.adapter == null) {
                list.adapter = WordsListAdapter(analogs!!)
            } else {
                list.adapter.notifyDataSetChanged()
            }
        })
    }
}
