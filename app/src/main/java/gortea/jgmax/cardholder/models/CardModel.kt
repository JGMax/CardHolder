package gortea.jgmax.cardholder.models

class CardModel(val id: Int) {
    var name = ""
    var additionalInfo = ""
    var position = ""
    val phoneNumbers = arrayListOf<String>()
    val socialNetworks = arrayListOf<String>()
}