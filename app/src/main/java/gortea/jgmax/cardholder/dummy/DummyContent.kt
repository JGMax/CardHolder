package gortea.jgmax.cardholder.dummy

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import gortea.jgmax.cardholder.models.CardModel
import gortea.jgmax.cardholder.models.params.ShowStates

object DummyContent {
    private val ITEMS = arrayListOf<CardModel>()

    fun getItems(): List<CardModel> {
        if (ITEMS.isEmpty()) {
            repeat(10) {
                ITEMS.add(
                    CardModel(
                        id = it,
                        name = ObservableField("Bredikhin Max"),
                        position = ObservableField("Student, HSE"),
                        additionalInfo = ObservableField("God"),
                        phoneNumbers = ObservableArrayList(),
                        socialNetworks = ObservableArrayList(),
                        emailList = ObservableArrayList(),
                        location = ObservableField("Hello")
                    ).also { model ->
                        model.showState.set(ShowStates.values().random())
                    }
                )
            }
        }
        return ITEMS
    }
}