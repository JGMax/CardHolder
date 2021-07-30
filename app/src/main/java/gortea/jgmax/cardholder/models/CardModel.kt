package gortea.jgmax.cardholder.models

data class CardModel(
    val id: Int,
    val name: String,
    val additionalInfo: String,
    val position: String,
    val phoneNumbers: ArrayList<String>,
    val socialNetworks: ArrayList<String>
)