package com.shashov.words.features.words.presentation.views

import android.arch.lifecycle.LifecycleFragment
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import com.shashov.words.R
import com.shashov.words.features.words.data.local.Category
import com.shashov.words.features.words.presentation.CategoriesViewModel
import com.shashov.words.features.words.presentation.adapters.CategoriesListAdapter
import com.shashov.words.features.words.presentation.adapters.CategoriesSpinnerAdapter
import com.shashov.words.features.words.presentation.hide
import com.shashov.words.features.words.presentation.show
import kotlinx.android.synthetic.main.fragment_categories.*

class CategoriesFragment : LifecycleFragment() {
    private val TAG = CategoriesFragment::class.java.simpleName
    private lateinit var categoriesViewModel: CategoriesViewModel
    private var listener: OpenAnalogsListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_categories, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var spanSize = 2
        list.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        categoriesViewModel = ViewModelProviders.of(activity!!).get(CategoriesViewModel::class.java)

        categoriesViewModel.getLoading().observe(this, Observer<Boolean> { isloading ->
            if (isloading!!) progressBar.show() else progressBar.hide()
        })

        categoriesViewModel.getCategories().observe(this, Observer<ArrayList<Category>> { categories ->
            if (categories!!.isEmpty()) {
                empty.show()
            } else {
                empty.hide()
            }

            if (list.adapter == null) {
                list.adapter = CategoriesListAdapter(categories, spanSize, { category ->
                    listener?.openWords(category)
                })
            } else {
                list.adapter.notifyDataSetChanged()
            }
        })

        categoriesViewModel.getTopCategories().observe(this, Observer<ArrayList<Category>> { categories ->
            with(spinner) {
                adapter = CategoriesSpinnerAdapter(activity!!, categories!!)
                setSelection(categoriesViewModel.getTopCategoryPosition())
                categoriesViewModel.loadCategories((adapter as CategoriesSpinnerAdapter).getItem(categoriesViewModel.getTopCategoryPosition())) //TODO from shared settings

                onItemSelectedListener = object : OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?,
                                                position: Int, id: Long) {
                        categoriesViewModel.loadCategories((adapter as CategoriesSpinnerAdapter).getItem(position))
                    }

                    override fun onNothingSelected(arg0: AdapterView<*>?) {}
                }
            }
        })

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OpenAnalogsListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OpenAnalogsListener")
        }
    }

    override fun onDetach() {
        listener = null
        super.onDetach()
    }

    interface OpenAnalogsListener {
        fun openWords(category: Category)
        fun hideSoftKeyboard(view: View?)
    }
}


