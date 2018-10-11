package com.mfeldsztejn.solstice.ui.list

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.emoji.widget.EmojiTextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.mfeldsztejn.solstice.R
import com.mfeldsztejn.solstice.dtos.Contact
import com.mfeldsztejn.solstice.events.StartIntentEvent
import com.mfeldsztejn.solstice.ui.details.DetailActivity
import org.greenrobot.eventbus.EventBus

const val STAR_EMOJI = "⭐"

class ContactValue(val contact: Contact) : Value {
    override fun getViewType() = ContactsAdapter.CONTACT_VIEW_TYPE
}

class ContactViewHolder(itemView: View) : ViewHolder<ContactValue>(itemView) {
    companion object {
        fun create(parent: ViewGroup): ViewHolder<ContactValue> {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_view_holder, parent, false)
            return ContactViewHolder(view)
        }
    }

    private val star: EmojiTextView = itemView.findViewById(R.id.contact_star)
    private val name: TextView = itemView.findViewById(R.id.contact_name)
    private val company: TextView = itemView.findViewById(R.id.contact_company)
    private val image: ImageView = itemView.findViewById(R.id.contact_image)

    override fun bind(value: ContactValue) {
        val contact = value.contact
        company.text = contact.companyName
        company.visibility = if (contact.companyName.isNullOrEmpty()) View.GONE else View.VISIBLE

        star.text = STAR_EMOJI
        if (contact.isFavorite) {
            star.visibility = View.VISIBLE
        } else {
            star.visibility = View.INVISIBLE
        }

        name.text = contact.name
        loadImage(contact.smallImageURL)

        itemView.setOnClickListener {
            val intent = Intent(itemView.context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.CONTACT_KEY, contact)
            EventBus.getDefault().post(StartIntentEvent(intent))
        }
    }

    private fun loadImage(url: String?) {
        if (url.isNullOrEmpty()) {
            image.setImageResource(R.drawable.ic_person_small)
        } else {
            Glide.with(image.context)
                    .load(url)
                    .apply(RequestOptions
                            .centerCropTransform()
                            .error(R.drawable.ic_person_small)
                            .placeholder(R.drawable.ic_person_small)
                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                    .into(image)
        }
    }
}