package com.example.shoestore.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoestore.model.Shoe

class ShoeViewModel:ViewModel() {

    private val _shoeList=MutableLiveData<ArrayList<Shoe>>(arrayListOf())
    val shoeList:LiveData<ArrayList<Shoe>>
        get() = _shoeList

    var shoe=Shoe("","","","")

    fun updateList(){
        _shoeList.value?.add(shoe)
        shoe=Shoe("","","","")
    }
    fun removeList(){
        _shoeList.value= arrayListOf()
    }
}