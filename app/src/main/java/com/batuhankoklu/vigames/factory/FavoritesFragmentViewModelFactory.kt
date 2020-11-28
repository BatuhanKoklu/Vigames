package com.batuhankoklu.vigames.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.batuhankoklu.vigames.viewmodel.FavoritesFragmentViewModel
import java.lang.IllegalArgumentException

class FavoritesFragmentViewModelFactory(var context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(FavoritesFragmentViewModel::class.java)){
            return FavoritesFragmentViewModel(context) as T
        }
        throw IllegalArgumentException("ViewModel class not found")
    }

}