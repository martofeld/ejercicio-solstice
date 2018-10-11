package com.mfeldsztejn.solstice.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mfeldsztejn.solstice.R

class TitleValue(val title: Int) : Value {
    override fun getViewType() = ContactsAdapter.TITLE_VIEW_TYPE
}

class TitleViewHolder(itemView: View) : ViewHolder<TitleValue>(itemView) {

    companion object {
        fun create(parent: ViewGroup) : ViewHolder<TitleValue>{
            val view = LayoutInflater.from(parent.context).inflate(R.layout.title_view_holder, parent, false)
            return TitleViewHolder(view)
        }
    }

    override fun bind(value: TitleValue) {
        (itemView as TextView).setText(value.title)
    }

}