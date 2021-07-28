package gortea.jgmax.cardholder.items

class CardModel(val id: Int) {
    var name = ""
    var additionalInfo = ""
    var position = ""
    val phoneNumbers = arrayListOf<String>()
    val socialNetworks = arrayListOf<String>()
}