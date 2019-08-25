package br.com.danieldlj.blueshoes.data

import br.com.danieldlj.blueshoes.domain.CreditCard

class CreditCardsDataBase {

    companion object{

        fun getItems()
                = mutableListOf(
            CreditCard(
                "7157",
                "Visa",
                "Daniel Leme Junior"
            ),
            CreditCard(
                "8431",
                "Mastercard",
                "Daniel Leme Junior"
            ),
            CreditCard(
                "321",
                "American Express",
                "Dean Winchester"
            ),
            CreditCard(
                "8438",
                "Visa",
                "Bobby Singer"
            ),
            CreditCard(
                "123",
                "American Express",
                "Hermione Granger"
            ),
            CreditCard(
                "9011",
                "Visa",
                "Nena Mezenga"
            )
        )
    }
}