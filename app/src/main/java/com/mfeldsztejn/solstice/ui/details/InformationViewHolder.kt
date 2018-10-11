package com.mfeldsztejn.solstice.ui.details

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mfeldsztejn.solstice.R

class InformationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val title: TextView = itemView.findViewById(R.id.information_title)
    private val value: TextView = itemView.findViewById(R.id.information_value)
    private val extra: TextView = itemView.findViewById(R.id.information_extra)

    fun bind(information: Information) {
        title.setText(information.title)
        value.text = information.value
        extra.text = information.extra
    }
}