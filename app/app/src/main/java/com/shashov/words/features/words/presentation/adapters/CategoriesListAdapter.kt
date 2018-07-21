package com.shashov.words.features.words.presentation.adapters;

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shashov.words.R
import com.shashov.words.features.words.data.local.Category
import kotlinx.android.synthetic.main.row_category_1.view.*
import kotlinx.android.synthetic.main.row_category_2.view.*


class CategoriesListAdapter(val categories: ArrayList<Category>, var columns: Int, var onClick: (category: Category) -> Unit) : RecyclerView.Adapter<CategoriesListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesListAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_category_1, parent, false)
        return ViewHolder(v, columns, onClick)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun getItemViewType(position: Int): Int {
        return categories[position].lvl
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindHeader(categories[position])
    }

    class ViewHolder(itemView: View, var columns: Int, var onClick: (category: Category) -> Unit) : RecyclerView.ViewHolder(itemView) {
        fun bindHeader(category: Category) {
            with(category) {
                itemView.categoryName1.text = title
                itemView.list.layoutManager = GridLayoutManager(itemView.context, columns)
                itemView.list.setHasFixedSize(true)
                itemView.list.adapter = CategoriesGridAdapter(categories, onClick)
            }
        }
    }
}

class CategoriesGridAdapter(val categories: ArrayList<Category>, var onClick: (category: Category) -> Unit) : RecyclerView.Adapter<CategoriesGridAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesGridAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_category_2, parent, false)
        return ViewHolder(v, onClick)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun getItemViewType(position: Int): Int {
        return categories[position].lvl
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(categories[position])
    }

    class ViewHolder(itemView: View, var onClick: (category: Category) -> Unit) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(category: Category) {
            with(category) {
                itemView.categoryName2.text = title
                itemView.categoryPosition2.text = position.toString()
                itemView.categoryItem.setOnClickListener({ _ ->
                    onClick(category)
                })
            }
        }
    }
}
