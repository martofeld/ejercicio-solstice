package com.mfeldsztejn.solstice.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mfeldsztejn.solstice.dtos.Contact
import com.mfeldsztejn.solstice.networking.ApiError
import com.mfeldsztejn.solstice.networking.DefaultCallback
import com.mfeldsztejn.solstice.networking.RestApiBuilder
import com.mfeldsztejn.solstice.repositories.ContactsApi
import org.greenrobot.eventbus.Subscribe


class ListViewModel : ViewModel() {
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    private val _contactsLiveData = MutableLiveData<List<Contact>>()
    val contactsLiveData: LiveData<List<Contact>>
        get() = _contactsLiveData

    init {
        RestApiBuilder.register(this)
        ContactsApi
                .build()
                .contacts()
                .enqueue(DefaultCallback<List<Contact>>())
    }

    override fun onCleared() {
        super.onCleared()
        RestApiBuilder.unregister(this)
    }

    @Subscribe
    fun onContactsSuccess(contacts: List<Contact>){
        _contactsLiveData.value = contacts
    }

    @Subscribe
    fun onContactsFailure(apiError: ApiError){
        _errorMessage.value = apiError.message
    }

    fun onContactChanged(modifiedContact: Contact) {
        val contacts = _contactsLiveData.value!!
        val index = contacts.indexOf(modifiedContact)
        (contacts as ArrayList)[index] = modifiedContact

        _contactsLiveData.value = contacts
    }
}
