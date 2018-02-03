package com.shashov.words.features.words.presentation.adapters

import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shashov.words.R
import com.shashov.words.features.words.data.local.Word
import com.shashov.words.features.words.presentation.hide
import com.shashov.words.features.words.presentation.show
import kotlinx.android.synthetic.main.row_word.view.*

class WordsListAdapter(val words: List<Word>) : RecyclerView.Adapter<WordsListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordsListAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_word, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return words.size
    }

    override fun getItemViewType(position: Int): Int {
        return words[position].base
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (getItemViewType(position) == 1)
            holder.bindMainWord(words[position], position == 0)
        else
            holder.bindWord(words[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindMainWord(word: Word, isStart: Boolean) {
            bindWord(word)
            with(itemView) {
                if (!isStart) divider.show()
                title.setTypeface(title.typeface, Typeface.BOLD)
                translate.setTypeface(translate.typeface, Typeface.BOLD)
            }
        }

        fun bindWord(word: Word) {
            with(word) {
                itemView.title.text = title
                itemView.transcription.text = transcription
                itemView.translate.text = translate
                //itemView.example.text = example
            }
            itemView.divider.hide()
        }
    }
}