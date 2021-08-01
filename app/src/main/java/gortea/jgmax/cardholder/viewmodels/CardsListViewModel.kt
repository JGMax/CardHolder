package gortea.jgmax.cardholder.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import gortea.jgmax.cardholder.models.CardModel

class CardsListViewModel : ViewModel() {
    private var cards: MutableLiveData<List<CardModel>> = MutableLiveData(listOf())
}