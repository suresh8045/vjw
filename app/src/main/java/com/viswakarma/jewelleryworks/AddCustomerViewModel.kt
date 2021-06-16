package com.viswakarma.jewelleryworks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.viswakarma.jewelleryworks.model.datasource.local.roomdb.models.Customer
import com.viswakarma.jewelleryworks.model.repository.DataRepository
import com.viswakarma.jewelleryworks.model.util.isValid
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.OffsetDateTime
import java.util.*

class AddCustomerViewModel(private val dataRepository: DataRepository) : ViewModel() {

    private val _addCustomerIsFinished: MutableLiveData<Boolean> = MutableLiveData(false)
    val addCustomerIsFinished: LiveData<Boolean> get() = _addCustomerIsFinished

    private val _addCustomerInProgress: MutableLiveData<Boolean> = MutableLiveData(false)
    val addCustomerInProgress: LiveData<Boolean> get() = _addCustomerInProgress

    fun addCustomer(customerName: String, phoneNo: String, address: String) {
        Timber.e("customerName: $customerName")
        viewModelScope.launch {
            _addCustomerInProgress.value = true
            val customer = Customer(id= UUID.randomUUID().toString(), name = customerName, phone = phoneNo, dateTime = OffsetDateTime.now(), location = address)
            dataRepository.addNewCustomer(customer)
            _addCustomerInProgress.value = true
            _addCustomerIsFinished.value = true
        }
    }

    fun isCustomerNameValid(name: String): Boolean {
        return name.isValid(maxlength = Customer.MAX_LENGTH_NAME, minLength = 3)
    }
    fun isPhoneNoValid(phone: String): Boolean {
        return phone.isValid(maxlength = 10, minLength = 10)
    }
    fun isAddressValid(address: String): Boolean {
        return address.isValid(canBeBlank = false, maxlength = 100, minLength = 0)
    }

}