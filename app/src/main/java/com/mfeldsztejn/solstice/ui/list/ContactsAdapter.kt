package com.mfeldsztejn.solstice.ui.list

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mfeldsztejn.solstice.R
import com.mfeldsztejn.solstice.dtos.Contact

class ContactsAdapter : RecyclerView.Adapter<ViewHolder<Value>>() {
    private var values: List<Value>? = null

    companion object {
        const val TITLE_VIEW_TYPE = 0
        const val CONTACT_VIEW_TYPE = 1

        const val FAVORITE_CONTACT = "favorite"
        const val OTHER_CONTACT = "other"
    }

    fun setContacts(contacts: List<Contact>) {
        val groupedContacts = contacts.groupBy { if (it.isFavorite) FAVORITE_CONTACT else OTHER_CONTACT }
        val values = ArrayList<Value>()
        if (groupedContacts.containsKey(FAVORITE_CONTACT) && groupedContacts[FAVORITE_CONTACT]!!.isNotEmpty()) {
            values.add(TitleValue(R.string.favorite_title))
            values.addAll(
                    groupedContacts[FAVORITE_CONTACT]!!
                            .sortedBy { it.name }
                            .map { ContactValue(it) }
            )
        }
        values.add(TitleValue(R.string.others_title))
        values.addAll(
                groupedContacts[OTHER_CONTACT]!!
                        .sortedBy { it.name }
                        .map { ContactValue(it) }
        )
        this.values = values
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<Value> {
        return if (viewType == CONTACT_VIEW_TYPE) {
            ContactViewHolder.create(parent) as ViewHolder<Value>
        } else {
            TitleViewHolder.create(parent) as ViewHolder<Value>
        }
    }

    override fun onBindViewHolder(holder: ViewHolder<Value>, position: Int) {
        holder.bind(values!![position])
    }

    override fun getItemCount(): Int = if (values == null) 0 else values!!.size

    override fun getItemViewType(position: Int): Int {
        return values!![position].getViewType()
    }
}

abstract class ViewHolder<in T : Value>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(value: T)
}

interface Value {
    fun getViewType(): Int
}