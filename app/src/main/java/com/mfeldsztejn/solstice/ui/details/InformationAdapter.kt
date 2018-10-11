package com.mfeldsztejn.solstice.ui.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mfeldsztejn.solstice.R

class InformationAdapter(private val information: List<Information>) : RecyclerView.Adapter<InformationViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InformationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.information_view_holder, parent, false)
        return InformationViewHolder(view)
    }

    override fun getItemCount() = information.size

    override fun onBindViewHolder(holder: InformationViewHolder, position: Int) {
        holder.bind(information[position])
    }
}