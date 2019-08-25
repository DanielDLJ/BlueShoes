package br.com.danieldlj.blueshoes.domain

class CreditCard(
    val number : String,//number contém somente o último conjunto de números do cartão (que são 4 ou 3 números).
    val enterprise: String,
    val ownerFullName: String,
    val ownerRegNumber: String = "",
    val expiryMonth: Int = 0,
    val expiryYear: Int = 0,
    val securityNumber: String = ""){

    fun getNumberAsHidden() = String.format("**** **** **** %s", number)

    fun getOwnerFullNameAsHidden() : String {
        val nameList = ownerFullName.split(" ")

        val firstName = nameList.first().substring(0, 2)
        val lastName = nameList.last()

        return String.format("%s... %s", firstName, lastName)
    }
}