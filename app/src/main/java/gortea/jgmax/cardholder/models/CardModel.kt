package gortea.jgmax.cardholder.models

import androidx.databinding.ObservableField
import androidx.databinding.ObservableList
import gortea.jgmax.cardholder.models.params.ShowStates

data class CardModel(
    val id: Int,
    val name: ObservableField<String>,
    val additionalInfo: ObservableField<String>,
    val position: ObservableField<String>,
    val phoneNumbers: ObservableList<String>,
    val socialNetworks: ObservableList<String>,
    val emailList: ObservableList<String>,
    val location: ObservableField<String>
) {
    var showState = ObservableField(ShowStates.SHOW_NOTHING)
}