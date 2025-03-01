package com.example.gorental.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gorental.utils.AppConstants
import com.example.gorental.utils.AppConstants.EMPTY_STRING
import com.example.gorental.utils.AppConstants.KAYAK_WEB_LINK
import com.example.gorental.utils.isBefore

class MainViewModel : ViewModel() {

    private val _cityFromLiveData = MutableLiveData(EMPTY_STRING)
    val cityFromLiveData: LiveData<String> = _cityFromLiveData
    private val _countryFromLiveData = MutableLiveData(EMPTY_STRING)
    val countryFromLiveData: LiveData<String> = _countryFromLiveData
    private val _cityToLiveData = MutableLiveData(EMPTY_STRING)
    val cityToLiveData :LiveData<String> = _cityToLiveData
    private val _countryToLiveData = MutableLiveData(EMPTY_STRING)
    val countryToLiveData :LiveData<String> = _countryToLiveData
    private val _pickUpDateLiveData = MutableLiveData(AppConstants.DISPLAY_TIME_FORMAT)
    val pickUpDateLiveData :LiveData<String> = _pickUpDateLiveData
    private val _dropOffDateLiveData = MutableLiveData(AppConstants.DISPLAY_TIME_FORMAT)
    val dropOffDateLiveData :LiveData<String> = _dropOffDateLiveData

    private val _validInputFlag = MutableLiveData(true)
    val validInputFlag :LiveData<Boolean> = _validInputFlag

    fun updateCityFrom(city:String){
        _cityFromLiveData.value = city
    }
    fun updateCountryFrom(country:String){
        _countryFromLiveData.value = country
    }
    fun updateCityTo(city:String){
        _cityToLiveData.value = city
    }
    fun updateCountryTo(country:String){
        _countryToLiveData.value = country
    }
    fun updatePickup(date:String){
        _pickUpDateLiveData.value = date
    }
    fun updateDropOff(date:String){
        _dropOffDateLiveData.value = date
    }

    fun updateValidityFlag(flag:Boolean){
        _validInputFlag.value = flag
    }

    fun getKayakWebLink():String{
       return "$KAYAK_WEB_LINK${cityFromLiveData.value},${countryFromLiveData.value}/${cityToLiveData.value},${countryToLiveData.value}/${pickUpDateLiveData.value}/${dropOffDateLiveData.value}"
    }

    fun validateInput(city:String,country:String,pickUp:String,dropOff:String):Boolean{
        if(city.isEmpty() || country.isEmpty() || dropOff == AppConstants.DISPLAY_TIME_FORMAT || pickUp == AppConstants.DISPLAY_TIME_FORMAT){
            return false
        }
        return pickUp.isBefore(dropOff, AppConstants.YYYY_MM_DD)
    }
}