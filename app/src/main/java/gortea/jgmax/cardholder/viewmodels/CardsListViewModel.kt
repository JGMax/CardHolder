package gortea.jgmax.cardholder.viewmodels

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import gortea.jgmax.cardholder.dummy.DummyContent
import gortea.jgmax.cardholder.models.CardModel
import gortea.jgmax.cardholder.models.params.ShowStates

class CardsListViewModel : ViewModel() {
    private val cards: MutableLiveData<List<CardModel>> = MutableLiveData(DummyContent.getItems())
    private val openIntent: MutableLiveData<Intent?> = MutableLiveData(null)

    fun onEventOpenLocation(location: String) {
        val dataUri = Uri.parse("geo:0,0?q=" + Uri.encode(location))
        val intent = Intent(Intent.ACTION_VIEW, dataUri)
        openIntent.value = intent
    }

    fun onEventOpenContacts(item: CardModel, showState: ShowStates) {
        item.showState.set(
            if (item.showState.get() == showState) {
                ShowStates.SHOW_NOTHING
            } else {
                showState
            }
        )
    }

    fun getCards(): LiveData<List<CardModel>> = cards
    fun getOpenIntent(): LiveData<Intent?> = openIntent
}