package com.mfeldsztejn.solstice.ui.details

import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mfeldsztejn.solstice.R
import com.mfeldsztejn.solstice.dtos.Contact
import kotlinx.android.synthetic.main.detail_fragment.*
import java.text.SimpleDateFormat


class DetailActivity : AppCompatActivity() {

    companion object {
        const val CONTACT_KEY = "CONTACT"
    }

    private lateinit var contact: Contact

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_fragment)

        contact = intent.getSerializableExtra(CONTACT_KEY) as Contact

        supportActionBar?.setTitle(R.string.contacts)

        if(contact.largeImageURL.isNullOrEmpty()){
            contact_image.setImageResource(R.drawable.ic_person_large)
        } else {
            Glide.with(contact_image.context)
                    .load(contact.largeImageURL)
                    .apply(RequestOptions
                            .centerCropTransform()
                            .error(R.drawable.ic_person_large))
                    .into(contact_image)
        }

        contact_name.text = contact.name

        contact_company.text = contact.companyName
        contact_company.visibility = if (contact.companyName.isNullOrEmpty()) View.GONE else View.VISIBLE

        val information = ArrayList<Information>()
        information.addAll(contact.phone.map { Information(R.string.phone, it.value, it.key.capitalize()) })
        information.add(Information(R.string.address, contact.address.toString()))
        val birthDate = SimpleDateFormat("MMMM dd, YYYY").format(contact.birthdate)
        information.add(Information(R.string.birthdate, birthDate))
        information.add(Information(R.string.email, contact.emailAddress))

        val layoutManager = LinearLayoutManager(this)
        contact_information.addItemDecoration(DividerItemDecoration(this, layoutManager.orientation))
        contact_information.layoutManager = layoutManager
        contact_information.adapter = InformationAdapter(information)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_fragment_menu, menu)

        setOptionIcon(menu!!.findItem(R.id.favorite_icon))
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == R.id.favorite_icon) {
            contact.isFavorite = !contact.isFavorite
            setOptionIcon(item)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setOptionIcon(item: MenuItem) {
        item.icon = ContextCompat.getDrawable(this, if (contact.isFavorite) R.drawable.ic_star else R.drawable.ic_star_outline)
    }

    override fun onBackPressed() {
        intent.putExtra(CONTACT_KEY, contact)
        setResult(Activity.RESULT_OK, intent)
        super.onBackPressed()
    }
}