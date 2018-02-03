package com.shashov.words.features.words.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.shashov.words.R
import com.shashov.words.features.words.data.local.Category

class CategoriesSpinnerAdapter(context: Context, objects: ArrayList<Category>)
    : ArrayAdapter<Category>(context, R.layout.row_spinner, objects) {

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    private fun getCustomView(position: Int, convertView: View?, parent: ViewGroup): View {
        // TODO Auto-generated method stub
        //return super.getView(position, convertView, parent);
        val view = LayoutInflater.from(context).inflate(R.layout.row_spinner, parent, false)
        view.findViewById<TextView>(R.id.text1).text = getItem(position).title

        return view
    }
}