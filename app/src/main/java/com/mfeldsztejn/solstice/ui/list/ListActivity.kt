package com.mfeldsztejn.solstice.ui.list

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.mfeldsztejn.solstice.R
import com.mfeldsztejn.solstice.dtos.Contact
import com.mfeldsztejn.solstice.events.StartIntentEvent
import com.mfeldsztejn.solstice.ui.details.DetailActivity
import kotlinx.android.synthetic.main.list_fragment.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class ListActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_CODE = 1
    }

    private lateinit var viewModel: ListViewModel
    private lateinit var adapter: ContactsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_fragment)

        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)

        val layoutManager = LinearLayoutManager(this)
        recycler_view.layoutManager = layoutManager
        adapter = ContactsAdapter()
        recycler_view.adapter = adapter
        recycler_view.addItemDecoration(DividerItemDecoration(this, layoutManager.orientation))

        supportActionBar?.setTitle(R.string.contacts)

        viewModel.contactsLiveData.observe(this, Observer {
            progress_bar.visibility = View.GONE
            adapter.setContacts(it)
        })
        viewModel.errorMessage.observe(this, Observer {
            progress_bar.visibility = View.GONE
            val parentView = findViewById<ViewGroup>(R.id.parent)
            Snackbar.make(parentView, it, Snackbar.LENGTH_SHORT).show()
        })
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe
    fun onStartIntentEvent(event: StartIntentEvent){
        startActivityForResult(event.intent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && data != null){
            val modifiedContact = data.getSerializableExtra(DetailActivity.CONTACT_KEY) as Contact
            viewModel.onContactChanged(modifiedContact)
        }
    }
}
